package com.example.harsh101.atlas;

public class Friend extends Person {
    protected int numTimesPlayedWith;
    protected int numOfAchievements;

    public Friend()
    {
        numTimesPlayedWith = 0;
        numOfAchievements = 0;
    }

    public Friend(int newNumPlayedWith, int newNumOfAchievements) {
        numTimesPlayedWith = newNumPlayedWith;
        numOfAchievements = newNumOfAchievements;
    }


    public int getNumTimesPlayedWith() {
        return numTimesPlayedWith;
    }

    public void setNumTimesPlayedWith(int numTimesPlayedWith) {
        this.numTimesPlayedWith = numTimesPlayedWith;
    }

    public int getNumOfAchievements() {
        return numOfAchievements;
    }

    public void setNumOfAchievements(int numOfAchievements) {
        this.numOfAchievements = numOfAchievements;
    }
}
