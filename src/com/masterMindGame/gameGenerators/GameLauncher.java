package com.masterMindGame.gameGenerators;

import com.masterMindGame.gameLevels.EasyGame;
import com.masterMindGame.gameLevels.HardGame;
import com.masterMindGame.gameLevels.MediumGame;

import java.util.*;

/**
 * Author: Yen-Chi (Audrey) Chen
 * Description:
 * This is the main entry file for the Mastermind game. The game will
 * generates different numbers combinations based on the level user chose.
 * The user needs to enter the correct numbers combinations within the default
 * limitation on attempts.
 */

public class GameLauncher {
    private static int totalNum;
    private static int min;
    private static int max;
    private static int lives;
    private static char[] targetCharArray;
    private static Map<Character, Integer> targetNumsMap = new HashMap<>();

    /**
   * We have 3 steps to finish the whole processing.
   * 1. Get parameters values from the chosen level.
   * 2. Build the game with the parameters values.
   * 3. Start playing.
   */
    public static void main(String[] args) {
        getLevelParam();
        buildGame();
        playGame(targetCharArray);
    }

    /**
     * Get level input from the chosen game difficulty.
     */
    public static void getLevelParam() {
        System.out.println("---------- Start the Mastermind game! ----------");
        while (true) {
            System.out.println("Please Enter Game Difficulty Level(easy/medium/hard): ");
            Scanner sc = new Scanner(System.in);

            String level = sc.nextLine();
            if (level.equals("easy")) {
                EasyGame easyMode = new EasyGame();
                totalNum = easyMode.getTotalNum();
                min = easyMode.getMin();
                max = easyMode.getMax();
                lives = easyMode.getLives();
                break;
            } else if (level.equals("medium")) {
                MediumGame mediumMode = new MediumGame();
                totalNum = mediumMode.getTotalNum();
                min = mediumMode.getMin();
                max = mediumMode.getMax();
                lives = mediumMode.getLives();
                break;
            } else if (level.equals("hard")) {
                HardGame hardMode = new HardGame();
                totalNum = hardMode.getTotalNum();
                min = hardMode.getMin();
                max = hardMode.getMax();
                lives = hardMode.getLives();
                break;
            }
            System.out.println("Invalid input! Please only enter easy, medium, or hard.");
        }
    }

    /**
     * Build Game by generating random numbers, and store it for validating later on.
     */
    private static void buildGame() {
        System.out.println("Initializing game...");
        // Generate random numbers from the parameter values we got.
        RandomNumFactory numFactory = new RandomNumFactory();
        int[] targetNumsArray = numFactory.generateRandomNum(totalNum, min, max);

        TargetNumsStorer numsStorer = new TargetNumsStorer();
        targetCharArray = numsStorer.intArrayToCharArray(targetNumsArray);
        // Put all elements in targetCharArray to a hashmap
        targetNumsMap = numsStorer.buildTargetMap(targetCharArray);

        // Print instruction based on the level user chosen
        System.out.println("Instruction: ");
        System.out.println("Enter " +  totalNum + " numbers from " + min +" to " + max + " , try to guess " +
                "the correct combination within " + lives + " attempts.");
        System.out.println("Note: duplicate numbers are also applied.");
    }

    /**
     * Game Time!
     */
    private static void playGame(char[] targetCharArray) {
        // For storing user's current numbers guessed
        char[] curGuess = new char[targetCharArray.length];
        // For storing user's current correct numbers input
        char[] curCorrect = new char[targetCharArray.length];
        // For recording attempts user made so far
        int attempt = 0;
        // Keep storing input record for tracking
        List<String> record = new ArrayList<>();

        while (lives > 0) {
            System.out.println("Attempts Left: " + lives);
            System.out.println();
            boolean validLengthInput = false;
            boolean isGameFinished = true;
            while (!validLengthInput) {
                // Prompt user to input their guess
                System.out.println("Enter your guess here: ");
                Scanner sc = new Scanner(System.in);
                String input = sc.nextLine();

                // Initialize and fill the current input with '.'
                for (int i = 0; i < targetCharArray.length; i++) {
                    curCorrect[i] = '.';
                }

                if (input.length() == totalNum) {
                    attempt++;
                    validLengthInput = true;
                    int correctNum = 0;
                    int correctIndex = 0;

                    for (int i = 0; i < totalNum; i++) {
                        curGuess[i] = input.charAt(i);
                        if (targetNumsMap.containsKey(input.charAt(i)) && targetNumsMap.get(input.charAt(i)) != 0) {
                            targetNumsMap.put(input.charAt(i), targetNumsMap.get(input.charAt(i)) - 1);
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
                        if (targetNumsMap.containsKey(curGuess[i])) {
                            targetNumsMap.put(curGuess[i], targetNumsMap.get(curGuess[i]) + 1);
                        }
                    }
                    System.out.println("Wrong guess!");
                    lives--;

                    System.out.println("Correct Number Guessed: " + correctNum + " /" + totalNum);
                    System.out.println("Correct Position Guessed: " + correctIndex + " /" + totalNum);

                    System.out.println();
                    System.out.println("Current Guess: ");

                    for (char c : curCorrect) {
                        if (c == '.') {
                            isGameFinished = false;
                        }
                    }

                    record.add(attempt + " : " + new String(curCorrect));
                    for (String s : record) {
                        System.out.println(s);
                    }

                    System.out.println();
                    System.out.println("-----------");

                } else {
                    System.out.println("Please enter " + totalNum + " numbers. Try Again.");
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