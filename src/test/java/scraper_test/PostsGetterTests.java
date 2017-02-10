package scraper_test;

import data.Post;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;
import scraper.JsonToPostConverter;
import scraper.PostsGetter;
import scraper.PostsIdsDownloader;
import scraper.WebPageDownloader;

import javax.json.Json;
import javax.json.JsonObject;

import static data.Constants.*;
import static org.mockito.ArgumentMatchers.doubleThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostsGetterTests {
    private PostsIdsDownloader postsIdsDownloader;
    private JsonToPostConverter jsonToPostConverter;
    private PostsGetter postsGetter;
//    private WebPageDownloader webPageDownloader;

    @Before
    public void setUp() {
        postsIdsDownloader = mock(PostsIdsDownloader.class);
        jsonToPostConverter = mock(JsonToPostConverter.class);
//        webPageDownloader = mock(WebPageDownloader.class);
//        postsIdsDownloader = new PostsIdsDownloader(webPageDownloader);
//        jsonToPostConverter = new JsonToPostConverter(webPageDownloader);
        postsGetter = new PostsGetter(postsIdsDownloader, jsonToPostConverter);
    }

//    @Test
//    public void getPosts_ShouldReturnPosts() {
//        when(postsIdsDownloader.downloadPostsIdsAsJsonArray(TOP_500_POSTS_LINK + LINK_ENDING))
//                .thenReturn(Json.createArrayBuilder().add(3).add(4).add(5).build());
//        for (int i = 3; i < 6; i++) {
//            generateMockJsonObjects(i);
//        }
//        Post[] expected = new Post[3];
//        for (int i = 0; i < 3; i++) {
//            expected[i] = generateExpectedPost(i + 1);
//        }
//        Assert.assertArrayEquals(expected, postsGetter.getPosts(3));
//    }
//
//    private OngoingStubbing<JsonObject> generateMockJsonObjects(int postId) {
//        return when(jsonToPostConverter.downloadPostAsJsonObject(EMPTY_POST_INFO_LINK + postId + LINK_ENDING))
//                .thenReturn(
//                        Json.createObjectBuilder()
//                                .add("title", "title")
//                                .add("uri", "url")
//                                .add("by", "")
//                                .add("score", 1)
//                                .add("descendants", 1)
//                                .build()
//                );
//    }
//
//    private Post generateExpectedPost(int i) {
//        return new Post.Builder()
//                .setTitle("title")
//                .setUri("https://www.google.co.uk")
//                .setAuthor("author")
//                .setPoints(1)
//                .setComments(1)
//                .setRank(i)
//                .build();
//    }
}
