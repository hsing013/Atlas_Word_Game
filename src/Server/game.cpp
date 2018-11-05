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
    connect(timer, SIGNAL(timeout()), this, SLOT(timerTrigger()));
    connect(this, SIGNAL(haltTimer()), timer, SLOT(stop()));
    connect(this, SIGNAL(startTimer(int)), timer, SLOT(start(int)));
}

void Game::startGame(){
    gameMutex.lock();
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

        player1 = NULL;
        player2 = NULL;

        gameMutex.unlock();

        emit changeOwnership();


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
    player1->newMessage("<$GAME$>$startTimer$");
    player2->newMessage("<$GAME$>$startTimer$");
    player1->myLock.unlock();
    player2->myLock.unlock();
    gameMutex.unlock();
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


    if (quitFlag || gameDone){
        gameMutex.unlock();
        player1->myLock.unlock();
        player2->myLock.unlock();
        return;
    }

    player1->g = NULL;
    player2->g = NULL;
    player1->inGame = false;
    player2->inGame = false;
    gameDone = true;

    currentPlayer->newMessage("<$GAME$>$lost$");
    if (currentPlayer == player1){
        player2->newMessage("<$GAME$>$won$");
        userLock.lock();
        player2->user->addPoints(100);
        userLock.unlock();
        player2->updatePoints(player2->user->userName, player2->user->pass, player2->user->points);
    }
    else{
        player1->newMessage("<$GAME$>$won$");
        userLock.lock();
        player1->user->addPoints(100);
        userLock.unlock();
        player1->updatePoints(player1->user->userName, player1->user->pass, player1->user->points);
    }



    player1->myLock.unlock();
    player2->myLock.unlock();

    player1 = NULL;
    player2 = NULL;


    gameMutex.unlock();

    emit changeOwnership();




}

void Game::updateGame(ActiveUser *u, QString word){
    gameMutex.lock();
    if (player1 == NULL || player2 == NULL){
        gameMutex.unlock();
        return;
    }
    emit haltTimer();
    player1->myLock.lock();
    player2->myLock.lock();
    if (player1 == u){
        currentPlayer = player2;
        player2->newMessage("<$GAMEW$>" + word);
    }
    else{
        currentPlayer = player1;
        player1->newMessage("<$GAMEW$>" + word);
    }
    player1->newMessage("<$GAME$>startTimer");
    player2->newMessage("<$GAME$>startTimer");
    player1->myLock.unlock();
    player2->myLock.unlock();
    gameMutex.unlock();
    emit startTimer(15000);

}

void Game::userDisconnected(ActiveUser *user){
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
        player2->newMessage("<$GAME$>$won2$");
        userLock.lock();
        player2->user->addPoints(50);
        userLock.unlock();
        player2->updatePoints(player2->user->userName, player2->user->pass, player2->user->points);
    }
    else{
        player1->newMessage("<$GAME$>$won2$");
        userLock.lock();
        player1->user->addPoints(50);
        userLock.unlock();
        player1->updatePoints(player1->user->userName, player1->user->pass, player1->user->points);
    }
    player1->g = NULL;
    player2->g = NULL;



    player1->myLock.unlock();
    player2->myLock.unlock();
    player1 = NULL;
    player2 = NULL;
    gameMutex.unlock();

    emit changeOwnership();


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
        userLock.lock();
        player2->user->addPoints(50);
        userLock.unlock();
        player2->updatePoints(player2->user->userName, player2->user->pass, player2->user->points);
    }
    else{
        player1->newMessage("<$GAME$>$won3$");
        userLock.lock();
        player1->user->addPoints(50);
        userLock.unlock();
        player1->updatePoints(player1->user->userName, player1->user->pass, player1->user->points);
    }
    player1->g = NULL;
    player2->g = NULL;


    player1->myLock.unlock();
    player2->myLock.unlock();
    player1 = NULL;
    player2 = NULL;

    gameMutex.unlock();

    emit changeOwnership();



}

void Game::handControlToMain(){
    if (timer->isActive()){
        timer->stop();
    }
    timer->moveToThread(mainThread);
    this->moveToThread(mainThread);
    emit destroyMe(this);
}


