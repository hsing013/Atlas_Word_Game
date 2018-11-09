#ifndef SERVER_H
#define SERVER_H
#include <QHash>
#include <QThread>
#include <QTcpServer>
#include <QTcpSocket>
#include <fstream>
#include <iostream>
#include <QString>
#include <QMutex>
#include <vector>
#include "include.h"
#include "activeuser.h"
#include "QSqlDatabase"
#include "QSqlQuery"
#include <QDateTime>
#include <QQueue>
#include <QSqlError>
#include <QMap>
#include <QVector>
using namespace std;






class Server : public QObject
{
    Q_OBJECT
public:
    Server();
    ~Server(){}
public slots:
    void disconnectUser(ActiveUser*);
    void newConnection();
    void listen();
    void displayMessage(QString);
    void saveUserToDB(QString, QString);
    void log(QString);
    void findPlayerToPlay(ActiveUser *user);
    void removeFromQueue(ActiveUser *user);
    void disconnectGame(Game*);
    void updatePoints(QString, QString, int points);
    void updateLeaderBoard();
    void updateFriendDB(QString, QString);
    void createGame(ActiveUser*, ActiveUser*);
signals:
    void disconnectAll();
    void leaderBoardUpdate(QString);
private:
   QTcpServer *server;
   QHash<QString, User*> *userTable;
   QSqlDatabase db;
   QQueue<ActiveUser*> queue;
   QTimer *leaderTimer;
   QString leaderBoardString;
   GameIDGen *gen;
};

#endif // SERVER_H
