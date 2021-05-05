package com.masterMindGame.gameLevels;

import com.masterMindGame.gameLevels.GameLevel;

public class HardGame extends GameLevel {
    int totalNum = 10;
    int min = 0;
    int max = 9;
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
