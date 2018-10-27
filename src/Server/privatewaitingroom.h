#ifndef PRIVATEWAITINGROOM_H
#define PRIVATEWAITINGROOM_H
#include <QObject>
#include <iostream>
#include <QString>
#include <QMutex>
using namespace std;


struct ActiveUser;


class PrivateWaitingRoom : public QObject{
    Q_OBJECT
public:
    PrivateWaitingRoom();
    PrivateWaitingRoom(ActiveUser*);
    QMutex roomLock;
    bool inGame;
    bool otherPlayerIn;
    bool timerTriggered;
    QString otherPlayerName;
public slots:
    void setOwner(ActiveUser*);
    void setOther(ActiveUser*);
    //void timerTrigger();

signals:
    void startGame(ActiveUser*, ActiveUser*);

private:
    ActiveUser *owner;
    ActiveUser *other;

};

#endif // PRIVATEWAITINGROOM_H
