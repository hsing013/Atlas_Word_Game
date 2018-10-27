#include "server.h"

Server::Server()
{
    server = new QTcpServer(0);
    db = QSqlDatabase::addDatabase("QSQLITE");
    db.setDatabaseName("testDB2");

    bool open = db.open();

    userTable = new QHash<QString, User*>();


    leaderTimer = new QTimer();

    connect(leaderTimer, SIGNAL(timeout()), this, SLOT(updateLeaderBoard()));

    leaderTimer->start(60000);

    ofstream out;

    out.open("log.txt", ios::app);

    if (!out.is_open()){
        cout << "Couldn't open log.txt in constructor" << endl;
    }
    else{
        QDateTime time = QDateTime::currentDateTime();

        out << "\n\n" << time.toString().toStdString() << endl;

    }

    out.close();

    if (!open){
        log("Error while opening database, exiting");
        exit(-1);
    }

    QSqlQuery query = db.exec("SELECT * from users");

    bool success = query.first();

    if (!query.isValid()){
        log("query was not valid");
        cout << "query was not valid" << endl;
        query = db.exec("CREATE TABLE users (NAME TEXT, PASS TEXT, POINTS INTEGER);");
        query = db.exec("INSERT INTO users (NAME, PASS, POINTS) VALUES ('harsh', 'singh', 1000000);");
        query = db.exec("SELECT * from users");

    }

    success = query.first();

    log("Size of query " + query.size());

    while (success){
        User *u = new User();
        u->userName = query.value(0).toString();
        u->pass = query.value(1).toString();
        u->points = query.value(2).toInt();
        QString notiTable = u->userName + "_nt";
        cout << u->points << endl;

        log ("Creating: " + u->userName + " " + u->pass);

        userTable->insert(u->userName, u);

        QString friendTable = u->userName + "FL";

        QSqlQuery q2 = db.exec("CREATE TABLE IF NOT EXISTS " + friendTable + " (FRIENDS TEXT);");

        if (!q2.isValid()){
            exit(-1);
        }

        q2 = db.exec("SELECT * FROM " + friendTable);

        if (!q2.isValid()){
            cout << "error when reading friend table" << endl;
        }

        bool check = q2.first();

        while(check){
            u->friendList.push_back(q2.value(0).toString());
            check = q2.next();
        }

        QSqlQuery q3 = db.exec("CREATE TABLE IF NOT EXISTS " + notiTable + " (TYPE TEXT, SENDER TEXT, FROM TEXT, TO TEXT);");

        q3 = db.exec("SELECT * FROM " + notiTable);

        if (!q3.isValid()){
            cout << "error when reading notiTable" << endl;
        }

        check = q3.first();

        while (check){
            QString type = q3.value(0).toString();
            QString from = q3.value(2).toString();
            QString to = q3.value(3).toString();
            QString senderString = q3.value(1).toString();
            bool sender = false;
            if (senderString == "TRUE"){
                sender = true;
                u->sentNotifications.push_back(new Notification(from, to, sender, type));
            }
            else{
                u->recievedNotifications.push_back(new Notification(from, to, sender, type));
            }

            check = q3.next();
        }

        success = query.next();
    }

    updateLeaderBoard();



}


void Server::listen() {
    server->setMaxPendingConnections(10000);
    connect(server, SIGNAL(newConnection()), this, SLOT(newConnection()));
    QHostAddress address("138.68.48.204");
    server->listen(address, 12345);
}

