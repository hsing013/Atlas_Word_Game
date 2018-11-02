#ifndef NOTIFICATION_H
#define NOTIFICATION_H
#include "include.h"
#include <QObject>
#include <iostream>
#include <QString>


class PrivateWaitingRoom;

using namespace std;

struct User;

class Notification
{
public:
    Notification();
    Notification(User *from, User *to, bool sender, QString type, QString ID);
    bool getSender();
    User* getFrom();
    User* getTo();
    QString getType();
    QString getID();
    void setFrom(User*);
    void setTo(User*);
    void setSender(bool);
    void setType(QString);
    void setID(QString);
private:
    bool sender;
    User *to;
    User *from;
    QString type;
    QString gameID;
};

#endif // NOTIFICATION_H
