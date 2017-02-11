package scraper;

import data.Post;
import data.PostsAndErrorMessagesContainer;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static data.Constants.POST_INFO_URL;
import static data.Constants.TOP_POSTS_IDS_URL;
import static java.lang.String.format;

public class PostsGetter {

    private PostsIdsFetcher postsIdsFetcher;
    private JsonToPostConverter jsonToPostConverter;

    public PostsGetter(PostsIdsFetcher postsIdsFetcher, JsonToPostConverter
            jsonToPostConverter) {
        this.postsIdsFetcher = postsIdsFetcher;
        this.jsonToPostConverter = jsonToPostConverter;
    }

    /**
     * Returns container with posts and error messages. Gets top posts IDs
     * and then gets needed information about each post. This info is presented
     * in form of Post.
     * <p>
     * If top posts IDs web page is not available, error message is added to
     * the resulting container. If any of the posts web pages is not
     * available or post information is in wrong format, error messages about
     * each of those posts is added to the resulting container.
     * <p>
     * If number of top posts IDs available on the corresponding web page is
     * less than input number of posts, all the available posts or corresponding
     * error messages are added to the resulting container with an
     * additional error message informing about that.
     *
     * @param postsNumber number of posts to be added to the output
     * @return PostsAndErrorMessagesContainer containing posts and/or info
     * about errors occurred while processing posts.
     */
    //TODO: docs - doesn't guarantee that all postNumber posts will return
    public PostsAndErrorMessagesContainer getPosts(int postsNumber) {
        List<String> errorMessages = new ArrayList<>();
        List<Integer> postsIds;

        // get top posts IDs
        try {
            JsonArray postsIdsAsJsonArray = postsIdsFetcher
                    .downloadPostsIdsAsJsonArray
                            (TOP_POSTS_IDS_URL);
            postsIds = postsIdsFetcher
                    .convertJsonArrayToList(postsIdsAsJsonArray);
        } catch (RuntimeException e) {
            errorMessages.add(e.getMessage());
            return new PostsAndErrorMessagesContainer(Collections
                    .<Post>emptyList(), errorMessages);
        }

        // get posts info
        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < Math.min(postsNumber, postsIds.size()); i++) {
            int rank = i + 1;
            try {
                String postUrl = format(POST_INFO_URL, postsIds.get(i));
                JsonObject postAsJsonObject = jsonToPostConverter
                        .downloadPostAsJsonObject(postUrl);
                Post post = jsonToPostConverter.convertJsonObjectToPost(
                        postAsJsonObject, rank);
                posts.add(post);
            } catch (RuntimeException e) {
                errorMessages.add("Post " + rank + ": " + e.getMessage());
            }
        }
        if (postsIds.size() < postsNumber) {
            errorMessages.add("Only " + postsIds.size() + " top posts " +
                    "available");
        }
        return new PostsAndErrorMessagesContainer(posts, errorMessages);
    }
}
