package com.masterMindGame.gameGenerators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GameLauncherUtils {

    char[] intArrayToCharArray(int[] intArray) {
        // Convert target int array to char array format
        char[] charArray = new char[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            charArray[i] = (char)(intArray[i] + '0');
        }
        return charArray;
    }

    Map<Character, Integer> buildTargetMap(char[] charArray) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : charArray) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        return map;
    }

    void printInstruction(int totalNum, int min, int max, int lives) {
        System.out.println("Instruction: ");
        System.out.println("Enter " +  totalNum + " numbers from " + min +" to " + max + " , try to guess " +
                "the correct combination within " + lives + " attempts.");
        System.out.println("Note: duplicate numbers are also applied.");
    }

    char[] initialCurCorrect(char[] target, char[] curCorrect) {
        // Initialize and fill the current input with '.'
        for (int i = 0; i < target.length; i++) {
            curCorrect[i] = '.';
        }
        return curCorrect;
    }


    boolean getIfInputIsDigit(String input) {
        try {
            long inputNum = Long.parseLong(input);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    int[] checkValidInput(int totalNum, char[] targetCharArray, Map<Character, Integer> targetNumsMap, String input, int correctNum, int correctIndex, char[] curCorrect, char[] curGuess) {
        for (int i = 0; i < totalNum; i++) {
            if (input.charAt(i) == targetCharArray[i]) {
                correctIndex++;
                curCorrect[i] = targetCharArray[i];
            }
            if (targetNumsMap.containsKey(input.charAt(i)) && targetNumsMap.get(input.charAt(i)) != 0) {
                targetNumsMap.put(input.charAt(i), targetNumsMap.get(input.charAt(i)) - 1);
                correctNum++;
                curGuess[i] = input.charAt(i);
            }
        }
        // Recover the counts of each target number.
        for (int i = 0; i < curGuess.length; i++) {
            if (curGuess[i] != '\0') {
                targetNumsMap.put(curGuess[i], targetNumsMap.get(curGuess[i]) + 1);
            }
        }
        return new int[] {correctNum, correctIndex};
    }

    void printCurRecords(int[] correctResult, int totalNum) {
        System.out.println("Correct Number Guessed: " + correctResult[0] + " / " + totalNum);
        System.out.println("Correct Position Guessed: " + correctResult[1] + " / " + totalNum);
        System.out.println();
    }

    void printAllGuess(char[] curCorrect, List<String> record, int attempt) {
        System.out.println("Current Guess: ");
        record.add(attempt + " : " + new String(curCorrect));
        for (String s : record) {
            System.out.println(s);
        }

        System.out.println();
        System.out.println("-----------");
    }

    boolean checkIfGameFinished(char[] curCorrect) {
        for (char c : curCorrect) {
            if (c == '.') {
                return false;
            }
        }
        return true;
    }

}