void Server::updateLeaderBoard(){
    userLock.lock();
    QMap<int, QString> *leaderMap = new QMap<int, QString>();
    QVector<QString> leaderBoard;
    QHash<QString, User*>::iterator it;

    for (it = userTable->begin(); it != userTable->end(); ++it){
        User *u = it.value();
        QString value = leaderMap->value(u->points);
        if (value == ""){
            leaderMap->insert(u->points, u->userName);
        }
        else{
            leaderMap->insertMulti(u->points, u->userName);
        }
    }

    QMap<int, QString>::iterator it2 = leaderMap->begin();



    while (it2 != leaderMap->end()){
        QString name = it2.value();
        QString points;

        int intPoints = it2.key();

        points.setNum(intPoints);

        QString finalResult = name + "-" + points;

        leaderBoard.push_front(finalResult);

        ++it2;
    }
    delete leaderMap;
    leaderMap = NULL;

    QString fullList = "<$LEADER$>";

    int size = leaderBoard.size();

    if (size > 25){
        size = 26;
    }

    for (int i = 0; i < size; ++i){
        if (i == 0){
            fullList = fullList + leaderBoard.at(i);
        }
        else{
            fullList = fullList + "$" + leaderBoard.at(i);
        }
    }

    leaderBoardString = fullList;

    userLock.unlock();


    emit leaderBoardUpdate(leaderBoardString);

    leaderTimer->start(60000);


}

void Server::log(QString s){
    ofstream out;

    out.open("log.txt", ios::app);

    if (!out.is_open()){
        cout << "Error opening the log file" << endl;
        return;
    }

    out << s.toStdString() << endl;

    out.close();

}

void Server::newConnection() {
    cout << "New connection" << QThread::currentThreadId() <<  endl;
    QTcpSocket *socket = server->nextPendingConnection();
    socket->setParent(NULL);
    ActiveUser *user = new ActiveUser(userTable);

    user->socket = socket;

    user->mainThread = QThread::currentThread();

    user->thread = new QThread();

    user->waitTimer->moveToThread(user->thread);

    user->leaderBoard = this->leaderBoardString;

    //user->waitTimer->setParent(user);

    user->moveToThread(user->thread);

    socket->moveToThread(user->thread);

    user->thread->start();

    connect(socket, SIGNAL(readyRead()), user, SLOT(messageRecieved()));
    connect(socket, SIGNAL(disconnected()), user, SLOT(disconnect()));
    connect(user, SIGNAL(disconnectUser(ActiveUser*)), this, SLOT(disconnectUser(ActiveUser*)));
    connect(user, SIGNAL(saveUser(QString, QString)), this, SLOT(saveUserToDB(QString,QString)));
    connect(user, SIGNAL(newMessage(QString)), user, SLOT(sendMessage(QString)));
    connect(user, SIGNAL(messageToServer(QString)), this, SLOT(displayMessage(QString)));
    connect(this, SIGNAL(disconnectAll()), user, SLOT(disconnect()));
    connect(user, SIGNAL(removeFromQueue(ActiveUser*)), this, SLOT(removeFromQueue(ActiveUser*)));
    connect(user, SIGNAL(playGame(ActiveUser*)), this, SLOT(findPlayerToPlay(ActiveUser*)));
    connect(user, SIGNAL(updatePoints(QString,QString,int)), this, SLOT(updatePoints(QString,QString,int)));
    connect(this, SIGNAL(leaderBoardUpdate(QString)), user, SLOT(updateLeaderBoard(QString)));
    connect(user, SIGNAL(updateFriend(QString, QString)), this, SLOT(updateFriendDB(QString, QString)));

    if (!user->socket->isOpen()){
        user->disconnect();
    }




}

void Server::saveUserToDB(QString user, QString pass){
    QSqlQuery query = db.exec("INSERT INTO users (NAME, PASS, POINTS) VALUES ('" + user + "', '" + pass + "', 0);");

    if (!query.isValid()){
        log("Error when adding user to DB");
    }

}

void Server::updatePoints(QString userName, QString pass, int points){
    //QSqlQuery query = db.exec("UPDATE users SET POINTS = '" + points + "' WHERE NAME = '" + userName + "';");
    cout << "Updating points for " << userName.toStdString() << " " << QThread::currentThreadId() <<  endl;
    QSqlQuery query(db);

    bool valid = query.prepare("UPDATE users SET POINTS = :points WHERE NAME = :userName");

    query.bindValue(":points", points);
    query.bindValue(":userName", userName);

    bool valid2 = query.exec();

    if (!valid || !valid2){
        cout << query.lastError().text().toStdString() << endl;
        log("Error when updating points");
        cout << "error when updating points for db" << endl;
    }
}

