package scraper;

import data.Post;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class JsonToPostConverter {

    private WebPageDownloader webPageDownloader;

    public JsonToPostConverter(WebPageDownloader webPageDownloader) {
        this.webPageDownloader = webPageDownloader;
    }

    /**
     * Downloads a post from given web address and returns it in JsonObject format.
     *
     * @param postUrl post web address
     * @return post in a JsonObject format
     */
    public JsonObject downloadPostAsJsonObject(String postUrl) {
        JsonObject jsonObject;
        try (JsonReader jsonReader = Json.createReader(new StringReader(webPageDownloader.downloadWebPage(postUrl)))) {
            jsonObject = jsonReader.readObject();
        }
        return jsonObject;
    }

    /**
     * Returns a new Post created from given JsonObject fields values and a rank value. In order for Post to be created
     * all the JsonObject fields should comply with the format requirements. Otherwise the new Post is not created
     * and IllegalArgumentException is written into STDERR. Title and author should not be empty Strings
     * and their length should not exceed 256. Points, comments and rank values should not be less that zero.
     *
     * @param jsonObject JsonObject with post fields
     * @param rank rank that is needed to create new Post object and is not contained in given JsonObject
     * @throws IllegalArgumentException if one or more fields are presented in a wrong format
     * @return new Post with all required fields
     */

    public Post convertJsonObjectToPost(JsonObject jsonObject, int rank) {
        String title = jsonObject.getString("title", null);
        checkNotNull(title, "Title");
        checkThat(title.isEmpty(), "Title field is empty");
        checkThat(title.length() > 256, "Title is too long");

        String url = jsonObject.getString("url", null);
        checkNotNull(url, "URL");
        checkThat(!URIValidator.isValid(url), "URI is not valid");

        String author = jsonObject.getString("by", null);
        checkNotNull(author, "Author");
        checkThat(author.isEmpty(), "Author field is empty");
        checkThat(author.length() > 256, "Author field is too long");

        int points = jsonObject.getInt("score", -1);
        checkThat(points < 0, "Points is negative number or is not present");

        int comments = jsonObject.getInt("descendants", -1);
        checkThat(comments < 0, "Comments is negative number or is not present");

        checkThat(rank < 0, "Rank is negative number or is not present" );

        return new Post.Builder()
                .setTitle(title)
                .setUri(url)
                .setAuthor(author)
                .setPoints(points)
                .setComments(comments)
                .setRank(rank)
                .build();
    }

    private void checkNotNull(Object object, String name) {
        if (object == null) {
            throw new IllegalArgumentException(name + " field is not present in the post");
        }
    }

    private void checkThat(boolean condition, String errorMessage) {
        if (condition) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
