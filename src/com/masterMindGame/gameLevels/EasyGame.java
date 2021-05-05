package com.masterMindGame.gameLevels;

import com.masterMindGame.gameLevels.GameLevel;

public class EasyGame extends GameLevel {
    int totalNum = 4;
    int min = 0;
    int max = 7;
    int lives = 10;

    public int getTotalNum() {
        return totalNum;
    }
    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }
    public int getLives() {
        return lives;
    }

}
