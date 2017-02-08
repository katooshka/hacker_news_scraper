package main;

import static data.Constants.FUNCTION_NAME;
import static data.Constants.MAX_POSTS_NUMBER;

public class ArgumentsChecker {
    /**
     * Returns number of posts that are to be printed.
     * The first argument represents the name of the function that outputs top Hacker News posts
     * and should be equal to '--posts'. The second argument represents a number of posts to be printed
     * and should be a value from 1 to 100 inclusive.
     *
     * @param args a String array containing name of the print function and number of posts to be printed
     * @return number of posts to be printed
     * @throws IllegalArgumentException if number of arguments differs from two
     *                                  or if the format of arguments is wrong
     */
    public static int readArguments(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Wrong number of arguments");
        }
        String firstArgument = args[0];
        if (!firstArgument.equals(FUNCTION_NAME)) {
            throw new IllegalArgumentException("Wrong first argument");
        }
        int secondArgument;
        try {
            secondArgument = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Second argument is not a number");
        }
        if (secondArgument < 0) {
            throw new IllegalArgumentException("Second argument is a negative number");
        }
        if (secondArgument == 0) {
            throw new IllegalArgumentException("Second argument is zero");
        }
        if (secondArgument > MAX_POSTS_NUMBER) {
            throw new IllegalArgumentException("Second argument is greater than " + MAX_POSTS_NUMBER);
        }
        return secondArgument;
    }
}
