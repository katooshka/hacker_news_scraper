package scraper;

import data.Post;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.net.URL;

public class JsonToPostConverter {
    public static JsonObject downloadJsonObject(String postUrl) {
        JsonObject jsonObject;
        try (JsonReader jr = Json.createReader(new URL(postUrl).openStream())) {
            jsonObject = jr.readObject();
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while downloading web page: " + postUrl);
        }
        return jsonObject;
    }

    //TODO: add uri validation
    public static Post createPostFromJsonObject(JsonObject jsonObject, int rank) {
        String title = jsonObject.getString("title");
        String url = jsonObject.getString("url");
        String author = jsonObject.getString("by");
        int points = jsonObject.getInt("score");
        int comments = jsonObject.getInt("descendants");
        if (title.isEmpty() || title.length() > 256
                || !URIValidator.isValid(url)
                || author.isEmpty() || author.length() > 256
                || points < 0 || comments < 0 || rank < 0) {
            throw new IllegalArgumentException("One or more fields of this post are in wrong format");
        }
        return new Post.Builder()
                .setTitle(title)
                .setUri(url)
                .setAuthor(author)
                .setPoints(points)
                .setComments(comments)
                .setRank(rank)
                .build();
    }
}
