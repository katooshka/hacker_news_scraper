package scraper;

import data.Post;

import java.io.IOException;

import static data.Constants.JSON_FORMAT_ENDING;
import static data.Constants.EMPTY_POST_INFO_LINK;
import static data.Constants.TOP_500_POSTS_LINK;
import static scraper.JsonToPostConverter.createPostFromJsonObject;
import static scraper.JsonToPostConverter.downloadJsonObject;
import static scraper.PostsIdsDownloader.downloadTopPostsIDsAsStringFromUrl;
import static scraper.PostsIdsDownloader.transformPostsIdsStringToArray;

public class PostsGetter {

    //TODO: get rid of static to be able to mock
    public static Post[] getPosts(int postsNumber) throws IOException {
        String[] postsIds = transformPostsIdsStringToArray(
                downloadTopPostsIDsAsStringFromUrl(TOP_500_POSTS_LINK + JSON_FORMAT_ENDING),
                postsNumber
        );
        Post[] posts = new Post[postsNumber];
        for (int i = 0; i < postsIds.length; i++) {
            try {
                posts[i] = createPostFromJsonObject(
                        downloadJsonObject(EMPTY_POST_INFO_LINK + postsIds[i] + JSON_FORMAT_ENDING),
                        i + 1
                );
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
        return posts;
    }
}
