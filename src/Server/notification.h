#ifndef NOTIFICATION_H
#define NOTIFICATION_H
#include "include.h"
#include <QObject>
#include <iostream>
#include <QString>
using namespace std;

class Notification
{
public:
    Notification();
    Notification(QString from, QString to, bool sender, QString type);
    bool getSender();
    QString getFrom();
    QString getTo();
    QString getType();
    void setFrom(QString);
    void setTo(QString);
    void setSender(bool);
    void setType(QString);

private:
    bool sender;
    QString to;
    QString from;
    QString type;
};

#endif // NOTIFICATION_H
