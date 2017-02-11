package scraper_test;

import org.junit.Before;
import org.junit.Test;
import scraper.PostsIdsFetcher;
import scraper.WebPageDownloader;

import javax.json.Json;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostsIdsFetcherTests {

    private WebPageDownloader webPageDownloader;
    private PostsIdsFetcher postsIdsFetcher;

    @Before
    public void setUp() {
        webPageDownloader = mock(WebPageDownloader.class);
        postsIdsFetcher = new PostsIdsFetcher(webPageDownloader);
    }

    @Test
    public void downloadPostsIdsAsJsonArray_shouldThrow_WhenPostsIdsInWrongFormat() {
        when(webPageDownloader.downloadWebPage("abc")).thenReturn("}:=3");
        try {
            postsIdsFetcher.downloadPostsIdsAsJsonArray("abc");
            fail();
        } catch (RuntimeException e) {
            //expected
        }
    }

    @Test
    public void downloadPostsIdsAsJsonArray_ShouldReturnJsonArray() {
        when(webPageDownloader.downloadWebPage("abc")).thenReturn("[1, 2, 3]");
        assertEquals(
                Json.createArrayBuilder().add(1).add(2).add(3).build(),
                postsIdsFetcher.downloadPostsIdsAsJsonArray("abc")
        );
    }

    @Test
    public void convertJsonArrayToList_ShouldReturnIntegerList() {
        assertEquals(
                Arrays.asList(1, 2, 3),
                postsIdsFetcher.convertJsonArrayToList(Json.createArrayBuilder().add(1).add(2).add(3).build())
        );
    }
}
