package scraper;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class PostsIdsDownloader {
    private WebPageDownloader webPageDownloader;

    public PostsIdsDownloader(WebPageDownloader webPageDownloader) {
        this.webPageDownloader = webPageDownloader;
    }

    /**
     * Downloads top posts IDs from the given web address and returns them in JsonArray format.
     *
     * @param topPostsUrl top posts IDs web address
     * @return top posts IDs in JsonArray format
     */
    public JsonArray downloadPostsIdsAsJsonArray(String topPostsUrl) {
        JsonArray jsonArray;
        try (JsonReader jsonReader = Json.createReader(new StringReader(webPageDownloader.downloadWebPage(topPostsUrl)))) {
            jsonArray = jsonReader.readArray();
        }
        return jsonArray;
    }

    /**
     * Returns a List of Integer created from JsonArray of top posts IDs.
     *
     * @param jsonArray top posts ID given in JsonArray format
     * @return List of top posts IDs
     */
    public List<Integer> convertJsonArrayToList(JsonArray jsonArray) {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            ids.add(jsonArray.getInt(i));
        }
        return ids;
    }
}
