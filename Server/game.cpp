#include "game.h"
#include "activeuser.h"
Game::Game()
{
    myThread = NULL;
    gameDone = false;
    quitFlag = false;
    player1 = NULL;
    player2 = NULL;
    currentPlayer = NULL;
    timer = new QTimer(0);
    connect(timer, SIGNAL(timeout), this, SLOT(timerTrigger()));
}

void Game::startGame(){
    if (player1 == NULL || player2 == NULL){
        return;
    }

    if (gameDone || quitFlag){
        return;
    }

    int start = (rand() % 2) + 1;
    player1->myLock.lock();
    player2->myLock.lock();

    if (player1->disconnectFlag || player2->disconnectFlag){

        player1->g = NULL;
        player2->g = NULL;

        quitFlag = true;

        if (!player1->disconnectFlag){
            player1->newMessage("<$GAME$>$DISCONNECTED$");
        }

        if (!player2->disconnectFlag){
            player2->newMessage("<$GAME$>$DISCONNECTED$");
        }

        player1->myLock.unlock();
        player2->myLock.unlock();
        return;
    }

    if (start == 1){
        currentPlayer = player1;
        player1->newMessage("<$GAME$>$first$");
        player2->newMessage("<$GAME$>$second$");
    }
    else{
        currentPlayer = player2;
        player2->newMessage("<$GAME$>$first$");
        player1->newMessage("<$GAME$>$second$");
    }
    player1->myLock.unlock();
    player2->myLock.unlock();
    timer->start(15000);

}

void Game::timerTrigger(){
    gameMutex.lock();
    if (player1 == NULL || player2 == NULL){
        gameMutex.unlock();
        return;
    }
    player1->myLock.lock();
    player2->myLock.lock();
    timer->stop();

    if (quitFlag || gameDone){
        gameMutex.unlock();
        player1->myLock.unlock();
        player2->myLock.unlock();
        return;
    }

    gameDone = true;

    currentPlayer->newMessage("<$GAME$>$lost$");
    if (currentPlayer == player1){
        player2->newMessage("<$GAME$>$won$");
    }
    else{
        player1->newMessage("<$GAME$>$won$");
    }



    player1->myLock.unlock();
    player2->myLock.unlock();
    gameMutex.unlock();



}

void Game::updateGame(ActiveUser *u, QString word){
    gameMutex.lock();
    if (player1 == NULL || player2 == NULL){
        gameMutex.unlock();
        return;
    }
    timer->stop();
    player1->myLock.lock();
    player2->myLock.lock();

    if (player1 == u){
        currentPlayer = player2;
        player2->newMessage("<$GAME_W$>" + word);
    }
    else{
        currentPlayer = player1;
        player1->newMessage("<$GAME_W$>" + word);
    }

    player1->myLock.unlock();
    player2->myLock.unlock();
    gameMutex.unlock();
    timer->start(15000);

}

void Game::userDisconnected(ActiveUser *user){
    gameMutex.lock();
    if (player1 == NULL || player2 == NULL){
        gameMutex.unlock();
        return;
    }
    timer->stop();
    player1->myLock.lock();
    player2->myLock.lock();

    player1->inGame = false;
    player2->inGame = false;

    if (user == player1){
        player2->newMessage("<$GAME$>$won2$");
    }
    else{
        player1->newMessage("<$GAME$>$won2$");
    }
    player1->g = NULL;
    player2->g = NULL;

    player1 = NULL;
    player2 = NULL;

    player1->myLock.unlock();
    player2->myLock.unlock();
    gameMutex.unlock();

    emit destroyMe(this);


}

void Game::userQuit(ActiveUser *user){
    gameMutex.lock();
    if (player1 == NULL || player2 == NULL){
        gameMutex.unlock();
        return;
    }
    player1->myLock.lock();
    player2->myLock.lock();

    player1->inGame = false;
    player2->inGame = false;

    if (user == player1){
        player2->newMessage("<$GAME$>$won3$");
    }
    else{
        player1->newMessage("<$GAME$>$won3$");
    }
    player1->g = NULL;
    player2->g = NULL;

    player1 = NULL;
    player2 = NULL;

    player1->myLock.unlock();
    player2->myLock.unlock();
    gameMutex.unlock();

    emit destroyMe(this);


}
