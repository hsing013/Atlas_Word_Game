#include "Server.h"

Server::Server()
{
    server = new QTcpServer(0);
    db = QSqlDatabase::addDatabase("QSQLITE");
    db.setDatabaseName("testDB");

    bool open = db.open();

    userTable = new QHash<QString, User*>();


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
        query = db.exec("CREATE TABLE users (NAME TEXT, PASS TEXT);");
        query = db.exec("INSERT INTO users (NAME, PASS) VALUES ('admin', 'pass');");
        query = db.exec("SELECT * from users");

    }

    success = query.first();

    log("Size of query " + query.size());

    while (success){
        User *u = new User();
        u->userName = query.value(0).toString();
        u->pass = query.value(1).toString();

        log ("Creating: " + u->userName + " " + u->pass);

        userTable->insert(u->userName, u);

        QString tableName = u->userName + "_FL";

        QSqlQuery q2 = db.exec("CREATE TABLE IF NOT EXISTS tableName (FREINDS TEXT);");

        q2 = db.exec("SELECT * FROM " + tableName);

        bool check = q2.first();

        while(check){
            u->friendList.push_back(q2.value(0).toString());
            check = q2.next();
        }
        success = query.next();
    }




}


void Server::listen() {
    server->setMaxPendingConnections(10000);
    connect(server, SIGNAL(newConnection()), this, SLOT(newConnection()));
    QHostAddress address("10.25.0.254");
    server->listen(address, 12345);
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
    cout << "New connection" << endl;
    QTcpSocket *socket = server->nextPendingConnection();
    socket->setParent(NULL);
    ActiveUser *user = new ActiveUser(userTable);

    user->socket = socket;

    user->mainThread = QThread::currentThread();

    user->thread = new QThread();

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

    QString message = "Hello\nHoopla\nSMAJBSBFHDJBFSBFSDBn";

    user->newMessage(message);

}

void Server::saveUserToDB(QString user, QString pass){
    QSqlQuery query = db.exec("INSERT INTO users (NAME, PASS) VALUES ('" + user + "', '" + pass + "');");

    if (!query.isValid()){
        log("Error when adding user to DB");
    }

}

void Server::disconnectUser(ActiveUser *au) {
    cout << "Disconnecting" << endl;
    removeFromQueue(au);
    au->myLock.lock();
    userLock.lock();
    disconnect(au, 0, 0, 0);
    User *u = au->user;
    if (u == NULL) {
        au->thread->quit();
        au->thread->wait();
        au->socket->close();
        au->myLock.unlock();
        au->deleteLater();
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

    u->userSetLock.unlock();

    userLock.unlock();

}

void Server::displayMessage(QString message) {
    cout << message.toStdString() << endl;
}

void Server::findPlayerToPlay(ActiveUser *user){
    queueLock.lock();
    user->myLock.lock();
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
            }
            else{
                Game *g = new Game();
                connect(g, SIGNAL(destroyMe(Game*)), this, SLOT(disconnectGame(Game*)));
                QThread *t = new QThread();
                connect(t, SIGNAL(started()), g, SLOT(startGame()));
                g->player1 = user;
                g->player2 = other;
                g->myThread = t;
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
    user->myLock.lock();
    user->inQueue = false;
    queue.removeAll(user);
    user->myLock.unlock();
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


