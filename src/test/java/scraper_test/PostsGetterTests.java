package scraper_test;

import data.Post;
import data.PostsAndErrorMessagesContainer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import scraper.JsonToPostConverter;
import scraper.PostsGetter;
import scraper.PostsIdsFetcher;
import scraper.WebPageDownloader;

import java.util.ArrayList;
import java.util.List;

import static data.Constants.*;
import static java.lang.String.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostsGetterTests {
    private WebPageDownloader webPageDownloader;
    private PostsIdsFetcher postsIdsFetcher ;
    private JsonToPostConverter jsonToPostConverter;
    private PostsGetter postsGetter;

    private static final String testPost111 = "{\n" +
            "  \"by\" : \"author111\",\n" +
            "  \"descendants\" : 111,\n" +
            "  \"id\" : 111,\n" +
            "  \"kids\" : [ 8952, 8876 ],\n" +
            "  \"score\" : 111,\n" +
            "  \"time\" : 1175714200,\n" +
            "  \"title\" : \"title111\",\n" +
            "  \"type\" : \"story\",\n" +
            "  \"url\" : \"https://www.google.co.uk/\"\n" +
            "}";

    private static final String testPost222 = "{\n" +
            "  \"by\" : \"author222\",\n" +
            "  \"descendants\" : 222,\n" +
            "  \"id\" : 222,\n" +
            "  \"kids\" : [ 8952, 8876 ],\n" +
            "  \"score\" : 222,\n" +
            "  \"time\" : 1175714200,\n" +
            "  \"title\" : \"title222\",\n" +
            "  \"type\" : \"story\",\n" +
            "  \"url\" : \"https://www.google.co.uk/\"\n" +
            "}";

    private static final String testPost333 = "{\n" +
            "  \"by\" : \"\",\n" +
            "  \"descendants\" : 333,\n" +
            "  \"id\" : 333,\n" +
            "  \"kids\" : [ 8952, 8876 ],\n" +
            "  \"score\" : 333,\n" +
            "  \"time\" : 1175714200,\n" +
            "  \"title\" : \"\",\n" +
            "  \"type\" : \"story\",\n" +
            "  \"url\" : \"https://www.google.co.uk/\"\n" +
            "}";

    private static final Post expectedPost111 = new Post.Builder()
            .setTitle("title111")
            .setUri("https://www.google.co.uk/")
            .setAuthor("author111")
            .setPoints(111)
            .setComments(111)
            .setRank(1)
            .build();

    private static final Post expectedPost222 = new Post.Builder()
            .setTitle("title222")
            .setUri("https://www.google.co.uk/")
            .setAuthor("author222")
            .setPoints(222)
            .setComments(222)
            .setRank(2)
            .build();

    @Before
    public void setUp() {
        webPageDownloader = mock(WebPageDownloader.class);
        postsIdsFetcher = new PostsIdsFetcher(webPageDownloader);
        jsonToPostConverter = new JsonToPostConverter(webPageDownloader);
        postsGetter = new PostsGetter(postsIdsFetcher, jsonToPostConverter);
    }

    @Test
    public void
    postsGetter_ShouldReturnContainerWithErrorMessage_WhenTopPostsIdsWebPageInaccessible() {
        String expectedErrorMessage = "Error occurred while " +
                "downloading web page: " + TOP_POSTS_IDS_URL;
        when(webPageDownloader.downloadWebPage(TOP_POSTS_IDS_URL)).thenThrow
                (new RuntimeException
                (expectedErrorMessage));
        List<String> expectedErrorMessages = new ArrayList<>();
        expectedErrorMessages.add(expectedErrorMessage);
        List<Post> expectedPosts = new ArrayList<>();
        PostsAndErrorMessagesContainer expectedContainer = new
                PostsAndErrorMessagesContainer(expectedPosts,
                expectedErrorMessages);
        assertEquals(expectedContainer, postsGetter.getPosts(2));
    }

    @Test
    public void
    postsGetter_ShouldReturnContainerWithoutErrorMessages() {
        when(webPageDownloader.downloadWebPage(TOP_POSTS_IDS_URL))
                .thenReturn("[ 111, 222, 333 ]");
        when(webPageDownloader.downloadWebPage(String.format
                (POST_INFO_URL, 111))).thenReturn(testPost111);
        when(webPageDownloader.downloadWebPage(String.format
                (POST_INFO_URL, 222))).thenReturn(testPost222);
        List<String> expectedErrorMessages = new ArrayList<>();
        List<Post> expectedPosts = new ArrayList<>();
        expectedPosts.add(expectedPost111);
        expectedPosts.add(expectedPost222);
        PostsAndErrorMessagesContainer expectedContainer = new
                PostsAndErrorMessagesContainer(expectedPosts,
                expectedErrorMessages);
        assertEquals(expectedContainer, postsGetter.getPosts(2));
    }

    @Test
    public void
    postsGetter_ShouldReturnContainerWithErrorMessages_WhenSomePostWebPagesInaccessible() {
        when(webPageDownloader.downloadWebPage(TOP_POSTS_IDS_URL))
                .thenReturn("[ 111, 222, 333 ]");
        when(webPageDownloader.downloadWebPage(String.format
                (POST_INFO_URL, 111))).thenReturn(testPost111);
        String expectedErrorMessage = "Error occurred while " +
                "downloading web page: " + String.format
                (POST_INFO_URL, 222);
        when(webPageDownloader.downloadWebPage(String.format
                (POST_INFO_URL, 222)))
                .thenThrow(new RuntimeException(expectedErrorMessage));
        List<String> expectedErrorMessages = new ArrayList<>();
        List<Post> expectedPosts = new ArrayList<>();
        expectedPosts.add(expectedPost111);
        expectedErrorMessages.add("Post 2: " + expectedErrorMessage);
        PostsAndErrorMessagesContainer expectedContainer = new
                PostsAndErrorMessagesContainer(expectedPosts,
                expectedErrorMessages);
        assertEquals(expectedContainer, postsGetter.getPosts(2));
    }

    @Test
    public void
    postsGetter_ShouldReturnContainerWithoutPosts_WhenAllPostWebPagesInaccessible() {
        when(webPageDownloader.downloadWebPage(TOP_POSTS_IDS_URL))
                .thenReturn("[ 111, 222, 333 ]");
        String expectedErrorMessage1 = "Error occurred while " +
                "downloading web page: " + format(POST_INFO_URL, 111);
        when(webPageDownloader.downloadWebPage(format(POST_INFO_URL,
                111)))
                .thenThrow(new RuntimeException(expectedErrorMessage1));
        String expectedErrorMessage2 = "Error occurred while " +
                "downloading web page: " + format(POST_INFO_URL, 222);
        when(webPageDownloader.downloadWebPage(format(POST_INFO_URL,
                222)))
                .thenThrow(new RuntimeException(expectedErrorMessage2));
        List<String> expectedErrorMessages = new ArrayList<>();
        List<Post> expectedPosts = new ArrayList<>();
        expectedErrorMessages.add("Post 1: " + expectedErrorMessage1);
        expectedErrorMessages.add("Post 2: " + expectedErrorMessage2);
        PostsAndErrorMessagesContainer expectedContainer = new
                PostsAndErrorMessagesContainer(expectedPosts,
                expectedErrorMessages);
        assertEquals(expectedContainer, postsGetter.getPosts(2));
    }

    @Test
    public void
    postsGetter_ShouldReturnContainerWithOnePost_WhenArgumentIsTwoAndOneTopPostExists() {
        when(webPageDownloader.downloadWebPage(TOP_POSTS_IDS_URL))
                .thenReturn("[ 111 ]");
        when(webPageDownloader.downloadWebPage(format
                (POST_INFO_URL, 111))).thenReturn(testPost111);
        List<String> expectedErrorMessages = new ArrayList<>();
        List<Post> expectedPosts = new ArrayList<>();
        expectedErrorMessages.add("Only 1 top posts available");
        expectedPosts.add(expectedPost111);
        PostsAndErrorMessagesContainer expectedContainer = new
                PostsAndErrorMessagesContainer(expectedPosts,
                expectedErrorMessages);
        assertEquals(expectedContainer, postsGetter.getPosts(2));
    }

    @Test
    public void
    postsGetter_ShouldReturnContainerWithoutPosts_WhenPostInWrongFormat() {
        when(webPageDownloader.downloadWebPage(TOP_POSTS_IDS_URL))
                .thenReturn("[ 333 ]");
        when(webPageDownloader.downloadWebPage(format
                (POST_INFO_URL, 333))).thenReturn(testPost333);
        List<String> expectedErrorMessages = new ArrayList<>();
        List<Post> expectedPosts = new ArrayList<>();
        expectedErrorMessages.add("Post 1: One or more fields of this post " +
                "are in a wrong format");
        PostsAndErrorMessagesContainer expectedContainer = new
                PostsAndErrorMessagesContainer(expectedPosts,
                expectedErrorMessages);
        assertEquals(expectedContainer, postsGetter.getPosts(1));
    }
}
