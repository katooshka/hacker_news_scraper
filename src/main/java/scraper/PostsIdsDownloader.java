package scraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

public class PostsIdsDownloader {
    public static String downloadTopPostsIDsAsStringFromUrl(String topPostsUrl) {
        String string;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new URL(topPostsUrl).openStream()))) {
            string = br.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while downloading web page" + topPostsUrl);
        }
        return string;
    }

    //TODO: find out if I can optimize this parsing
    public static String[] transformPostsIdsStringToArray(String string, int postsNumber) {
        String[] ids = string
                .replace("[ ", "")
                .replace(" ]", "")
                .split(", ");
        return Arrays.copyOf(ids, postsNumber);
    }
}
