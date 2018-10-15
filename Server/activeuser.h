#ifndef ACTIVEUSER_H
#define ACTIVEUSER_H
#include <QTcpSocket>
#include <QMutex>
#include <QThread>
#include <QHash>
#include "user.h"
#include "game.h"
#include <QMap>
#include <QVector>
using namespace std;




struct ActiveUser : public QObject
{
    Q_OBJECT
public:
    QTimer *waitTimer;
    QTcpSocket *socket;
    QThread *thread;
    QMutex myLock;
    QString message;
    User *user;
    bool disconnectFlag;
    bool inGame;
    bool waitingRoom;
    bool inQueue;
    Game *g;
    QThread *mainThread;
    QString leaderBoard;
    ActiveUser();
    ActiveUser(QHash<QString, User*> *table);
    ~ActiveUser();

signals:
    void disconnectUser(ActiveUser*);
    void newMessage(QString);
    void saveUser(QString, QString);
    void messageToServer(QString);
    void playGame(ActiveUser*);
    void removeFromQueue(ActiveUser*);
    void stopTimer();
    void updatePoints(QString, QString, int);
public slots:
    void messageRecieved();
    void disconnect();
    void sendFL();
    void sendMessage(QString);
    void addFriend(QString);
    void sendSavedMessages();
    void timerTrigger();
    void updateLeaderBoard(QString newBoard);

private:
    QHash<QString, User*> *userTable;


};

#endif // ACTIVEUSER_H
