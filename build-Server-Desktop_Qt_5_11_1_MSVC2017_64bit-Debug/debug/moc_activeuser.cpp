/****************************************************************************
** Meta object code from reading C++ file 'activeuser.h'
**
** Created by: The Qt Meta Object Compiler version 67 (Qt 5.11.1)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "../../Server/activeuser.h"
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
    QByteArrayData data[17];
    char stringdata0[197];
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
QT_MOC_LITERAL(10, 110, 15), // "messageRecieved"
QT_MOC_LITERAL(11, 126, 10), // "disconnect"
QT_MOC_LITERAL(12, 137, 6), // "sendFL"
QT_MOC_LITERAL(13, 144, 11), // "sendMessage"
QT_MOC_LITERAL(14, 156, 9), // "addFriend"
QT_MOC_LITERAL(15, 166, 17), // "sendSavedMessages"
QT_MOC_LITERAL(16, 184, 12) // "timerTrigger"

    },
    "ActiveUser\0disconnectUser\0\0ActiveUser*\0"
    "newMessage\0saveUser\0messageToServer\0"
    "playGame\0removeFromQueue\0stopTimer\0"
    "messageRecieved\0disconnect\0sendFL\0"
    "sendMessage\0addFriend\0sendSavedMessages\0"
    "timerTrigger"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_ActiveUser[] = {

 // content:
       7,       // revision
       0,       // classname
       0,    0, // classinfo
      14,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       7,       // signalCount

 // signals: name, argc, parameters, tag, flags
       1,    1,   84,    2, 0x06 /* Public */,
       4,    1,   87,    2, 0x06 /* Public */,
       5,    2,   90,    2, 0x06 /* Public */,
       6,    1,   95,    2, 0x06 /* Public */,
       7,    1,   98,    2, 0x06 /* Public */,
       8,    1,  101,    2, 0x06 /* Public */,
       9,    0,  104,    2, 0x06 /* Public */,

 // slots: name, argc, parameters, tag, flags
      10,    0,  105,    2, 0x0a /* Public */,
      11,    0,  106,    2, 0x0a /* Public */,
      12,    0,  107,    2, 0x0a /* Public */,
      13,    1,  108,    2, 0x0a /* Public */,
      14,    1,  111,    2, 0x0a /* Public */,
      15,    0,  114,    2, 0x0a /* Public */,
      16,    0,  115,    2, 0x0a /* Public */,

 // signals: parameters
    QMetaType::Void, 0x80000000 | 3,    2,
    QMetaType::Void, QMetaType::QString,    2,
    QMetaType::Void, QMetaType::QString, QMetaType::QString,    2,    2,
    QMetaType::Void, QMetaType::QString,    2,
    QMetaType::Void, 0x80000000 | 3,    2,
    QMetaType::Void, 0x80000000 | 3,    2,
    QMetaType::Void,

 // slots: parameters
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void,
    QMetaType::Void, QMetaType::QString,    2,
    QMetaType::Void, QMetaType::QString,    2,
    QMetaType::Void,
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
        case 7: _t->messageRecieved(); break;
        case 8: _t->disconnect(); break;
        case 9: _t->sendFL(); break;
        case 10: _t->sendMessage((*reinterpret_cast< QString(*)>(_a[1]))); break;
        case 11: _t->addFriend((*reinterpret_cast< QString(*)>(_a[1]))); break;
        case 12: _t->sendSavedMessages(); break;
        case 13: _t->timerTrigger(); break;
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
        if (_id < 14)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 14;
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        if (_id < 14)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 14;
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
QT_WARNING_POP
QT_END_MOC_NAMESPACE
