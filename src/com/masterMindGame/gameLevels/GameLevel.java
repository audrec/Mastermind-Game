package com.masterMindGame.gameLevels;

import java.util.List;

public abstract class GameLevel {
    public abstract int getTotalNum();
    public abstract int getMin();
    public abstract int getMax();
    public abstract int getLives();
    public abstract int getTimeLimit();
    public abstract List<String> getInstruction();
    public abstract int getHintLimit();
}
