package com.masterMindGame.gameGenerators;

import com.masterMindGame.exceptionHandlers.InvalidMethodCallException;
import com.masterMindGame.exceptionHandlers.InvalidResponseException;
import com.masterMindGame.gameGenerators.HTTPRequestNumGenerator;

public class RandomNumFactory {

    int[] generateRandomNum(int totalNum, int min, int max) {
        int number = 0;

        try {
            HTTPRequestNumGenerator rn = new HTTPRequestNumGenerator("cbf7ac13-539b-4d29-893f-7b16220e8035");
            rn.generate(totalNum, min, max, true, "method");

            // Get random numbers in integer array format.
            int[] numArray = rn.getElementAsArray();
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
