#include "privatewaitingroom.h"
#include "activeuser.h"

PrivateWaitingRoom::PrivateWaitingRoom(){
    owner = NULL;
    other = NULL;
    inGame = false;
    otherPlayerIn = false;
    timerTriggered = false;
}

PrivateWaitingRoom::PrivateWaitingRoom(ActiveUser *owner){
    this->owner = owner;
    other = NULL;
    inGame = false;
    otherPlayerIn = false;
    timerTriggered = false;
}

void PrivateWaitingRoom::setOwner(ActiveUser *owner){
    this->owner = owner;
}

void PrivateWaitingRoom::setOther(ActiveUser *other){
    this->other = other;
    otherPlayerIn = true;
    emit startGame(owner, other);
}


