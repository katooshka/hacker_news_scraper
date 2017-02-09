package scraper;

import data.Post;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class JsonToPostConverter {

    private WebPageDownloader webPageDownloader;

    public JsonToPostConverter(WebPageDownloader webPageDownloader) {
        this.webPageDownloader = webPageDownloader;
    }

    public JsonObject downloadJsonObject(String postUrl) {
        JsonObject jsonObject;
        try (JsonReader jsonReader = Json.createReader(new StringReader(webPageDownloader.downloadWebPage(postUrl)))) {
            jsonObject = jsonReader.readObject();
        }
        return jsonObject;
    }

    //TODO: add uri validation
    public Post createPostFromJsonObject(JsonObject jsonObject, int rank) {
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
