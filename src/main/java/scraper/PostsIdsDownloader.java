package scraper;

import java.util.Arrays;

public class PostsIdsDownloader {
    private WebPageDownloader webPageDownloader;

    public PostsIdsDownloader(WebPageDownloader webPageDownloader) {
        this.webPageDownloader = webPageDownloader;
    }

    public String downloadTopPostsIdsAsStringFromUrl(String topPostsUrl) {
        return webPageDownloader.downloadWebPage(topPostsUrl);
    }

    //TODO: find out if I can optimize this parsing
    public String[] transformPostsIdsStringToArray(String string, int postsNumber) {
        String[] ids = string
                .replace("[ ", "")
                .replace("[", "")
                .replace(" ]", "")
                .replace("]", "")
                .split(",\\s+");
        return Arrays.copyOf(ids, postsNumber);
    }
}
