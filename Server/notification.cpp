#include "notification.h"

Notification::Notification()
{
    to = "";
    from = "";
    sender = false;
    type = "";
}

Notification::Notification(QString from, QString to, bool sender, QString type){
    this->to = to;
    this->from = from;
    this->sender = sender;
    this->type = type;
}

bool Notification::getSender(){
    return sender;
}
QString Notification::getFrom(){
    return from;
}
QString Notification::getTo(){
    return to;
}
QString Notification::getType(){
    return type;
}
void Notification::setFrom(QString from){
    this->from = from;
}
void Notification::setTo(QString to){
    this->to = to;
}
void Notification::setSender(bool sender){
    this->sender = sender;
}
void Notification::setType(QString type){
    this->type = type;
}

