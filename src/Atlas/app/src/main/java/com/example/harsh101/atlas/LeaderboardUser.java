package com.example.harsh101.atlas;

public class LeaderboardUser extends Person {
    private int achievements;

    public LeaderboardUser() {
    }

    public LeaderboardUser(String newName, int newPoints, int newAchievements) {
        super(newName, newPoints);
        achievements = newAchievements;
    }
}
