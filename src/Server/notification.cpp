#include "notification.h"
#include "user.h"

Notification::Notification()
{
    to = NULL;
    from = NULL;
    sender = false;
    type = "";
    gameID = "";
}

Notification::Notification(User *from, User *to, bool sender, QString type, QString ID){
    this->to = to;
    this->from = from;
    this->sender = sender;
    this->type = type;
    this->gameID = ID;
}

bool Notification::getSender(){
    return sender;
}
User* Notification::getFrom(){
    return from;
}
User* Notification::getTo(){
    return to;
}
QString Notification::getType(){
    return type;
}
QString Notification::getID(){
    return gameID;
}
void Notification::setFrom(User *from){
    this->from = from;
}
void Notification::setTo(User *to){
    this->to = to;
}
void Notification::setSender(bool sender){
    this->sender = sender;
}
void Notification::setType(QString type){
    this->type = type;
}
void Notification::setID(QString ID){
    gameID = ID;
}

