package scraper;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class WebPageDownloaderTest {

    @Test
    public void downloadWebPage_ShouldThrow_WhenUrlNotValid() {
        try {
            WebPageDownloader webPageDownloader = new WebPageDownloader();
            webPageDownloader.downloadWebPage("abc");
            fail("RuntimeException expected");
        } catch (RuntimeException e) {
            assertEquals("Error occurred while downloading web page: abc", e.getMessage());
        }
    }
}
