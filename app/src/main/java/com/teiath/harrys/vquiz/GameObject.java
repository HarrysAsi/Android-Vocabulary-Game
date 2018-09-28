package com.teiath.harrys.vquiz;

import java.util.ArrayList;

/**
 * Created by harrys on 30/1/2018.
 */

public class GameObject {
    private int maxAttempts;
    private int maxTime;
    private int maxQuestions;
    private int gameMode;
    private ArrayList<String> data;

    public GameObject(int maxAttempts, int maxTime, int maxQuestions, int gameMode, ArrayList<String> data) {
        this.maxAttempts = maxAttempts;
        this.maxTime = maxTime;
        this.maxQuestions = maxQuestions;
        this.gameMode = gameMode;
        this.data = data;
    }


    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public int getMaxQuestions() {
        return maxQuestions;
    }

    public int getGameMode() {
        return gameMode;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public void setMaxQuestions(int maxQuestions) {
        this.maxQuestions = maxQuestions;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }
}
