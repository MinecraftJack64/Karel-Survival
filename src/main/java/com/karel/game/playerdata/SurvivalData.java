package com.karel.game.playerdata;

import java.io.Serializable;

public class SurvivalData implements Serializable{
    private int[] maxLevels = {0, 0, 0, 0, 0};
    private int[] maxScores = {0, 0, 0, 0, 0};
    public SurvivalData(){
        //
    }
    public int getHighScore(int d) {
        return maxScores[d];
    }
    public int getHighLevel(int d){
        return maxLevels[d];
    }
    public void setHighScore(int d, int nv) {
        maxScores[d] = nv;
    }
    public void setHighLevel(int d, int nv) {
        maxLevels[d] = nv;
    }
}
