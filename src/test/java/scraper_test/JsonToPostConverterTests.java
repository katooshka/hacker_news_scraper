package scraper_test;

import org.junit.Test;
import scraper.JsonToPostConverter;

import javax.json.JsonObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//TODO: decide how to check Uri validator
public class JsonToPostConverterTests {

    @Test
    public void downloadJSonObject_ShouldThrow_WhenUrlNotValid() {
        try {
            JsonToPostConverter.downloadJsonObject("abc");
            fail("RuntimeException expected");
        } catch (RuntimeException e) {
            assertEquals("Error occurred while downloading web page: abc", e.getMessage());
        }
    }

    @Test
    public void createPostFromJsonObject_ShouldThrow_WhenTitleIsEmpty() {
        JsonObject testJsonObject = mock(JsonObject.class);
        when(testJsonObject.getString("title")).thenReturn("");
        try {
            JsonToPostConverter.createPostFromJsonObject(testJsonObject, 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("One or more fields of this post are in wrong format", e.getMessage());
        }
    }

    @Test
    public void createPostFromJsonObject_ShouldThrow_WhenTitleLengthGreaterThan256() {
        JsonObject testJsonObject = mock(JsonObject.class);
        when(testJsonObject.getString("title")).thenReturn(generate256CharsLongString());
        try {
            JsonToPostConverter.createPostFromJsonObject(testJsonObject, 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("One or more fields of this post are in wrong format", e.getMessage());
        }
    }

    @Test
    public void createPostFromJsonObject_ShouldThrow_WhenAuthorIsEmpty() {
        JsonObject testJsonObject = mock(JsonObject.class);
        when(testJsonObject.getString("title")).thenReturn("title");
        when(testJsonObject.getString("url")).thenReturn("https://www.google.co.uk/");
        when(testJsonObject.getString("by")).thenReturn("");
        try {
            JsonToPostConverter.createPostFromJsonObject(testJsonObject, 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("One or more fields of this post are in wrong format", e.getMessage());
        }
    }

    @Test
    public void createPostFromJsonObject_ShouldThrow_WhenAuthorLengthGreaterThan256() {
        JsonObject testJsonObject = mock(JsonObject.class);
        when(testJsonObject.getString("title")).thenReturn("title");
        when(testJsonObject.getString("url")).thenReturn("https://www.google.co.uk/");
        when(testJsonObject.getString("by")).thenReturn(generate256CharsLongString());
        try {
            JsonToPostConverter.createPostFromJsonObject(testJsonObject, 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("One or more fields of this post are in wrong format", e.getMessage());
        }
    }

    @Test
    public void createPostFromJsonObject_ShouldThrow_WhenPointsLessThanZero() {
        JsonObject testJsonObject = mock(JsonObject.class);
        when(testJsonObject.getString("title")).thenReturn("title");
        when(testJsonObject.getString("url")).thenReturn("https://www.google.co.uk/");
        when(testJsonObject.getString("by")).thenReturn("");
        when(testJsonObject.getInt("score")).thenReturn(-1);
        try {
            JsonToPostConverter.createPostFromJsonObject(testJsonObject, 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("One or more fields of this post are in wrong format", e.getMessage());
        }
    }

    @Test
    public void createPostFromJsonObject_ShouldThrow_WhenCommentsLessThanZero() {
        JsonObject testJsonObject = mock(JsonObject.class);
        when(testJsonObject.getString("title")).thenReturn("title");
        when(testJsonObject.getString("url")).thenReturn("https://www.google.co.uk/");
        when(testJsonObject.getString("by")).thenReturn(generate256CharsLongString());
        when(testJsonObject.getInt("score")).thenReturn(100);
        when(testJsonObject.getInt("descendants")).thenReturn(-1);
        try {
            JsonToPostConverter.createPostFromJsonObject(testJsonObject, 1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("One or more fields of this post are in wrong format", e.getMessage());
        }
    }

    @Test
    public void createPostFromJsonObject_ShouldThrow_WhenRankLessThanZero() {
        JsonObject testJsonObject = mock(JsonObject.class);
        when(testJsonObject.getString("title")).thenReturn("title");
        when(testJsonObject.getString("url")).thenReturn("https://www.google.co.uk/");
        when(testJsonObject.getString("by")).thenReturn(generate256CharsLongString());
        when(testJsonObject.getInt("score")).thenReturn(100);
        when(testJsonObject.getInt("descendants")).thenReturn(100);
        try {
            JsonToPostConverter.createPostFromJsonObject(testJsonObject, -1);
            fail("IllegalArgumentException expected");
        } catch (IllegalArgumentException e) {
            assertEquals("One or more fields of this post are in wrong format", e.getMessage());
        }
    }

    private static String generate256CharsLongString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 257; i++) {
            sb.append("a");
        }
        return sb.toString();
    }

}
