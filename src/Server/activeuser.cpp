#include "activeuser.h"

ActiveUser::ActiveUser()
{
    socket = NULL;
    thread = NULL;
    disconnectFlag = false;
    user = NULL;
    mainThread = NULL;
    inGame = false;
    waitingRoom = false;
    inQueue = false;
    g = NULL;
    waitTimer = new QTimer(NULL);
    pRoom = NULL;
    connect(waitTimer, SIGNAL(timeout()), this, SLOT(timerTrigger()));
    connect(this, SIGNAL(stopTimer()), waitTimer, SLOT(stop()));
}

ActiveUser::ActiveUser(QHash<QString, User *> *table) {
    socket = NULL;
    thread = NULL;
    disconnectFlag = false;
    user = NULL;
    mainThread = NULL;
    userTable = table;
    inGame = false;
    waitingRoom = false;
    inQueue = false;
    g = NULL;
    waitTimer = new QTimer();
    pRoom = NULL;
    connect(waitTimer, SIGNAL(timeout()), this, SLOT(timerTrigger()));
}

ActiveUser::~ActiveUser() {
    if (thread != NULL) {
        delete thread;
    }
    if (socket != NULL) {
        delete socket;
    }
}

void ActiveUser::disconnect() {
    string out = "";
    if (this->user != NULL){
        out = this->user->userName.toStdString();
    }
    cout << out << " is diconnecting" << endl;
    myLock.lock();
    disconnectFlag = true;
    if (g != NULL){
        myLock.unlock();
        g->userDisconnected(this);
        myLock.lock();
    }
    if (waitTimer->isActive()){
        waitTimer->stop();
    }
    waitTimer->deleteLater();
    this->moveToThread(mainThread);
    socket->moveToThread(mainThread);
    emit disconnectUser(this);
    myLock.unlock();
}

