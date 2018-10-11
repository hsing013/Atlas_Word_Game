#include <QCoreApplication>
#include <iostream>
#include <QString>
#include <QHash>
#include <fstream>
#include "include.h"
#include "mainwindow.h"
#include "server.h"
#include <cstdlib>
#include <ctime>
#include <QObject>

using namespace std;

QMutex userLock;
QMutex userFileLock;
QMutex friendFileLock;
QMutex queueLock;




int main(int argc, char *argv[])
{
    srand(time(0));
    QApplication a(argc, argv);

    MainWindow w;

    w.show();


    Server *s = new Server();

    s->listen();


    return a.exec();
}
