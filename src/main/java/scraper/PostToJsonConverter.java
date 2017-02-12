package scraper;

import data.Post;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Converts given post into JsonObject.
 */
public class PostToJsonConverter {
    public JsonObject createJsonFromPost(Post post) {
        return Json.createObjectBuilder()
                .add("title", post.getTitle())
                .add("uri", post.getUri())
                .add("author", post.getAuthor())
                .add("points", post.getPoints())
                .add("comments", post.getComments())
                .add("rank", post.getRank())
                .build();
    }
}
