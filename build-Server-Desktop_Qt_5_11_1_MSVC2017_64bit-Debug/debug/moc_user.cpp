/****************************************************************************
** Meta object code from reading C++ file 'user.h'
**
** Created by: The Qt Meta Object Compiler version 67 (Qt 5.11.1)
**
** WARNING! All changes made in this file will be lost!
*****************************************************************************/

#include "../../Server/user.h"
#include <QtCore/qbytearray.h>
#include <QtCore/qmetatype.h>
#if !defined(Q_MOC_OUTPUT_REVISION)
#error "The header file 'user.h' doesn't include <QObject>."
#elif Q_MOC_OUTPUT_REVISION != 67
#error "This file was generated using the moc from 5.11.1. It"
#error "cannot be used with the include files from this version of Qt."
#error "(The moc has changed too much.)"
#endif

QT_BEGIN_MOC_NAMESPACE
QT_WARNING_PUSH
QT_WARNING_DISABLE_DEPRECATED
struct qt_meta_stringdata_User_t {
    QByteArrayData data[11];
    char stringdata0[92];
};
#define QT_MOC_LITERAL(idx, ofs, len) \
    Q_STATIC_BYTE_ARRAY_DATA_HEADER_INITIALIZER_WITH_OFFSET(len, \
    qptrdiff(offsetof(qt_meta_stringdata_User_t, stringdata0) + ofs \
        - idx * sizeof(QByteArrayData)) \
    )
static const qt_meta_stringdata_User_t qt_meta_stringdata_User = {
    {
QT_MOC_LITERAL(0, 0, 4), // "User"
QT_MOC_LITERAL(1, 5, 7), // "setUser"
QT_MOC_LITERAL(2, 13, 0), // ""
QT_MOC_LITERAL(3, 14, 11), // "ActiveUser*"
QT_MOC_LITERAL(4, 26, 4), // "user"
QT_MOC_LITERAL(5, 31, 12), // "friendExists"
QT_MOC_LITERAL(6, 44, 8), // "myFriend"
QT_MOC_LITERAL(7, 53, 9), // "addFriend"
QT_MOC_LITERAL(8, 63, 14), // "initialStartup"
QT_MOC_LITERAL(9, 78, 11), // "saveMessage"
QT_MOC_LITERAL(10, 90, 1) // "s"

    },
    "User\0setUser\0\0ActiveUser*\0user\0"
    "friendExists\0myFriend\0addFriend\0"
    "initialStartup\0saveMessage\0s"
};
#undef QT_MOC_LITERAL

static const uint qt_meta_data_User[] = {

 // content:
       7,       // revision
       0,       // classname
       0,    0, // classinfo
       4,   14, // methods
       0,    0, // properties
       0,    0, // enums/sets
       0,    0, // constructors
       0,       // flags
       0,       // signalCount

 // slots: name, argc, parameters, tag, flags
       1,    1,   34,    2, 0x0a /* Public */,
       5,    1,   37,    2, 0x0a /* Public */,
       7,    2,   40,    2, 0x0a /* Public */,
       9,    1,   45,    2, 0x0a /* Public */,

 // slots: parameters
    QMetaType::Bool, 0x80000000 | 3,    4,
    QMetaType::Bool, QMetaType::QString,    6,
    QMetaType::Void, QMetaType::QString, QMetaType::Bool,    6,    8,
    QMetaType::Void, QMetaType::QString,   10,

       0        // eod
};

void User::qt_static_metacall(QObject *_o, QMetaObject::Call _c, int _id, void **_a)
{
    if (_c == QMetaObject::InvokeMetaMethod) {
        User *_t = static_cast<User *>(_o);
        Q_UNUSED(_t)
        switch (_id) {
        case 0: { bool _r = _t->setUser((*reinterpret_cast< ActiveUser*(*)>(_a[1])));
            if (_a[0]) *reinterpret_cast< bool*>(_a[0]) = std::move(_r); }  break;
        case 1: { bool _r = _t->friendExists((*reinterpret_cast< QString(*)>(_a[1])));
            if (_a[0]) *reinterpret_cast< bool*>(_a[0]) = std::move(_r); }  break;
        case 2: _t->addFriend((*reinterpret_cast< QString(*)>(_a[1])),(*reinterpret_cast< bool(*)>(_a[2]))); break;
        case 3: _t->saveMessage((*reinterpret_cast< QString(*)>(_a[1]))); break;
        default: ;
        }
    }
}

QT_INIT_METAOBJECT const QMetaObject User::staticMetaObject = {
    { &QObject::staticMetaObject, qt_meta_stringdata_User.data,
      qt_meta_data_User,  qt_static_metacall, nullptr, nullptr}
};


const QMetaObject *User::metaObject() const
{
    return QObject::d_ptr->metaObject ? QObject::d_ptr->dynamicMetaObject() : &staticMetaObject;
}

void *User::qt_metacast(const char *_clname)
{
    if (!_clname) return nullptr;
    if (!strcmp(_clname, qt_meta_stringdata_User.stringdata0))
        return static_cast<void*>(this);
    return QObject::qt_metacast(_clname);
}

int User::qt_metacall(QMetaObject::Call _c, int _id, void **_a)
{
    _id = QObject::qt_metacall(_c, _id, _a);
    if (_id < 0)
        return _id;
    if (_c == QMetaObject::InvokeMetaMethod) {
        if (_id < 4)
            qt_static_metacall(this, _c, _id, _a);
        _id -= 4;
    } else if (_c == QMetaObject::RegisterMethodArgumentMetaType) {
        if (_id < 4)
            *reinterpret_cast<int*>(_a[0]) = -1;
        _id -= 4;
    }
    return _id;
}
QT_WARNING_POP
QT_END_MOC_NAMESPACE
