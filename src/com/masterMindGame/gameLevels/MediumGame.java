package com.masterMindGame.gameLevels;

import com.masterMindGame.gameLevels.GameLevel;

import java.util.ArrayList;
import java.util.List;

public class MediumGame extends GameLevel {
    private final int totalNum = 7;
    private final int min = 0;
    private final int max = 8;
    private final int lives = 10;
    private final int timeLimit = 120;
    private final int hintLimit = 2;

    @Override
    public int getTotalNum() {
        return totalNum;
    }
    @Override
    public int getMin() {
        return min;
    }
    @Override
    public int getMax() {
        return max;
    }
    @Override
    public int getLives() {
        return lives;
    }
    @Override
    public int getTimeLimit() { return timeLimit; }
    @Override
    public int getHintLimit() {
        return hintLimit;
    }

    @Override
    public List<String> getInstruction() {
        List<String> strList = new ArrayList<>();
        strList.add("Instruction: ");
        strList.add("Enter " +  totalNum + " numbers from " + min +" to " + max + " , try to guess " +
                "the correct combination within " + lives + " attempts or within " + timeLimit + " seconds, whichever ends first.");
        strList.add("Note: duplicate numbers are also applied.");
        return strList;

    }
}
