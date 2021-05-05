package com.masterMindGame.gameGenerators;

import com.masterMindGame.exceptionHandlers.InvalidMethodCallException;
import com.masterMindGame.exceptionHandlers.InvalidResponseException;
import com.masterMindGame.gameGenerators.HTTPRequestNumGenerator;

public class randomNumFactory {

    int[] generateRandomNum(int totalNum, int min, int max) {
        int number = 0;

        try {
            HTTPRequestNumGenerator rn = new HTTPRequestNumGenerator("cbf7ac13-539b-4d29-893f-7b16220e8035");
            rn.generate(totalNum, min, max, true, "method");

            // obtaining random numbers together as an integer array
            System.out.println("obtaining random numbers together as an integer array");
            int[] numArray = rn.getElementAsArray();

            for (int i = 0; i < totalNum; i++) {
                number = numArray[i];
                System.out.print(number);
            }
            System.out.println();
            return numArray;
        } catch (InvalidResponseException invalidResponseException) {
            System.out.println(invalidResponseException.getMessage());
            invalidResponseException.printStackTrace();
        } catch (InvalidMethodCallException invalidMethodCallException) {
            System.out.println(invalidMethodCallException.getMessage());
            invalidMethodCallException.printStackTrace();
        }
        return null;
    }
}
