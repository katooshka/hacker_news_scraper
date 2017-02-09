package scraper;

import data.Post;

import static data.Constants.*;

public class PostsGetter {

    private PostsIdsDownloader postsIdsDownloader;
    private JsonToPostConverter jsonToPostConverter;

    public PostsGetter(PostsIdsDownloader postsIdsDownloader, JsonToPostConverter jsonToPostConverter) {
        this.postsIdsDownloader = postsIdsDownloader;
        this.jsonToPostConverter = jsonToPostConverter;
    }

    public Post[] getPosts(int postsNumber) {
        String[] postsIds = postsIdsDownloader.transformPostsIdsStringToArray(
                postsIdsDownloader.downloadTopPostsIdsAsStringFromUrl(TOP_500_POSTS_LINK + LINK_ENDING),
                postsNumber
        );
        Post[] posts = new Post[postsNumber];
        for (int i = 0; i < postsIds.length; i++) {
            try {
                posts[i] = jsonToPostConverter.createPostFromJsonObject(
                        jsonToPostConverter.downloadJsonObject(EMPTY_POST_INFO_LINK + postsIds[i] + LINK_ENDING),
                        i + 1
                );
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }
        }
        return posts;
    }
}
