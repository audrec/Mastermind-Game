package com.company;


public class GenerateRandomNum {
    public static int[] getNum(int count, int min, int max) {
        int number = 0;

        try {
            RandomNumbers rn = new RandomNumbers("cbf7ac13-539b-4d29-893f-7b16220e8035");
            rn.generate(count, min, max, true, "method");

            // obtaining random numbers together as an integer array
            System.out.println("obtaining random numbers together as an integer array");
            int[] numArray = rn.getElementAsArray();

            for (int i = 0; i < count; i++) {
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
