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
signals:
    void disconnectAll();
private:
   QTcpServer *server;
   QHash<QString, User*> *userTable;
   QSqlDatabase db;
   QQueue<ActiveUser*> queue;

};

#endif // SERVER_H
