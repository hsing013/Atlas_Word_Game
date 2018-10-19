package com.example.harsh101.atlas;

public class Person {
    protected String name;
    protected int numPoints;

    public Person() {
        name = null;
        numPoints = 0;


    }

    public Person(String newName, int newPoints){
        name = newName;
        numPoints = newPoints;
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



}
