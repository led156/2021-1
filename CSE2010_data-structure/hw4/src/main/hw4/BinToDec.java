package hw4;
/*
 * CSE2010 Homework #4: BinToDec.java
 *
 * Complete the code below.
 */

public class BinToDec {

    public static int binToDec(String number) {
        if (number.length() == 1) {
            // Base case
            return Integer.parseInt(number);
        } else {
            // Recursive case
            return Integer.parseInt(number.substring(0, 1)) * (int)Math.pow(2, number.length()-1) + binToDec(number.substring(1));
        }
    }

    // Tail-recursion
    public static int binToDecTR(String number, int result) {
        if (number.length()== 1) {
            // Base case
            return result + Integer.parseInt(number);
        } else {
            // Recursive case
            return binToDecTR(number.substring(1), result + Integer.parseInt(number.substring(0, 1)) * (int)Math.pow(2, number.length()-1));
        }
    }

}
