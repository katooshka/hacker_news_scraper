package scraper;

import data.Post;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JsonToPostConverterTest {

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
        when(downloader.downloadWebPage("abc")).thenReturn("{\n  \"by\" : " +
                "\"houston\" \n}");
        assertEquals(Json.createObjectBuilder().add("by", "houston").build(),
                jsonToPostConverter.downloadPostAsJsonObject("abc")
        );
    }

    @Test
    public void createPostFromJsonObject_ShouldThrow_WhenFiedlIsNotPresent() {
        try {
            JsonObject testJsonObject = Json.createObjectBuilder()
                    .add("url", "")
                    .add("by", "")
                    .add("score", 1)
                    .add("descendants", 1)
                    .build();
            jsonToPostConverter.convertJsonObjectToPost(testJsonObject, 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Title field is not present in the post", e
                    .getMessage());
        }

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
        checkExceptionIsThrownWhenJsonObjectFieldsInWrongFormat(
                testJsonObject, "Title field is empty");
    }

    @Test
    public void
    createPostFromJsonObject_ShouldThrow_WhenTitleLengthGreaterThan256() {
        JsonObject testJsonObject = Json.createObjectBuilder()
                .add("title", generate257CharsLongString())
                .add("url", "")
                .add("by", "")
                .add("score", 1)
                .add("descendants", 1)
                .build();
        checkExceptionIsThrownWhenJsonObjectFieldsInWrongFormat(
                testJsonObject, "Title is too long");
    }

    @Test
    public void createPostFromJsonObject_ShouldThrow_WhenUriIsInvalid() {
        JsonObject testJsonObject = Json.createObjectBuilder()
                .add("title", "title")
                .add("url", "abc")
                .add("by", "")
                .add("score", 1)
                .add("descendants", 1)
                .build();
        checkExceptionIsThrownWhenJsonObjectFieldsInWrongFormat
                (testJsonObject, "URI is not valid");
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
        checkExceptionIsThrownWhenJsonObjectFieldsInWrongFormat(testJsonObject,
                "Author field is empty");
    }

    @Test
    public void
    createPostFromJsonObject_ShouldThrow_WhenAuthorLengthGreaterThan256() {
        JsonObject testJsonObject = Json.createObjectBuilder()
                .add("title", "title")
                .add("url", "https://www.google.co.uk/")
                .add("by", generate257CharsLongString())
                .add("score", 1)
                .add("descendants", 1)
                .build();
        checkExceptionIsThrownWhenJsonObjectFieldsInWrongFormat(testJsonObject,
                "Author field is too long");
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
        checkExceptionIsThrownWhenJsonObjectFieldsInWrongFormat(testJsonObject,
                "Points is negative number or is not present");
    }

    @Test
    public void createPostFromJsonObject_ShouldThrow_WhenCommentsLessThanZero
            () {
        JsonObject testJsonObject = Json.createObjectBuilder()
                .add("title", "title")
                .add("url", "https://www.google.co.uk/")
                .add("by", "author")
                .add("score", 1)
                .add("descendants", -1)
                .build();
        checkExceptionIsThrownWhenJsonObjectFieldsInWrongFormat(testJsonObject,
                "Comments is negative number or is not present");
    }

    private void checkExceptionIsThrownWhenJsonObjectFieldsInWrongFormat
            (JsonObject testJsonObject, String message) {
        try {
            jsonToPostConverter.convertJsonObjectToPost(testJsonObject, 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals(message, e.getMessage());
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
            assertEquals("Rank is negative number or is not present", e
                    .getMessage());
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
