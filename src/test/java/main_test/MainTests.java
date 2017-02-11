package main_test;

import data.Post;
import data.PostsAndErrorMessagesContainer;
import main.Main;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scraper.PostToJsonConverter;
import scraper.PostsGetter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static data.Constants.TOP_POSTS_IDS_URL;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MainTests {

    private static final Post EXPECTED_POST_111 = new Post.Builder()
            .setTitle("title111")
            .setUri("https://www.google.co.uk/")
            .setAuthor("author111")
            .setPoints(111)
            .setComments(111)
            .setRank(1)
            .build();

    private static final Post EXPECTED_POST_222 = new Post.Builder()
            .setTitle("title222")
            .setUri("https://www.google.co.uk/")
            .setAuthor("author222")
            .setPoints(222)
            .setComments(222)
            .setRank(2)
            .build();

    private static final String EXPECTED_OUTPUT = "\n[\n" +
            "    {\n" +
            "        \"title\":\"title111\",\n" +
            "        \"uri\":\"https://www.google.co.uk/\",\n" +
            "        \"author\":\"author111\",\n" +
            "        \"points\":111,\n" +
            "        \"comments\":111,\n" +
            "        \"rank\":1\n" +
            "    },\n" +
            "    {\n" +
            "        \"title\":\"title222\",\n" +
            "        \"uri\":\"https://www.google.co.uk/\",\n" +
            "        \"author\":\"author222\",\n" +
            "        \"points\":222,\n" +
            "        \"comments\":222,\n" +
            "        \"rank\":2\n" +
            "    }\n" +
            "]";

    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;
    private PostsGetter postsGetter;
    private PostToJsonConverter postToJsonConverter;
    private Main main;


    @Before
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        postsGetter = mock(PostsGetter.class);
        postToJsonConverter = new PostToJsonConverter();
        main = new Main(postsGetter, postToJsonConverter);
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void run_ShouldPrintToStderr_WhenArgumentsInWrongFormat() {
        main.run(new String[]{"--posts", "string"});
        assertEquals("Second argument is not a number" + "\n" +
                        "Usage: hackernews --posts <number of posts>\n",
                errContent.toString());
    }

    @Test
    public void run_ErrorMessageToStderr_WhenTopPostsIdsWebPageInaccessible() {
        List<String> errorMessages = new ArrayList<>();
        String expectedError = "Error occurred while downloading web page: " +
        TOP_POSTS_IDS_URL;
        errorMessages.add(expectedError);
        PostsAndErrorMessagesContainer postsAndErrorMessagesContainer = new
                PostsAndErrorMessagesContainer(Collections.emptyList(), errorMessages);
        when(postsGetter.getPosts(3)).thenReturn(postsAndErrorMessagesContainer);
        main.run(new String[]{"--posts", "3"});
        assertEquals(expectedError + "\n", errContent.toString());
    }

    @Test
    public void run_ShouldPrintPostsToStdout() {
        List<Post> posts = new ArrayList<>();
        posts.add(EXPECTED_POST_111);
        posts.add(EXPECTED_POST_222);
        PostsAndErrorMessagesContainer postsAndErrorMessagesContainer =
                new PostsAndErrorMessagesContainer(posts, Collections.emptyList());
        when(postsGetter.getPosts(2)).thenReturn(postsAndErrorMessagesContainer);
        main.run(new String[]{"--posts", "2"});
        assertEquals(EXPECTED_OUTPUT, outContent.toString());
        assertEquals("", errContent.toString());
    }

    @Test
    public void run_ShouldPrintPostsToStdoutAndErrorMessagesToStderr() {
        List<Post> posts = new ArrayList<>();
        posts.add(EXPECTED_POST_111);
        posts.add(EXPECTED_POST_222);
        List<String> errorMessages = new ArrayList<>();
        String expectedError = "Post 3: One or more fields of this post are in a wrong format";
        errorMessages.add(expectedError);
        PostsAndErrorMessagesContainer postsAndErrorMessagesContainer =
                new PostsAndErrorMessagesContainer(posts, errorMessages);
        when(postsGetter.getPosts(3)).thenReturn(postsAndErrorMessagesContainer);
        main.run(new String[]{"--posts", "3"});
        assertEquals(EXPECTED_OUTPUT, outContent.toString());
        assertEquals("Post 3: One or more fields of this post are in a wrong format\n", errContent.toString());
    }
}
