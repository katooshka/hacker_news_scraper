package main;

import data.Post;
import scraper.*;

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
        WebPageDownloader webPageDownloader = new WebPageDownloader();
        PostsGetter postsGetter = new PostsGetter(new PostsIdsDownloader(webPageDownloader), new JsonToPostConverter(webPageDownloader));
        Post[] posts = postsGetter.getPosts(postsNumber);
        String[] jsonObjects = new String[postsNumber];
        PostToJsonConverter postToJsonConverter = new PostToJsonConverter();
        for (int i = 0; i < posts.length; i++) {
            jsonObjects[i] = postToJsonConverter.createStringFromJsonObject(
                    postToJsonConverter.createJsonFromPost(posts[i])
            );
        }
        System.out.println(Arrays.toString(jsonObjects));
        System.out.println();
    }

    private static void showUsage() {
        System.err.println("Usage: hackernews --posts <number of posts>");
    }
}
