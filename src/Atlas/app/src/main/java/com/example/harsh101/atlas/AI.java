package com.example.harsh101.atlas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

public class AI {
    public Hashtable<String, String> easy = null;
    public Hashtable<String, String> medium = null;
    public Hashtable<String, String> hard = null;
    public String currentGameDifficulty = null;
    public ArrayList<Integer> alphabet = null;

    public AI(){

    }

    public void startGame(String difficulty){
        currentGameDifficulty = difficulty;
        alphabet = new ArrayList<>();
        for (int i = 0; i < 26; ++i){
            alphabet.add(0);
        }
    }

    public String getWord(char c){
        int index = -1;
        if (c == 'a'){
            index = 1;
        }
        else if (c == 'b'){
            index = 2;
        }
        else if (c == 'c'){
            index = 3;
        }
        else if (c == 'd'){
            index = 4;
        }
        else if (c == 'e'){
            System.out.println("e -----------------------");
            index = 5;
        }
        else if (c == 'f'){
            index = 6;
        }
        else if (c == 'g'){
            index = 7;
        }
        else if (c == 'h'){
            index = 8;
        }
        else if (c == 'i'){
            index = 9;
        }
        else if (c == 'j'){
            index = 10;
        }
        else if (c == 'k'){
            index = 11;
        }
        else if (c == 'l'){
            index = 12;
        }
        else if (c == 'm'){
            index = 13;
        }
        else if (c == 'n'){
            index = 14;
        }
        else if (c == 'o'){
            index = 15;
        }
        else if (c == 'p'){
            index = 16;
        }
        else if (c == 'q'){
            index = 17;
        }
        else if (c == 'r'){
            index = 18;
        }
        else if (c == 's'){
            index = 19;
        }
        else if (c == 't'){
            index = 20;
        }
        else if (c == 'u'){
            index = 21;
        }
        else if (c == 'v'){
            index = 22;
        }
        else if (c == 'w'){
            index = 23;
        }
        else if (c == 'x'){
            index = 24;
        }
        else if (c == 'y'){
            index = 25;
        }
        else if (c == 'z'){
            index = 26;
        }

        if (currentGameDifficulty.compareTo("EASY") == 0){
            int i = alphabet.get(index - 1).intValue();
            if (i == 100){
                System.out.print("HELLO");
                return null;
            }
            String s1 = c + Integer.toString(i);
            System.out.print(s1);
            i = i + 1;
            alphabet.set(index - 1, new Integer(i));
            return easy.get(s1);
        }
        else if (currentGameDifficulty.compareTo("MEDIUM") == 0){
            int i = alphabet.get(index - 1).intValue();
            if (i == 250){
                return null;
            }
            String s1 = c + Integer.toString(i);
            i = i + 1;
            alphabet.set(index - 1, new Integer(i));
            return medium.get(s1);
        }
        else if (currentGameDifficulty.compareTo("HARD") == 0){
            int i = alphabet.get(index - 1).intValue();
            if (i == 500){
                return null;
            }
            String s1 = c + Integer.toString(i);
            i = i + 1;
            alphabet.set(index - 1, new Integer(i));
            return medium.get(s1);
        }



        return null;
    }

    public void reset(){
        currentGameDifficulty = null;
        alphabet = null;
    }








}
