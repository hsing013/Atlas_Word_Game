#ifndef ACTIVEUSER_H
#define ACTIVEUSER_H
#include <QTcpSocket>
#include <QMutex>
#include <QThread>
#include <QHash>
#include "user.h"
#include "game.h"
#include "privatewaitingroom.h"
#include <QMap>
#include <QVector>
#include <cstdlib>
#include "gameidgen.h"
#include <ctime>
using namespace std;


class Server;

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
    PrivateWaitingRoom *pRoom;
    Server *mainServer;
    ActiveUser();
    ActiveUser(QHash<QString, User*> *table);
    GameIDGen *gen;
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
    void updateFriend(QString, QString);
public slots:
    void messageRecieved();
    void disconnect();
    void sendFL();
    void sendMessage(QString);
    void addFriend(QString);
    void sendSavedMessages();
    void timerTrigger();
    void updateLeaderBoard(QString newBoard);
    void handleNotificationResponse(QString);
    void sendNotification(Notification*);
    void sendAllNotifications();
    void denyGame();
private:
    QHash<QString, User*> *userTable;


};

#endif // ACTIVEUSER_H
