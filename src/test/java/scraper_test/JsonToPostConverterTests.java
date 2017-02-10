package scraper_test;

import data.Post;
import org.junit.Before;
import org.junit.Test;
import scraper.JsonToPostConverter;
import scraper.WebPageDownloader;

import javax.json.Json;
import javax.json.JsonObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//TODO: add URI test
public class JsonToPostConverterTests {

    private WebPageDownloader downloader;
    private JsonToPostConverter jsonToPostConverter;

    @Before
    public void setUp() {
        downloader = mock(WebPageDownloader.class);
        jsonToPostConverter = new JsonToPostConverter(downloader);
    }

    @Test
    public void downloadPostAsJsonObject_ShouldThrow_WhenPostInWrongFormat() {
        when(downloader.downloadWebPage("abc")).thenReturn("^_^");
        try {
            jsonToPostConverter.downloadPostAsJsonObject("abc");
            fail();
        } catch (RuntimeException e) {
            // expected
        }
    }

    @Test
    public void downloadPostAsJsonObject_ShouldReturnJsonObject() {
        when(downloader.downloadWebPage("abc")).thenReturn("{\n  \"by\" : \"houston\" \n}");
        assertEquals(Json.createObjectBuilder().add("by", "houston").build(),
                jsonToPostConverter.downloadPostAsJsonObject("abc")
        );
    }

    @Test
    public void createPostFromJsonObject_ShouldThrow_WhenTitleIsEmpty() {
        JsonObject testJsonObject = Json.createObjectBuilder()
                .add("title", "")
                .add("url", "")
                .add("by", "")
                .add("score", 1)
                .add("descendants", 1)
                .build();
        checkExceptionIsThrownWhenJsonObjectFieldsInWrongFormat(testJsonObject);
    }

    @Test
    public void createPostFromJsonObject_ShouldThrow_WhenTitleLengthGreaterThan256() {
        JsonObject testJsonObject = Json.createObjectBuilder()
                .add("title", generate257CharsLongString())
                .add("url", "")
                .add("by", "")
                .add("score", 1)
                .add("descendants", 1)
                .build();
        checkExceptionIsThrownWhenJsonObjectFieldsInWrongFormat(testJsonObject);
    }

    @Test
    public void createPostFromJsonObject_ShouldThrow_WhenAuthorIsEmpty() {
        JsonObject testJsonObject = Json.createObjectBuilder()
                .add("title", "title")
                .add("url", "https://www.google.co.uk/")
                .add("by", "")
                .add("score", 1)
                .add("descendants", 1)
                .build();
        checkExceptionIsThrownWhenJsonObjectFieldsInWrongFormat(testJsonObject);
    }

    @Test
    public void createPostFromJsonObject_ShouldThrow_WhenAuthorLengthGreaterThan256() {
        JsonObject testJsonObject = Json.createObjectBuilder()
                .add("title", "title")
                .add("url", "https://www.google.co.uk/")
                .add("by", generate257CharsLongString())
                .add("score", 1)
                .add("descendants", 1)
                .build();
        checkExceptionIsThrownWhenJsonObjectFieldsInWrongFormat(testJsonObject);
    }

    @Test
    public void createPostFromJsonObject_ShouldThrow_WhenPointsLessThanZero() {
        JsonObject testJsonObject = Json.createObjectBuilder()
                .add("title", "title")
                .add("url", "https://www.google.co.uk/")
                .add("by", "author")
                .add("score", -1)
                .add("descendants", 1)
                .build();
        checkExceptionIsThrownWhenJsonObjectFieldsInWrongFormat(testJsonObject);
    }

    @Test
    public void createPostFromJsonObject_ShouldThrow_WhenCommentsLessThanZero() {
        JsonObject testJsonObject = Json.createObjectBuilder()
                .add("title", "title")
                .add("url", "https://www.google.co.uk/")
                .add("by", "author")
                .add("score", 1)
                .add("descendants", -1)
                .build();
        checkExceptionIsThrownWhenJsonObjectFieldsInWrongFormat(testJsonObject);
    }

    private void checkExceptionIsThrownWhenJsonObjectFieldsInWrongFormat(JsonObject testJsonObject) {
        try {
            jsonToPostConverter.convertJsonObjectToPost(testJsonObject, 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("One or more fields of this post are in wrong format", e.getMessage());
        }
    }

    @Test
    public void createPostFromJsonObject_ShouldThrow_WhenRankLessThanZero() {
        JsonObject testJsonObject = Json.createObjectBuilder()
                .add("title", "title")
                .add("url", "https://www.google.co.uk/")
                .add("by", "author")
                .add("score", 1)
                .add("descendants", 1)
                .build();
        try {
            jsonToPostConverter.convertJsonObjectToPost(testJsonObject, -1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("One or more fields of this post are in wrong format", e.getMessage());
        }
    }

    @Test
    public void createPostFromJsonObject_ShouldReturnPost() {
        JsonObject testJsonObject = Json.createObjectBuilder()
                .add("title", "title")
                .add("url", "https://www.google.co.uk/")
                .add("by", "author")
                .add("score", 1)
                .add("descendants", 1)
                .build();
        assertEquals(
                jsonToPostConverter.convertJsonObjectToPost(testJsonObject, 1),
                new Post.Builder()
                        .setTitle("title")
                        .setUri("https://www.google.co.uk/")
                        .setAuthor("author")
                        .setPoints(1)
                        .setComments(1)
                        .setRank(1)
                        .build());
    }

    private static String generate257CharsLongString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 257; i++) {
            sb.append("a");
        }
        return sb.toString();
    }
}
