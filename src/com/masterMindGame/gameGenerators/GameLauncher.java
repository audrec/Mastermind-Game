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
    private static int timeLimit;
    private static List<String> instruction;
    private static int hintLimit;
    private static char[] targetCharArray;
    private static Map<Character, Integer> targetNumsMap = new HashMap<>();
    static GameLauncherUtils utils = new GameLauncherUtils();
    private static double score = 0;

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
                timeLimit = easyMode.getTimeLimit();
                instruction = easyMode.getInstruction();
                hintLimit = easyMode.getHintLimit();
                break;
            } else if (level.equals("medium")) {
                MediumGame mediumMode = new MediumGame();
                totalNum = mediumMode.getTotalNum();
                min = mediumMode.getMin();
                max = mediumMode.getMax();
                lives = mediumMode.getLives();
                timeLimit = mediumMode.getTimeLimit();
                instruction = mediumMode.getInstruction();
                hintLimit = mediumMode.getHintLimit();
                break;
            } else if (level.equals("hard")) {
                HardGame hardMode = new HardGame();
                totalNum = hardMode.getTotalNum();
                min = hardMode.getMin();
                max = hardMode.getMax();
                lives = hardMode.getLives();
                timeLimit = hardMode.getTimeLimit();
                instruction = hardMode.getInstruction();
                hintLimit = hardMode.getHintLimit();
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

        // Get the char array format of the target integer array
        targetCharArray = utils.intArrayToCharArray(targetNumsArray);
        // Put all elements in targetCharArray to a hashmap
        targetNumsMap = utils.buildTargetMap(targetCharArray);

        // Print instruction based on the level user chosen
        // utils.printInstruction(totalNum, min, max, lives, timeLimit);

        for (String s : instruction) {
            System.out.println(s);
        }
    }

    /**
     * Game Time!
     */
    private static void playGame(char[] targetCharArray) {
        // For storing user's current correct numbers guessed
        char[] curGuess = new char[targetCharArray.length];
        // For storing user's current correct numbers input
        char[] curCorrect = new char[targetCharArray.length];
        // For recording attempts user made so far
        int attempt = 0;
        // Keep storing input record for tracking
        List<String> record = new ArrayList<>();

        while (lives > 0) {
            System.out.println("Attempts Left: " + lives);
            System.out.println("Hint Available: " + hintLimit);
            System.out.println();
            boolean validLengthInput = false;
            boolean isGameFinished = true;
            while (!validLengthInput) {
                // Prompt user to input their guess
                System.out.println("Enter your guess here: ");
                long startTime = System.currentTimeMillis();

                new GameLauncherUtils.Reminder(timeLimit);
                Scanner sc = new Scanner(System.in);
                String input = sc.nextLine();

                // If the user types in the secret code, indicate the answer and exit the program
                if (utils.isSecrectCodeEntered(input)) {
                    String answer = new String(targetCharArray);
                    System.out.println("Congratulations! The answer is: " + answer);
                    break;
                }

                // Initialize current correct array with '.' at each index
                curCorrect = utils.initialCurCorrectArray(targetCharArray, curCorrect);

                // Only when the input's length equals to the target's length will we validate the combinations
                if (input.length() == totalNum) {
                    // If input were not all digits, re-prompt for user input
                    if (!utils.getIfInputIsDigit(input)) {
                        System.out.println("Please only enter digits numbers. Try again.");
                        continue;
                    }
                    attempt++;
                    lives--;
                    validLengthInput = true;
                    int correctNum = 0;
                    int correctIndex = 0;
                    // Check the numbers of correct numbers and correct index guessed for current input
                    int[] correctResult = utils.checkValidInput(totalNum, targetCharArray, targetNumsMap, input,
                            correctNum, correctIndex, curCorrect, curGuess);
                    // Count current highest score
                    double curScore = utils.countScore(curCorrect, totalNum);
                    score = Math.max(score, curScore);
                    // Show current guess results
                    utils.printCurRecords(correctResult, totalNum);
                    // Show all number combinations guessed so far
                    utils.printAllGuess(curCorrect, record, attempt);

                    // If either number of the target was missed, game is not finished yet
                    isGameFinished = utils.checkIfGameFinished(curCorrect);

                    if (!isGameFinished && lives == 0) {
                        System.out.println("Wrong guess!");
                        System.out.println("You lost! The word was: " + new String(targetCharArray));
                        System.out.println("Your highest score: " + score + " / 100.00");
                        break;
                    } else if (!isGameFinished) {
                        System.out.println("Wrong guess! Try again!");
                        long endTime = System.currentTimeMillis();
                        long timeElapsed = (endTime - startTime) / 1000;
                        long timeLeft = timeLimit - timeElapsed;
                        timeLimit -= timeElapsed;
                        System.out.println("You still have " + timeLeft + " seconds left.");
                        if (hintLimit > 0) {
                            System.out.println("Do you want a hint? (y/n)");
                            String ifGetHint = sc.nextLine();
                            askHint(ifGetHint, curCorrect);
                        }

                    }
                } else {
                    System.out.println("Please enter " + totalNum + " numbers. Try Again.");
                }
            }
            if (isGameFinished) {
                System.out.println("You won!");
                System.out.println("Your highest score: " + score + " / 100.00");
                break;
            }
        }
        System.exit(0);
    }

    private static void askHint(String input, char[] curCorrect) {
        if (input.equals("y")) {
            hintLimit--;
            curCorrect = utils.getHint(curCorrect, targetCharArray);
            String newCurGuess = new String(curCorrect);
            System.out.println("New current guess: " + newCurGuess);
        }
    }
}