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
    myLock.lock();
    disconnectFlag = true;
    if (g != NULL){
        myLock.unlock();
        g->userDisconnected(this);
        myLock.lock();
    }
    this->moveToThread(mainThread);
    socket->moveToThread(mainThread);
    emit disconnectUser(this);
    myLock.unlock();
}

void ActiveUser::messageRecieved() {

    //cout << "message recieved\n";

    myLock.lock();
    cout << "Table size: " << userTable->size() << endl;
    if (socket != NULL && !disconnectFlag) {
        char data[1024];
        qint64 read = socket->read(data, sizeof(data));

        if (read != -1) {
            QString newMessage = QString(data).toUtf8();
            cout << "$" << newMessage.toStdString() << "$" << endl;
            message = message + newMessage;

            int index = message.indexOf("\n");
            while (index != -1) {
                QString split = message.mid(0, index);
                QString addF = message.mid(0, 7);
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
                            sendMessage("<$ADF$>-1");
                        }
                        else{
                            sendMessage("<$ADF$>1");
                        }
                    }
                    else if (addF == "<$GAM$>"){
                        QString code = split.mid(7);
                        if (code == "enqueue"){
                            if (inGame || waitingRoom){
                               continue;
                            }
                        }
                        else if (code == "dequeue"){
                            if (waitingRoom){
                                waitingRoom = false;
                            }
                        }
                        else if (code == "quit"){
                            if (inGame){

                            }
                        }
                    }
                    else {

                    }

                }
                else {
                    cout << "Yup" << endl;
                    QString code = split.mid(0, 3);
                    cout << "Code: " << code.toStdString() << endl;
                    int index2 = split.indexOf(" ");
                    QString userName = split.mid(3, index2 - 3);
                    QString pass = split.mid(index2 + 1);

                    if (code == "661") {
                        userLock.lock();
                        cout << "Hello" << endl;
                        User *potentialUser = userTable->value(userName);
                        cout << "Bye" << endl;
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
                                this->user = potentialUser;
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
                            this->user = u;
                            u->setUser(this);
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


void ActiveUser::sendSavedMessages() {
//    user->messageLock.lock();
//    for (unsigned i = 0; i < user->savedMessages.size(); ++i) {
//        sendMessage(user->savedMessages.at(i));
//    }
//    user->savedMessages.resize(0);
//    user->messageLock.unlock();
}

void ActiveUser::sendMessage(QString message) {
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
            fList = fList + current + ",";
        }
        user->friendLock.unlock();
        sendMessage(fList);
    }
}

void ActiveUser::addFriend(QString userName) {
    userLock.lock();
    User *toAdd = userTable->value(userName);

    if (toAdd == NULL || user->friendExists(userName)) {
        QString result = "<$ADDRESULT$>" + userName + " 0";
        sendMessage(result);
    }
    else {
        toAdd->addFriend(user->userName, false);
        user->addFriend(userName, false);
        toAdd->userSetLock.lock();
        QString result = "<$NEWFRIEND$>" + user->userName;
        if (toAdd->userA != NULL) {
            toAdd->userA->newMessage(result);
        }
        else {
            toAdd->saveMessage(result);
        }
        toAdd->userSetLock.unlock();
        result = "<$ADDRESULT$>" + userName + " 1";
        sendMessage(result);
    }
    userLock.unlock();

}
