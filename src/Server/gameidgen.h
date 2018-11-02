#ifndef GAMEIDGEN_H
#define GAMEIDGEN_H
#include <QObject>
#include <cstdlib>
#include <iostream>
#include <QString>
#include <QHash>
#include <string>
using namespace std;

class GameIDGen : public QObject{
    Q_OBJECT

public:
    GameIDGen();

public slots:

    QString getGameID();
signals:

private:
    QHash<int, char> *translateTable;
};

#endif // GAMEIDGEN_H
