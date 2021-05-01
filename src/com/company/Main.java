package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        int totalNum = 4;
        int rangeMin = 0;
        int rangeMax = 7;
        int lives = 10;

        System.out.println("Start the Mastermind game!");
        System.out.println("Instruction: ");
        System.out.println("Enter " +  totalNum + " numbers from " + rangeMin +" to " + rangeMax + " , try to guess " +
                "the correct combination within " + lives + " attempts.");

        System.out.println("Note: duplicate numbers are also applied.");

        // Get random 4 numbers from 0-7
        GenerateRandomNum numGenerator = new GenerateRandomNum();
        int[] targetArray = numGenerator.getNum(totalNum, rangeMin, rangeMax);
        char[] targetCharArray = new char[targetArray.length];

        // Convert target int array to char array format
        for (int i = 0; i < targetArray.length; i++) {
            targetCharArray[i] = (char)(targetArray[i] + '0');
        }

        // Put all elements in targetCharArray to a hashmap
        Map<Character, Integer> map = new HashMap<>();
        for (char c : targetCharArray) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        // For storing user's current numbers guessed
        char[] curGuess = new char[targetArray.length];

        // For storing user's current correct input
        char[] curCorrect = new char[targetArray.length];

        int attemp = 0;
        // Keep storing input record for tracking
        List<String> record = new ArrayList<>();

        while (lives > 0) {
            System.out.println("Attempts Left: " + lives);
            System.out.println();
            boolean longEnoughInput = true;
            boolean isGameFinished = true;
            while (longEnoughInput) {
                // Prompt user to input their guess
                System.out.println("Please enter random 4 numbers from 0-7: ");
                Scanner sc = new Scanner(System.in);
                String input = sc.nextLine();

                // Initialize and fill the current input with '.'
                for (int i = 0; i < curCorrect.length; i++) {
                    curCorrect[i] = '.';
                }

                if (input.length() == totalNum) {
                    attemp++;
                    longEnoughInput = false;
                    boolean isGuessCorrect = false;
                    int correctNum = 0;
                    int correctIndex = 0;

                    for (int i = 0; i < totalNum; i++) {
                        curGuess[i] = input.charAt(i);
                        if (map.containsKey(input.charAt(i)) && map.get(input.charAt(i)) != 0) {
                            map.put(input.charAt(i), map.get(input.charAt(i)) - 1);
                            //isGuessCorrect = true;
                            if (input.charAt(i) == targetCharArray[i]) {
                                correctIndex++;
                                correctNum++;
                                curCorrect[i] = targetCharArray[i];
                            } else {
                                correctNum++;
                            }
                        }
                    }
                    // Recover the counts of each target number.
                    for (int i = 0; i < curGuess.length; i++) {
                        map.put(curGuess[i], map.getOrDefault(curGuess[i], 0) + 1);
                    }
                    System.out.println("Wrong guess!");
                    lives--;

                    System.out.println("Correct Number Guessed: " + correctNum + " /" + totalNum);
                    System.out.println("Correct Position Guessed: " + correctIndex + " /" + totalNum);

                    System.out.println();
                    System.out.println("Current Guess: ");

                    for (int i = 0; i < curCorrect.length; i++) {
                        if (curCorrect[i] == '.') {
                            isGameFinished = false;
                        }
                    }

                    record.add(attemp + " : " + new String(curCorrect));
                    for (String s : record) {
                        System.out.println(s);
                    }

                    System.out.println();
                    System.out.println("-----------");

                } else {
                    System.out.println("Please enter " + totalNum + " numbers. Try Again.");
                    lives--;
                }
            }
            if (isGameFinished) {
                System.out.println("You won!");
                break;
            } else if (lives == 0) {
                System.out.println("You lost! The word was: " + new String(targetCharArray));
            }
        }
    }
}