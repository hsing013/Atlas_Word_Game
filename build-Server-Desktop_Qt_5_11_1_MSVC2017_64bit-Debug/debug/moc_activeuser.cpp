/****************************************************************************
** Meta object code from reading C++ file 'activeuser.h'
**
** Created by: The Qt Meta Object Compiler version 67 (Qt 5.11.1)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "../../src/Server/activeuser.h"
#include <QtCore/qbytearray.h>
#include <QtCore/qmetatype.h>
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'activeuser.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 67
#error "This file was generated using the moc from 5.11.1. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
QT_WARNING_PUSH
QT_WARNING_DISABLE_DEPRECATED
struct qt_meta_stringdata_ActiveUser_t {
    QByteArrayData data[25];
    char stringdata0[329];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_ActiveUser_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_ActiveUser_t qt_meta_stringdata_ActiveUser = {
    {
QT_MOC_LITERAL(0, 0, 10), // "ActiveUser"
QT_MOC_LITERAL(1, 11, 14), // "disconnectUser"
QT_MOC_LITERAL(2, 26, 0), // ""
QT_MOC_LITERAL(3, 27, 11), // "ActiveUser*"
QT_MOC_LITERAL(4, 39, 10), // "newMessage"
QT_MOC_LITERAL(5, 50, 8), // "saveUser"
QT_MOC_LITERAL(6, 59, 15), // "messageToServer"
QT_MOC_LITERAL(7, 75, 8), // "playGame"
QT_MOC_LITERAL(8, 84, 15), // "removeFromQueue"
QT_MOC_LITERAL(9, 100, 9), // "stopTimer"
QT_MOC_LITERAL(10, 110, 12), // "updatePoints"
QT_MOC_LITERAL(11, 123, 12), // "updateFriend"
QT_MOC_LITERAL(12, 136, 15), // "messageRecieved"
QT_MOC_LITERAL(13, 152, 10), // "disconnect"
QT_MOC_LITERAL(14, 163, 6), // "sendFL"
QT_MOC_LITERAL(15, 170, 11), // "sendMessage"
QT_MOC_LITERAL(16, 182, 9), // "addFriend"
QT_MOC_LITERAL(17, 192, 17), // "sendSavedMessages"
QT_MOC_LITERAL(18, 210, 12), // "timerTrigger"
QT_MOC_LITERAL(19, 223, 17), // "updateLeaderBoard"
QT_MOC_LITERAL(20, 241, 8), // "newBoard"
QT_MOC_LITERAL(21, 250, 26), // "handleNotificationResponse"
QT_MOC_LITERAL(22, 277, 16), // "sendNotification"
QT_MOC_LITERAL(23, 294, 13), // "Notification*"
QT_MOC_LITERAL(24, 308, 20) // "sendAllNotifications"

    },
    "ActiveUser\0disconnectUser\0\0ActiveUser*\0"
    "newMessage\0saveUser\0messageToServer\0"
    "playGame\0removeFromQueue\0stopTimer\0"
    "updatePoints\0updateFriend\0messageRecieved\0"
    "disconnect\0sendFL\0sendMessage\0addFriend\0"
    "sendSavedMessages\0timerTrigger\0"
    "updateLeaderBoard\0newBoard\0"
    "handleNotificationResponse\0sendNotification\0"
    "Notification*\0sendAllNotifications"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_ActiveUser[] = {

 // content:
       7,       // revision
       0,       // classname
       0,    0, // classinfo
      20,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       9,       // signalCount

 // signals: name, argc, parameters, tag, flags
       1,    1,  114,    2, 0x06 /* Public */,
       4,    1,  117,    2, 0x06 /* Public */,
       5,    2,  120,    2, 0x06 /* Public */,
       6,    1,  125,    2, 0x06 /* Public */,
       7,    1,  128,    2, 0x06 /* Public */,
       8,    1,  131,    2, 0x06 /* Public */,
       9,    0,  134,    2, 0x06 /* Public */,
      10,    3,  135,    2, 0x06 /* Public */,
      11,    2,  142,    2, 0x06 /* Public */,

 // slots: name, argc, parameters, tag, flags
      12,    0,  147,    2, 0x0a /* Public */,
      13,    0,  148,    2, 0x0a /* Public */,
      14,    0,  149,    2, 0x0a /* Public */,
      15,    1,  150,    2, 0x0a /* Public */,
      16,    1,  153,    2, 0x0a /* Public */,
      17,    0,  156,    2, 0x0a /* Public */,
      18,    0,  157,    2, 0x0a /* Public */,
      19,    1,  158,    2, 0x0a /* Public */,
      21,    1,  161,    2, 0x0a /* Public */,
      22,    1,  164,    2, 0x0a /* Public */,
      24,    0,  167,    2, 0x0a /* Public */,

 // signals: parameters
    QMetaType::Void, 0x80000000 | 3,    2,
    QMetaType::Void, QMetaType::QString,    2,
    QMetaType::Void, QMetaType::QString, QMetaType::QString,    2,    2,
    QMetaType::Void, QMetaType::QString,    2,
    QMetaType::Void, 0x80000000 | 3,    2,
    QMetaType::Void, 0x80000000 | 3,    2,
    QMetaType::Void,
    QMetaType::Void, QMetaType::QString, QMetaType::QString, QMetaType::Int,    2,    2,    2,
    QMetaType::Void, QMetaType::QString, QMetaType::QString,    2,    2,

 // slots: parameters
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void, QMetaType::QString,    2,
    QMetaType::Void, QMetaType::QString,    2,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void, QMetaType::QString,   20,
    QMetaType::Void, QMetaType::QString,    2,
    QMetaType::Void, 0x80000000 | 23,    2,
    QMetaType::Void,

       0        // eod
};