void Server::disconnectUser(ActiveUser *au) {
    cout << "Disconnecting" << endl;

    au->myLock.lock();
    userLock.lock();
    removeFromQueue(au);
    disconnect(au, 0, 0, 0);
    User *u = au->user;
    if (u == NULL) {
        au->thread->quit();
        au->thread->wait();
        au->socket->close();
        au->myLock.unlock();
        au->deleteLater();
        au = NULL;
        userLock.unlock();
        return;
    }
    disconnect(u, 0, 0, 0);
    u->userSetLock.lock();
    u->userA = NULL;
    au->thread->quit();
    au->thread->wait();
    au->socket->close();
    au->myLock.unlock();
    au->deleteLater();
    au = NULL;
    u->userSetLock.unlock();

    userLock.unlock();
    cout << "done disconnecting" << endl;
}

void Server::displayMessage(QString message) {
    cout << message.toStdString() << endl;
}

void Server::findPlayerToPlay(ActiveUser *user){
    cout << "I am enqueuing " << user->user->userName.toStdString() << endl;
    queueLock.lock();
    user->myLock.lock();

    if (!user->waitingRoom || user->disconnectFlag){
        user->inQueue = false;
        user->myLock.unlock();
        queueLock.unlock();
        return;
    }

    if (queue.size() == 0){
        queue.append(user);
        user->myLock.unlock();
    }
    else{
        bool matchFound = false;
        while(!matchFound && queue.size() != 0){
            ActiveUser *other = queue.front();
            other->myLock.lock();
            if (!other->waitingRoom || other->disconnectFlag){
                other->inQueue = false;
                queue.removeAll(other);
                other->myLock.unlock();
                queue.pop_front();
            }
            else{
                Game *g = new Game();
                emit user->stopTimer();
                emit other->stopTimer();
                removeFromQueue(other);
                connect(g, SIGNAL(destroyMe(Game*)), this, SLOT(disconnectGame(Game*)));
                connect(g, SIGNAL(changeOwnership()), g, SLOT(handControlToMain()));
                QThread *t = new QThread();
                connect(t, SIGNAL(started()), g, SLOT(startGame()));
                g->player1 = user;
                g->player2 = other;
                g->myThread = t;
                g->timer->moveToThread(t);
                g->mainThread = QThread::currentThread();
                other->inGame = true;
                user->inGame = true;
                other->waitingRoom = false;
                other->inQueue = false;
                user->waitingRoom = false;
                user->inQueue = false;
                user->g = g;
                other->g = g;
                user->myLock.unlock();
                other->myLock.unlock();
                matchFound = true;
                g->moveToThread(t);
                t->start();
            }
        }

        if (!matchFound){
            queue.append(user);
            user->myLock.unlock();
        }
    }

    queueLock.unlock();
}

void Server::removeFromQueue(ActiveUser *user){
    //user->myLock.lock();
    user->inQueue = false;
    queue.removeAll(user);
    //user->myLock.unlock();
}

void Server::disconnectGame(Game *g){
    cout << "Disconnecting Game" << endl;
    g->gameMutex.lock();
    disconnect(g, 0, 0, 0);
    g->myThread->quit();
    g->myThread->wait();
    g->myThread->deleteLater();

    g->deleteLater();

    g->gameMutex.unlock();


}

void Server::updateFriendDB(QString userName, QString newFriend){
    QString friendTable = userName + "FL";
    QSqlQuery query = db.exec("INSERT INTO " + friendTable + " (FRIENDS) VALUES ('" + newFriend + "')");

    if (!query.isValid()){
       cout << "Error while adding a friend to DB" << endl;
    }
}

void Server::createGame(ActiveUser *owner, ActiveUser* other){

}