void ActiveUser::messageRecieved() {

    //cout << "message recieved\n";
    string out = "";
    if (this->user != NULL){
        out = this->user->userName.toStdString();
    }
    cout << "New message" << QThread::currentThreadId() << out << endl;
    myLock.lock();
    if (socket != NULL && !disconnectFlag) {
        char data[1024];
        qint64 read = socket->read(data, sizeof(data));

        cout << read << endl;

        cout << data << endl;

        if (read != -1) {
            QString newMessage = QString(data).toUtf8();
            qint64 size = newMessage.size();
            if (read != newMessage.size()){
                cout << "I was triggered" << endl;
                int diff = abs(read - size);
                cout << diff << endl;
                newMessage = newMessage.mid(0, newMessage.size() - diff);
            }
            cout << "$" << newMessage.toStdString() << "$" << endl;
            cout << "New message size: " << newMessage.size() << endl;
            cout << "Last one: " << newMessage.at(newMessage.size() - 1).toLatin1() << endl;



            message = message + newMessage;

            int index = message.indexOf("\n");
            while (index != -1) {
                QString split = message.mid(0, index);
                cout << split.toStdString() << endl;
                QString addF = message.mid(0, 7);
                QString game = message.mid(0, 8);
                QString gameWord = message.mid(0, 9);
                QString notif = message.mid(0, 16);
                if (user != NULL) {  //if the user is logged in
                    if (split == "<$GETFL$>") {
                        sendFL();
                    }
                    else if (addF == "<$ADD$>") {
                        addFriend(split.mid(7));
                    }
                    else if (addF == "<$ADF$>"){
                        userLock.lock();
                        QString name = split.mid(7);
                        User *potentialUser = userTable->value(name);

                        if (potentialUser == NULL){
                            sendMessage("<$ADF$>" + name + "-" + QString::number(1));
                        }
                        else if (this->user != NULL && this->user->userName == name){
                            sendMessage("<$ADF$>" + name + "-" + QString::number(5));
                        }
                        else{
                            bool check = false;
                            if (this->user != NULL){
                                this->user->userSetLock.lock();
                                check = this->user->friendExists(name);
                                if (check){
                                    sendMessage("<$ADF$>" + name + "-" + QString::number(4));
                                }
                                for (int i = 0; i < this->user->sentNotifications.size(); ++i){
                                    if (check){
                                        break;
                                    }
                                    Notification *n = this->user->sentNotifications.at(i);
                                    if (n->getType() == "FRIEND_REQUEST" && n->getTo() == name){
                                        check = true;
                                        sendMessage("<$ADF$>" + name + "-" + QString::number(2));
                                        break;
                                    }
                                }
                                for (int i = 0; i < this->user->recievedNotifications.size(); ++i){
                                    if (check){
                                        break;
                                    }
                                    Notification *n = this->user->recievedNotifications.at(i);
                                    if (n->getType() == "FRIEND_REQUEST" && n->getFrom() == name){
                                        check = true;
                                        sendMessage("<$ADF$>" + name + "-" + QString::number(3));
                                        break;
                                    }
                                }

                                this->user->userSetLock.unlock();


                            }
                            if (!check){
                                sendMessage("<$ADF$>" + name + "-" + QString::number(potentialUser->points));
                            }

                        }
                        userLock.unlock();
                    }
                    else if (notif == "<$NOTIFICATION$>"){
                        handleNotificationResponse(message.mid(16));
                    }
                    else if (game == "<$GAME$>"){
                        QString code = split.mid(8);
                        if (code == "enqueue"){
                            if (inGame || waitingRoom){
                               continue;
                            }
                            else{
                                waitTimer->start(5000);
                                emit playGame(this);
                                waitingRoom = true;
                                inQueue = true;
                            }
                        }
                        else if (code == "quit"){
                            if (inGame && g != NULL){
                                myLock.unlock();
                                g->userQuit(this);
                                myLock.lock();
                            }
                        }
                    }
                    else if (gameWord == "<$GAMEW$>"){
                        if (inGame && g != NULL){
                            myLock.unlock();
                            g->updateGame(this, split.mid(9));
                            myLock.lock();
                        }
                    }
                    else if (gameWord == "<$GAMEI$>"){
                        if (pRoom == NULL && !waitingRoom && !inQueue && !inGame){
                            pRoom = new PrivateWaitingRoom(this);
                            waitTimer->start(30000);
                            waitingRoom = true;
                        }
                    }

                }
                else {
                    QString code = split.mid(0, 3);
                    cout << "Code: " << code.toStdString() << endl;
                    int index2 = split.indexOf(" ");
                    QString userName = split.mid(3, index2 - 3);
                    QString pass = split.mid(index2 + 1);

                    if (code == "661") {
                        userLock.lock();

                        User *potentialUser = userTable->value(userName);

                        if (potentialUser == NULL) {
                            cout << "I am here" << endl;
                            sendMessage(QString("<$LOGIN$>-1"));
                        }
                        else {
                            cout << "I entered the else" << endl;
                            if (potentialUser->pass.compare(pass) != 0) {
                                sendMessage(QString("<$LOGIN$>-2"));
                            }
                            else if (!potentialUser->setUser(this)) {
                                sendMessage(QString("<$LOGIN$>-3"));
                            }
                            else {
                                sendMessage(QString("<$LOGIN$>1"));
                                QString points;
                                points.setNum(potentialUser->points);
                                sendMessage(QString("<$POINTS$>") + points);
                                sendMessage(leaderBoard);
                                this->user = potentialUser;
                                sendFL();
                                connect(potentialUser, SIGNAL(recievedNewNoti(Notification*)), this, SLOT(sendNotification(Notification*)));
                                sendAllNotifications();
                            }
                        }
                        userLock.unlock();
                    }
                    else if (code == "662") {
                        userLock.lock();
                        User *u = userTable->value(userName);
                        if (u == NULL) {
                            u = new User();
                            u->userName = userName;
                            u->pass = pass;
                            userTable->insert(userName, u);
                            emit saveUser(userName, pass);
                            sendMessage(QString("<$SIGNUP$>1"));
                            QString points;
                            points.setNum(u->points);
                            sendMessage(QString("<$POINTS$>") + points);
                            this->user = u;
                            u->setUser(this);
                            sendMessage(leaderBoard);
                            sendFL();
                            connect(u, SIGNAL(recievedNewNoti(Notification*)), this, SLOT(sendNotification(Notification*)));
                            sendAllNotifications();
                        }
                        else {
                            sendMessage(QString("<$SIGNUP$>-1"));
                        }
                        userLock.unlock();
                    }
                    else{
                        cout << "FAILURE" << endl;
                        emit disconnect();
                    }

                }
                message = message.mid(index + 1, message.size());
                index = message.indexOf("\n");
            }
        }
    }
    myLock.unlock();
}