void ActiveUser::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        ActiveUser *_t = static_cast<ActiveUser *>(_o);
        Q_UNUSED(_t)
        switch (_id) {
        case 0: _t->disconnectUser((*reinterpret_cast< ActiveUser*(*)>(_a[1]))); break;
        case 1: _t->newMessage((*reinterpret_cast< QString(*)>(_a[1]))); break;
        case 2: _t->saveUser((*reinterpret_cast< QString(*)>(_a[1])),(*reinterpret_cast< QString(*)>(_a[2]))); break;
        case 3: _t->messageToServer((*reinterpret_cast< QString(*)>(_a[1]))); break;
        case 4: _t->playGame((*reinterpret_cast< ActiveUser*(*)>(_a[1]))); break;
        case 5: _t->removeFromQueue((*reinterpret_cast< ActiveUser*(*)>(_a[1]))); break;
        case 6: _t->stopTimer(); break;
        case 7: _t->updatePoints((*reinterpret_cast< QString(*)>(_a[1])),(*reinterpret_cast< QString(*)>(_a[2])),(*reinterpret_cast< int(*)>(_a[3]))); break;
        case 8: _t->updateFriend((*reinterpret_cast< QString(*)>(_a[1])),(*reinterpret_cast< QString(*)>(_a[2]))); break;
        case 9: _t->messageRecieved(); break;
        case 10: _t->disconnect(); break;
        case 11: _t->sendFL(); break;
        case 12: _t->sendMessage((*reinterpret_cast< QString(*)>(_a[1]))); break;
        case 13: _t->addFriend((*reinterpret_cast< QString(*)>(_a[1]))); break;
        case 14: _t->sendSavedMessages(); break;
        case 15: _t->timerTrigger(); break;
        case 16: _t->updateLeaderBoard((*reinterpret_cast< QString(*)>(_a[1]))); break;
        case 17: _t->handleNotificationResponse((*reinterpret_cast< QString(*)>(_a[1]))); break;
        case 18: _t->sendNotification((*reinterpret_cast< Notification*(*)>(_a[1]))); break;
        case 19: _t->sendAllNotifications(); break;
        default: ;
        }
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        switch (_id) {
        default: *reinterpret_cast<int*>(_a[0]) = -1; break;
        case 0:
            switch (*reinterpret_cast<int*>(_a[1])) {
            default: *reinterpret_cast<int*>(_a[0]) = -1; break;
            case 0:
                *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< ActiveUser* >(); break;
            }
            break;
        case 4:
            switch (*reinterpret_cast<int*>(_a[1])) {
            default: *reinterpret_cast<int*>(_a[0]) = -1; break;
            case 0:
                *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< ActiveUser* >(); break;
            }
            break;
        case 5:
            switch (*reinterpret_cast<int*>(_a[1])) {
            default: *reinterpret_cast<int*>(_a[0]) = -1; break;
            case 0:
                *reinterpret_cast<int*>(_a[0]) = qRegisterMetaType< ActiveUser* >(); break;
            }
            break;
        }
    } else if (_c == QMetaObject::IndexOfMethod) {
        int *result = reinterpret_cast<int *>(_a[0]);
        {
            using _t = void (ActiveUser::*)(ActiveUser * );
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&ActiveUser::disconnectUser)) {
                *result = 0;
                return;
            }
        }
        {
            using _t = void (ActiveUser::*)(QString );
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&ActiveUser::newMessage)) {
                *result = 1;
                return;
            }
        }
        {
            using _t = void (ActiveUser::*)(QString , QString );
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&ActiveUser::saveUser)) {
                *result = 2;
                return;
            }
        }
        {
            using _t = void (ActiveUser::*)(QString );
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&ActiveUser::messageToServer)) {
                *result = 3;
                return;
            }
        }
        {
            using _t = void (ActiveUser::*)(ActiveUser * );
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&ActiveUser::playGame)) {
                *result = 4;
                return;
            }
        }
        {
            using _t = void (ActiveUser::*)(ActiveUser * );
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&ActiveUser::removeFromQueue)) {
                *result = 5;
                return;
            }
        }
        {
            using _t = void (ActiveUser::*)();
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&ActiveUser::stopTimer)) {
                *result = 6;
                return;
            }
        }
        {
            using _t = void (ActiveUser::*)(QString , QString , int );
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&ActiveUser::updatePoints)) {
                *result = 7;
                return;
            }
        }
        {
            using _t = void (ActiveUser::*)(QString , QString );
            if (*reinterpret_cast<_t *>(_a[1]) == static_cast<_t>(&ActiveUser::updateFriend)) {
                *result = 8;
                return;
            }
        }
    }
}

