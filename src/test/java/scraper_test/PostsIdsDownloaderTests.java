package scraper_test;

import org.junit.Before;
import org.junit.Test;
import scraper.PostsIdsDownloader;
import scraper.WebPageDownloader;

import javax.json.Json;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostsIdsDownloaderTests {

    private WebPageDownloader webPageDownloader;
    private PostsIdsDownloader postsIdsDownloader;

    @Before
    public void setUp() {
        webPageDownloader = mock(WebPageDownloader.class);
        postsIdsDownloader = new PostsIdsDownloader(webPageDownloader);
    }

    @Test
    public void downloadPostsIdsAsJsonArray_shouldThrow_WhenPostsIdsInWrongFormat() {
        when(webPageDownloader.downloadWebPage("abc")).thenReturn("}:=3");
        try {
            postsIdsDownloader.downloadPostsIdsAsJsonArray("abc");
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
                postsIdsDownloader.downloadPostsIdsAsJsonArray("abc")
        );
    }

    @Test
    public void convertJsonArrayToList_ShouldReturnIntegerList() {
        assertEquals(
                Arrays.asList(1, 2, 3),
                postsIdsDownloader.convertJsonArrayToList(Json.createArrayBuilder().add(1).add(2).add(3).build())
        );
    }
}
