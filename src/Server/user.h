#ifndef USER_H
#define USER_H
#include <QObject>
#include <QMutex>
#include <fstream>
#include <iostream>
#include <vector>
#include "include.h"
#include "notification.h"
#include <QVector>
using namespace std;

struct ActiveUser;

struct User : public QObject{
    Q_OBJECT
public:
    QString userName;
    QString pass;
    vector <QString> friendList;
    QString color;
    int favNumber;
    int points;
    QString message;
    ActiveUser *userA;
    QMutex userSetLock;
    QMutex friendLock;
    QMutex messageLock;
    QVector<Notification*> recievedNotifications;
    QVector<Notification*> sentNotifications;
    User(){
        userName = "";
        pass = "";
        color = "";
        favNumber = -1;
        userA = NULL;
        points = 0;
    }
    ~User(){

    }
public slots:
    bool setUser(ActiveUser *user){
        userSetLock.lock();
        if (this->userA != NULL){
            userSetLock.unlock();
            return false;
        }
        else{
            this->userA = user;
            userSetLock.unlock();
            return true;
        }
    }
    bool friendExists(QString myFriend){
        friendLock.lock();
        for (unsigned i = 0; i < friendList.size(); ++i){
            if (myFriend == friendList.at(i)){
                friendLock.unlock();
                return true;
            }
         }
         friendLock.unlock();
         return false;
    }

    void addPoints(int add){
        points += add;
    }

    int getPoints(){
        return points;
    }

    void addFriend(QString myFriend, bool initialStartup){
        bool status = friendExists(myFriend);
        if (status){
            return;
        }

        friendLock.lock();
        friendList.push_back(myFriend);
        friendLock.unlock();

    }
    void saveMessage(QString s){
        messageLock.lock();

        messageLock.unlock();
    }
signals:
    void recievedNewNoti(Notification *);
    void newFriend(QString);
};

#endif // USER_H
