#ifndef GAME_H
#define GAME_H
#include <QObject>
#include <QTimer>
#include <ctime>
#include <cstdlib>
#include <QMutex>
using namespace std;


struct ActiveUser;

class Game : public QObject{
    Q_OBJECT
public:
    Game();
    QThread *myThread;
    QThread *mainThread;
    ActiveUser *player1;
    ActiveUser *player2;
    ActiveUser *currentPlayer;
    bool gameDone;
    bool quitFlag;
    QTimer *timer;
    QMutex gameMutex;
public slots:
    void startGame();
    void updateGame(ActiveUser*, QString);
    void timerTrigger();
    void userDisconnected(ActiveUser*);
    void userQuit(ActiveUser*);
    void handControlToMain();
signals:
    void destroyMe(Game*);
    void changeOwnership();
    void haltTimer();
    void startTimer(int);
private:

};

#endif // GAME_H
