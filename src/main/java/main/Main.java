package main;

import data.Post;
import scraper.PostsGetter;

import java.io.IOException;
import java.util.Arrays;

import static main.ArgumentsChecker.readArguments;

public class Main {

    public static void main(String[] args) throws IOException {
        int postsNumber;
        try {
            postsNumber = readArguments(args);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            showUsage();
            return;
        }
        Post[] posts = PostsGetter.getPosts(postsNumber);
        System.out.println(Arrays.toString(posts));
    }

    //TODO: change usage message
    private static void showUsage() {
        System.err.println("hackernews --posts n");
    }
}
