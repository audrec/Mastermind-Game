package com.masterMindGame.gameGenerators;

import java.util.HashMap;
import java.util.Map;

public class TargetNumsStorer {

    public char[] intArrayToCharArray(int[] intArray) {
        // Convert target int array to char array format
        char[] charArray = new char[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            charArray[i] = (char)(intArray[i] + '0');
        }
        return charArray;
    }

    public Map<Character, Integer> buildTargetMap(char[] charArray) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : charArray) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return map;
    }
}
