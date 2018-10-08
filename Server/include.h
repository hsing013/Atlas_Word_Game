#ifndef INCLUDE_H
#define INCLUDE_H
#include <QMutex>

extern QMutex userLock;
extern QMutex friendFileLock;
extern QMutex userFileLock;
extern QMutex queueLock;

#endif // INCLUDE_H
