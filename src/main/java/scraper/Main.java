package scraper;

import static scraper.ArgumentsChecker.readArguments;

public class Main {

    public static void main(String[] args) {
        try {
            readArguments(args);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            showUsage();
        }
    }

    //TODO: change usage message
    private static void showUsage() {
        System.err.println("hackernews --posts n");
    }
}
