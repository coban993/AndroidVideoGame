package com.igrica.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.igrica.GameG;
import com.igrica.config.DifficultyLevel;

public class GameManager {

    public static final GameManager instance = new GameManager();

    private static final String HIGH_SCORE_KEY = "highscore";
    private static final String DIFFICULTY_KEY = "difficulty";

    private Preferences prefs;
    private int highScore = 0;
    private DifficultyLevel difficultyLevel = DifficultyLevel.MEDIUM;

    private GameManager() {
        prefs = Gdx.app.getPreferences(GameG.class.getSimpleName());
        highScore = prefs.getInteger(HIGH_SCORE_KEY, 0);
        String difficultyName = prefs.getString(DIFFICULTY_KEY, DifficultyLevel.MEDIUM.name());
        difficultyLevel = DifficultyLevel.valueOf(difficultyName);
    }

    public String getHighScoreString() {
        return String.valueOf(highScore);
    }

    public void updateHighScore(int score) {
        if(score < highScore) return;

        highScore = score;
        prefs.putInteger(HIGH_SCORE_KEY, highScore);
        prefs.flush();
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void updateDifficulty(DifficultyLevel newDifficultyLevel) {
        if(newDifficultyLevel == difficultyLevel) return;

        difficultyLevel = newDifficultyLevel;
        prefs.putString(DIFFICULTY_KEY, difficultyLevel.name());
        prefs.flush();
    }
}
