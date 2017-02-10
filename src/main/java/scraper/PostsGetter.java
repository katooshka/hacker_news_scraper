package scraper;

import data.Post;

import java.util.List;

import static data.Constants.*;

public class PostsGetter {

    private PostsIdsDownloader postsIdsDownloader;
    private JsonToPostConverter jsonToPostConverter;

    public PostsGetter(PostsIdsDownloader postsIdsDownloader, JsonToPostConverter jsonToPostConverter) {
        this.postsIdsDownloader = postsIdsDownloader;
        this.jsonToPostConverter = jsonToPostConverter;
    }

    public Post[] getPosts(int postsNumber) {
        List<Integer> postsIds = postsIdsDownloader.convertJsonArrayToList(
                postsIdsDownloader.downloadPostsIdsAsJsonArray(TOP_500_POSTS_LINK + LINK_ENDING)
        );
        Post[] posts = new Post[postsNumber];
        for (int i = 0; i < postsNumber; i++) {
            try {
                posts[i] = jsonToPostConverter.convertJsonObjectToPost(jsonToPostConverter.downloadPostAsJsonObject(
                                EMPTY_POST_INFO_LINK + postsIds.get(i) + LINK_ENDING), i + 1
                );
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
            }
        }
        return posts;
    }
}
