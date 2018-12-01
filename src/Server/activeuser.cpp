#include "activeuser.h"
#include "server.h"
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
    connect(this, SIGNAL(stopTimer()), waitTimer, SLOT(stop()));
}

ActiveUser::~ActiveUser() {
    if (thread != NULL) {
        delete thread;
    }
    if (socket != NULL) {
        delete socket;
    }
    if (pRoom != NULL){
        delete pRoom;
        pRoom = NULL;
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
                        userLock.unlock();
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
                                    if (n->getType() == "$FRIEND_REQUEST$" && n->getTo()->userName == name){
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
                                    if (n->getType() == "$FRIEND_REQUEST$" && n->getFrom()->userName == name){
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
                        if (pRoom == NULL && !waitingRoom && !inQueue && !inGame && this->user != NULL){
                            //myLock.lock();
                            QString gameID = gen->getGameID();
                            QString otherPlayerName = split.mid(9);
                            pRoom = new PrivateWaitingRoom(this);
                            connect(pRoom, SIGNAL(startGame(ActiveUser*,ActiveUser*)), mainServer, SLOT(createGame(ActiveUser*, ActiveUser*)));
                            pRoom->gameID = gameID;
                            waitTimer->start(30000);
                            waitingRoom = true;
                            Notification *invite = new Notification(this->user, userTable->value(otherPlayerName), true, "$GAME_INVITE$", gameID);
                            this->user->userSetLock.lock();
                            this->user->sentNotifications.push_back(invite);
                            invite = new Notification(this->user, userTable->value(otherPlayerName), false, "$GAME_INVITE$", gameID);
                            userLock.lock();
                            User *other = userTable->value(otherPlayerName);
                            userLock.unlock();
                            other->userSetLock.lock();
                            other->recievedNotifications.push_back(invite);
                            emit other->recievedNewNoti(invite);
                            other->userSetLock.unlock();
                            this->user->userSetLock.unlock();
                            //myLock.unlock();
                        }
                    }
                    else{
                        sendMessage("<$GAME$>FAIL_TO_INVITE");
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
                pRoom->roomLock.unlock();
            }
            else{
                QString gameID = pRoom->gameID;
                pRoom->timerTriggered = true;
                QObject::disconnect(pRoom, 0, 0, 0);
                pRoom->roomLock.unlock();
                delete pRoom;
                pRoom = NULL;
                waitingRoom = false;
                inQueue = false;
                inGame = false;
                waitTimer->stop();
                sendMessage("<$GAME$>$NOT_JOIN$");
                if (this->user != NULL){
                    this->user->userSetLock.lock();
                    for (int i = 0; i < user->sentNotifications.size(); ++i){
                        Notification *n = user->sentNotifications.at(i);
                        if (n->getType() == "$GAME_INVITE$" && n->getID() == gameID){
                            User *u = n->getTo();
                            u->userSetLock.lock();
                            int pos = -1;
                            for (int j = 0; j < u->recievedNotifications.size(); ++j){
                                Notification *n = u->recievedNotifications.at(j);
                                if (n->getType() == "$GAME_INVITE$" && n->getFrom()->userName == this->user->userName && n->getID() == gameID){
                                    pos = j;
                                    break;
                                }
                            }
                            if (pos != -1){
                                Notification *toDelete = u->recievedNotifications.at(pos);
                                delete toDelete;
                                u->recievedNotifications.remove(pos);
                            }
                            u->userSetLock.unlock();
                            delete n;
                            user->sentNotifications.remove(i);
                            break;
                        }
                    }
                    this->user->userSetLock.unlock();
                }
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


void ActiveUser::denyGame(){
    if (pRoom != NULL){
        waitTimer->stop();
        delete pRoom;
        pRoom = NULL;
        inGame = false;
        waitingRoom = false;
        inQueue = false;
    }
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
        QString type = "";
        QString from = "";
        QString yesOrNo = "";
        QString gameID = "";
        bool sentSomething = false;
        QStringList responseList = response.split(" ");
        if (responseList.size() == 3){
            type = responseList.at(2).trimmed();
            from = responseList.at(0);
            yesOrNo = responseList.at(1);
        }
        else if (responseList.size() == 4){
            type = responseList.at(2);
            from = responseList.at(0);
            yesOrNo = responseList.at(1);
            gameID = responseList.at(3).trimmed();
        }
        else{
            this->user->userSetLock.unlock();
            return;
        }
        for (int i = 0; i < this->user->recievedNotifications.size(); ++i){
            Notification *current = this->user->recievedNotifications.at(i);
            cout << "From: " << current->getFrom()->userName.toStdString() << " Type: " << current->getType().toStdString() << endl;
            if (type == "$FRIEND_REQUEST$" && current->getType() == type && current->getFrom()->userName == from){
                cout << "HNR ELSE" << endl;
                User *other = current->getFrom();
                if (other != NULL){
                    other->userSetLock.lock();
                    int position = -1;
                    for (int j = 0; j < other->sentNotifications.size(); ++j){
                        Notification *sent = other->sentNotifications.at(j);
                        if (sent->getTo()->userName == this->user->userName && sent->getType() == type){
                            cout << "the crucial one" << endl;
                            position = j;
                            if (yesOrNo == "YES" && type == "$FRIEND_REQUEST$"){
                                other->addFriend(this->user->userName, false);
                                this->user->addFriend(other->userName, false);
                                emit updateFriend(this->user->userName, other->userName);
                                emit updateFriend(other->userName, this->user->userName);
                                sendMessage("<$NEWFRIEND$>" + other->userName);
                            }
                            if (other->userA != NULL && yesOrNo == "YES" && type == "$FRIEND_REQUEST$"){
                                emit other->userA->newMessage("<$NEWFRIEND$>" + this->user->userName);
                            }
                            break;
                        }
                    }
                    if (position != -1){
                        Notification *toDelete = other->sentNotifications.at(position);
                        delete toDelete;
                        other->sentNotifications.remove(position);
                    }
                    other->userSetLock.unlock();
                }
                delete current;
                this->user->recievedNotifications.remove(i);

                break;
            }
            else if (type == "$GAME_INVITE$" && current->getType() == type && current->getFrom()->userName == from && current->getID() == gameID){
                User *u = current->getFrom();
                u->userSetLock.lock();
                if (u->userA != NULL){
                    u->userA->myLock.lock();
                    if (u->userA->pRoom != NULL && !u->userA->pRoom->timerTriggered && u->userA->pRoom->gameID == gameID && !u->userA->disconnectFlag){
                        if (yesOrNo == "YES"){
                            u->userA->pRoom->setOther(this);
                            sentSomething = true;
                        }
                        else{
                            u->userA->newMessage("<$GAME$>$DENIED$");
                            u->userA->denyGame();
                            sentSomething = true;
                        }
                    }
                    else{
                        sendMessage("<$GAME$>$GAME_NOT_VALID$");
                        sentSomething = true;
                    }
                    u->userA->myLock.unlock();
                }
                else{
                    sendMessage("<$GAME$>$GAME_NOT_VALID$");
                    sentSomething = true;
                }

                int position = -1;

                for (int j = 0; j < u->sentNotifications.size(); ++j){
                    Notification *n = u->sentNotifications.at(j);
                    if (n->getTo()->userName == this->user->userName && n->getType() == "$GAME_INVITE$" && n->getID() == gameID){
                        position = j;
                        delete n;
                        break;
                    }
                }

                if (position != -1){
                    u->sentNotifications.remove(position);
                }

                u->userSetLock.unlock();

                delete current;

                this->user->recievedNotifications.remove(i);


                break;


            }
        }
        //userLock.unlock();
        this->user->userSetLock.unlock();
        if (type == "$GAME_INVITE$" && !sentSomething){
            sendMessage("<$GAME$>$GAME_NOT_VALID$");
        }

    }
    cout << "I am leaving HNR" << endl;
}

void ActiveUser::sendNotification(Notification *n){
    if (this->user != NULL && socket != NULL && !disconnectFlag){
        QString message = "<$NOTIFICATION$>" + n->getFrom()->userName + "-" + n->getType() + "-" + n->getID();
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
            if (current->getType() == "$FRIEND_REQUEST$" && current->getFrom()->userName == toAdd->userName){
                toAdd->userSetLock.unlock();
                this->user->userSetLock.unlock();
                //userLock.unlock();
                return;
            }
        }
        Notification *newNoti = new Notification(this->user, toAdd, true, "$FRIEND_REQUEST$", "");
        this->user->sentNotifications.push_back(newNoti);
        newNoti = new Notification(this->user, toAdd, false, "$FRIEND_REQUEST$", "");
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


