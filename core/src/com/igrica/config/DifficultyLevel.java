package com.igrica.config;

public enum DifficultyLevel {
    EASY(GameConfig.EASY_OBSTACLE_SPEED),
    MEDIUM(GameConfig.MEDIUM_OBSTACLE_SPEED),
    HARD(GameConfig.HARD_OBSTACLE_SPEED);

    private final float obstacleSpreed;

    DifficultyLevel(float obstacleSpreed){
        this.obstacleSpreed = obstacleSpreed;
    }

    public float getObstacleSpreed(){
        return obstacleSpreed;
    }

    public boolean isEasy() {
        return this == EASY;
    }

    public boolean isMedium() {
        return this == MEDIUM;
    }

    public boolean isHard() {
        return this == HARD;
    }
}
