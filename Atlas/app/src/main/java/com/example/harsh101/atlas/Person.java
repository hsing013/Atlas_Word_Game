package com.example.harsh101.atlas;

public class Person {
    protected String name;
    protected int numPoints;
    protected int numAchievements;

    public Person() {
        name = null;
        numPoints = 0;
        numAchievements = 0;

    }

    public Person(String newName, int newPoints, int newAchievements){
        name = newName;
        numPoints = newPoints;
        numAchievements = newAchievements;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumPoints() {
        return numPoints;
    }

    public void setNumPoints(int numPoints) {
        this.numPoints = numPoints;
    }

    public int getNumAchievements() {
        return numAchievements;
    }

    public void setNumAchievements(int numAchievements) {
        this.numAchievements = numAchievements;
    }


}