void ActiveUser::timerTrigger(){
    cout << "timer" << QThread::currentThreadId() <<  endl;
    myLock.lock();
    if (pRoom == NULL){
        if (!inGame && waitTimer != NULL && inQueue){
            waitingRoom = false;
            waitTimer->stop();
            sendMessage("<$GAME$>$NOTFOUND$");
            emit removeFromQueue(this);
        }
        else{
            waitTimer->stop();
        }
    }
    else{
        if (!inGame && waitTimer != NULL){
            pRoom->roomLock.lock();
            if (pRoom->otherPlayerIn){
                waitTimer->stop();
            }
            else{
                pRoom->timerTriggered = true;
                QObject::disconnect(pRoom, 0, 0, 0);
                pRoom->roomLock.unlock();
                delete pRoom;
                pRoom = NULL;
            }
        }
        else{
            waitTimer->stop();
        }
    }

    myLock.unlock();

}

void ActiveUser::sendSavedMessages() {
//    user->messageLock.lock();
//    for (unsigned i = 0; i < user->savedMessages.size(); ++i) {
//        sendMessage(user->savedMessages.at(i));
//    }
//    user->savedMessages.resize(0);
//    user->messageLock.unlock();
}

void ActiveUser::sendMessage(QString message) {
    if (user != NULL){
    cout << user->userName.toStdString() << endl;
    }
    if (socket != NULL && !disconnectFlag) {
        if (message.at(message.size() - 1) != '\n') {
            message = message + "\n";
        }
        cout << "Sending: " << message.toStdString() << endl;
        string s = message.toStdString();
        if (s.at(s.size() - 1) != '\n') {
            s = s + "\n";
        }
        const char *c = s.c_str();


        qint64 sent = socket->write(c, s.size());

        cout << "Size of message sent: " << sent << endl;

        while (sent < message.size() && sent != -1) {
            cout << "extra life" << endl;
            string rest = message.mid(sent, message.size()).toStdString();
            if (rest.at(rest.size() - 1) != '\n') {
                s = s + "\n";
            }
            const char *c2 = rest.c_str();
            qint64 sent2 = socket->write(c2, rest.size());
            sent = sent + sent2;
        }

    }
}

