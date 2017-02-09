package scraper_test;

import org.junit.Test;
import scraper.PostsIdsDownloader;
import scraper.WebPageDownloader;

import static org.junit.Assert.assertArrayEquals;

public class PostsIdsDownloaderTests {

    @Test
    public void transformPostsIdsStringToArray_ShouldReturnArray() {
        String testString = "[123, 456,, 789,  123[, 45[]6, [7],8,9, 333, 333, 333";
        int postNumber = 6;
        PostsIdsDownloader postsIdsDownloader = new PostsIdsDownloader(new WebPageDownloader());
        assertArrayEquals(
                new String[]{"123", "456,", "789", "123", "456", "7,8,9"},
                postsIdsDownloader.transformPostsIdsStringToArray(testString, postNumber));
    }
}
