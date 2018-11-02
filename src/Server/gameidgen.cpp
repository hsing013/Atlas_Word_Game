#include "gameidgen.h"

GameIDGen::GameIDGen()
{
    translateTable = new QHash<int, char>();

    int c = 48;

    for (int i = 0; i < 62; ++i){
        if (i == 10){
            c = 65;
            translateTable->insert(i, (char) c);
        }
        else if (i == 36){
            c = 97;
            translateTable->insert(i, (char) c);
        }
        else {
            translateTable->insert(i, (char) c);
        }
        ++c;
    }


}

QString GameIDGen::getGameID(){
    string tag = "";

    for (int i = 0; i < 10; ++i){
        int r = rand() % 62;
        tag += translateTable->value(r);
    }

    return QString::fromStdString(tag);


}
