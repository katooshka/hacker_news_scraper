package scraper_test;

import data.Post;
import org.junit.Test;
import scraper.PostToJsonConverter;

import javax.json.Json;
import javax.json.JsonObject;

import static org.junit.Assert.assertEquals;

public class PostToJsonConverterTests {
    private PostToJsonConverter postToJsonConverter = new PostToJsonConverter();

    @Test
    public void createJsonFromPost_ShouldReturnJsonObject() {
        Post testPost = new Post.Builder()
                .setTitle("title")
                .setUri("uri")
                .setAuthor("author")
                .setPoints(1)
                .setComments(1)
                .setRank(1)
                .build();
        JsonObject expectedJsonObject = createTestJsonObject();
        assertEquals(expectedJsonObject, postToJsonConverter.createJsonFromPost(testPost));
    }

    private JsonObject createTestJsonObject() {
        return Json.createObjectBuilder()
                .add("title", "title")
                .add("uri", "uri")
                .add("author", "author")
                .add("points", 1)
                .add("comments", 1)
                .add("rank", 1)
                .build();
    }
}
