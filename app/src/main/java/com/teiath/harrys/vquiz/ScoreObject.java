package com.teiath.harrys.vquiz;

/**
 * Created by harrys on 2/2/2018.
 */

public class ScoreObject {
    private int mIconType; //1 for female, 0 for male
    private String mUsername;
    private String mMode;
    private String mScore;

    public ScoreObject(String mIconType, String mUsername, String mMode, String mScore) {
        if (mIconType.equals("1")) {
            this.mIconType = R.drawable.male;
        } else {
            this.mIconType = R.drawable.female;
        }
        this.mUsername = mUsername;
        this.mMode = mMode;
        this.mScore = mScore;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmMode() {
        return mMode;
    }

    public void setmMode(String mMode) {
        this.mMode = mMode;
    }

    public String getmScore() {
        return mScore;
    }

    public void setmScore(String mScore) {
        this.mScore = mScore;
    }

    public void setmIconType(int mIconType) {
        this.mIconType = mIconType;
    }

    public int getmIconType() {return mIconType; }

    @Override
    public String toString() {
        return "ScoreObject{" +
                "mIconType=" + mIconType +
                ", mUsername='" + mUsername + '\'' +
                ", mMode='" + mMode + '\'' +
                ", mScore='" + mScore + '\'' +
                '}';
    }
}