void ActiveUser::handleNotificationResponse(QString response){
    cout << "Handle notification response was called " << response.toStdString() << endl;
    if (disconnectFlag){
        return;
    }
    if (this->user != NULL){
        this->user->userSetLock.lock();
        //userLock.lock();
        QStringList responseList = response.split(" ");
        if (responseList.size() != 3){
            this->user->userSetLock.unlock();
            //userLock.unlock();
            return;
        }
        QString type = responseList.at(2).trimmed();
        QString from = responseList.at(0);
        QString yesOrNo = responseList.at(1);
        for (int i = 0; i < this->user->recievedNotifications.size(); ++i){
            Notification *current = this->user->recievedNotifications.at(i);
            //cout << "From: " << current->getFrom() << " Type: " << current->getType() << endl;
            if (current->getType() == type && current->getFrom() == from){
                cout << "HNR ELSE" << endl;
                User *other = userTable->value(from);
                if (other != NULL){
                    other->userSetLock.lock();
                    int position = -1;
                    for (int j = 0; j < other->sentNotifications.size(); ++j){
                        Notification *sent = other->sentNotifications.at(j);
                        if (sent->getTo() == this->user->userName && sent->getType() == type){
                            cout << "the crucial one" << endl;
                            position = j;
                            if (yesOrNo == "YES" && type == "FRIEND_REQUEST"){
                                other->addFriend(this->user->userName, false);
                                this->user->addFriend(other->userName, false);
                                emit updateFriend(this->user->userName, other->userName);
                                emit updateFriend(other->userName, this->user->userName);
                                sendMessage("<$NEWFRIEND$>" + other->userName);
                            }
                            if (other->userA != NULL && yesOrNo == "YES" && type == "FRIEND_REQUEST"){
                                emit other->userA->newMessage("<$NEWFRIEND$>" + this->user->userName);
                            }
                            break;
                        }
                    }
                    if (position != -1){
                        other->sentNotifications.remove(position);
                    }
                    other->userSetLock.unlock();
                }

                this->user->recievedNotifications.remove(i);

                break;
            }
        }
        //userLock.unlock();
        this->user->userSetLock.unlock();

    }
    cout << "I am leaving HNR" << endl;
}

void ActiveUser::sendNotification(Notification *n){
    if (this->user != NULL && socket != NULL && !disconnectFlag){
        QString message = "<$NOTIFICATION$>" + n->getFrom() + "-" + n->getType();
        sendMessage(message);
    }
}

void ActiveUser::sendAllNotifications(){
    if (this->user != NULL){
        this->user->userSetLock.lock();
        for (int  i = 0; i < this->user->recievedNotifications.size(); ++i){
            sendNotification(this->user->recievedNotifications.at(i));
        }
        this->user->userSetLock.unlock();
    }
}

void ActiveUser::sendFL() {
    if (socket != NULL && !disconnectFlag) {
        user->friendLock.lock();
        QString fList = "<$FRIENDLIST$>";
        for (unsigned i = 0; i < user->friendList.size(); ++i) {
            QString current = user->friendList.at(i);
            if (i == user->friendList.size() - 1) {
                fList = fList + current;
                continue;
            }
            fList = fList + current + "-";
        }
        user->friendLock.unlock();
        sendMessage(fList);
    }
}

void ActiveUser::addFriend(QString userName) {
    if (disconnectFlag){
        return;
    }
    if (this->user != NULL && userName == this->user->userName){
        return;
    }
    //userLock.lock();
    User *toAdd = userTable->value(userName);

    if (toAdd != NULL && this->user != NULL){
        toAdd->userSetLock.lock();
        this->user->userSetLock.lock();
        for (int i = 0; i < this->user->recievedNotifications.size(); ++i){
            Notification *current = this->user->recievedNotifications.at(i);
            if (current->getType() == "FRIEND_REQUEST" && current->getFrom() == toAdd->userName){
                toAdd->userSetLock.unlock();
                this->user->userSetLock.unlock();
                //userLock.unlock();
                return;
            }
        }
        Notification *newNoti = new Notification(this->user->userName, toAdd->userName, true, "FRIEND_REQUEST");
        this->user->sentNotifications.push_back(newNoti);
        newNoti = new Notification(this->user->userName, toAdd->userName, false, "FRIEND_REQUEST");
        toAdd->recievedNotifications.push_back(newNoti);
        emit toAdd->recievedNewNoti(newNoti);
        toAdd->userSetLock.unlock();
        this->user->userSetLock.unlock();
    }

    //userLock.unlock();

}


void ActiveUser::updateLeaderBoard(QString newBoard){
    this->leaderBoard = newBoard;
    sendMessage(leaderBoard);
}