QT_INIT_METAOBJECT const QMetaObject ActiveUser::staticMetaObject = {
    { &QObject::staticMetaObject, qt_meta_stringdata_ActiveUser.data,
      qt_meta_data_ActiveUser,  qt_static_metacall, nullptr, nullptr}
};


const QMetaObject *ActiveUser::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *ActiveUser::qt_metacast(const char *_clname)
{
    if (!_clname) return nullptr;
    if (!strcmp(_clname, qt_meta_stringdata_ActiveUser.stringdata0))
        return static_cast<void*>(this);
    return QObject::qt_metacast(_clname);
}

int ActiveUser::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QObject::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 20)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 20;
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        if (_id < 20)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 20;
    }
    return _id;
}

// SIGNAL 0
void ActiveUser::disconnectUser(ActiveUser * _t1)
{
    void *_a[] = { nullptr, const_cast<void*>(reinterpret_cast<const void*>(&_t1)) };
    QMetaObject::activate(this, &staticMetaObject, 0, _a);
}

// SIGNAL 1
void ActiveUser::newMessage(QString _t1)
{
    void *_a[] = { nullptr, const_cast<void*>(reinterpret_cast<const void*>(&_t1)) };
    QMetaObject::activate(this, &staticMetaObject, 1, _a);
}

// SIGNAL 2
void ActiveUser::saveUser(QString _t1, QString _t2)
{
    void *_a[] = { nullptr, const_cast<void*>(reinterpret_cast<const void*>(&_t1)), const_cast<void*>(reinterpret_cast<const void*>(&_t2)) };
    QMetaObject::activate(this, &staticMetaObject, 2, _a);
}

// SIGNAL 3
void ActiveUser::messageToServer(QString _t1)
{
    void *_a[] = { nullptr, const_cast<void*>(reinterpret_cast<const void*>(&_t1)) };
    QMetaObject::activate(this, &staticMetaObject, 3, _a);
}

// SIGNAL 4
void ActiveUser::playGame(ActiveUser * _t1)
{
    void *_a[] = { nullptr, const_cast<void*>(reinterpret_cast<const void*>(&_t1)) };
    QMetaObject::activate(this, &staticMetaObject, 4, _a);
}

// SIGNAL 5
void ActiveUser::removeFromQueue(ActiveUser * _t1)
{
    void *_a[] = { nullptr, const_cast<void*>(reinterpret_cast<const void*>(&_t1)) };
    QMetaObject::activate(this, &staticMetaObject, 5, _a);
}

// SIGNAL 6
void ActiveUser::stopTimer()
{
    QMetaObject::activate(this, &staticMetaObject, 6, nullptr);
}

// SIGNAL 7
void ActiveUser::updatePoints(QString _t1, QString _t2, int _t3)
{
    void *_a[] = { nullptr, const_cast<void*>(reinterpret_cast<const void*>(&_t1)), const_cast<void*>(reinterpret_cast<const void*>(&_t2)), const_cast<void*>(reinterpret_cast<const void*>(&_t3)) };
    QMetaObject::activate(this, &staticMetaObject, 7, _a);
}

// SIGNAL 8
void ActiveUser::updateFriend(QString _t1, QString _t2)
{
    void *_a[] = { nullptr, const_cast<void*>(reinterpret_cast<const void*>(&_t1)), const_cast<void*>(reinterpret_cast<const void*>(&_t2)) };
    QMetaObject::activate(this, &staticMetaObject, 8, _a);
}
QT_WARNING_POP
QT_END_MOC_NAMESPACE
