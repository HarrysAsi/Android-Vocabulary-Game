package com.teiath.harrys.vquiz;

import com.teiath.harrys.vquiz.ScoreObject;

import java.util.ArrayList;

/**
 * Created by harrys on 2/2/2018.
 */

public class DatabaseGlobalData {

    public ArrayList<ScoreObject> scores;

    public DatabaseGlobalData() {
        scores = new ArrayList<>();
    }

    public void setData(ScoreObject dt) {
        scores.add(dt);
    }

    public ArrayList<ScoreObject> getData() {
        return scores;
    }

}
