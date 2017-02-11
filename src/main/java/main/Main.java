package main;

import data.Post;
import data.PostsAndErrorMessagesContainer;
import scraper.*;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static main.ArgumentsReader.readArguments;

public class Main {

    private PostsGetter postsGetter;
    private PostToJsonConverter postToJsonConverter;

    public Main(PostsGetter postsGetter, PostToJsonConverter
            postToJsonConverter) {
        this.postsGetter = postsGetter;
        this.postToJsonConverter = postToJsonConverter;
    }

    /**
     * Initialises required objects, creates new Main object and invokes run
     * method.
     *
     */
    public static void main(String[] args) {
        WebPageDownloader webPageDownloader = new WebPageDownloader();
        PostsGetter postsGetter = new PostsGetter(
                new PostsIdsFetcher(webPageDownloader),
                new JsonToPostConverter(webPageDownloader)
        );
        PostToJsonConverter postToJsonConverter = new PostToJsonConverter();

        Main main = new Main(postsGetter, postToJsonConverter);
        main.run(args);
    }

    /**
     * Prints top posts from Hacker News to the standard output in JSON format.
     * Checks if passed arguments are valid, fetches posts and prints them.
     * Error messages are printed to the standard error output.
     *
     * @param args command line arguments
     */

    public void run(String[] args) {
        //check arguments are valid
        int postsNumber;
        try {
            postsNumber = readArguments(args);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            showUsage();
            return;
        }

        //print posts
        PostsAndErrorMessagesContainer container = postsGetter.getPosts
                (postsNumber);
        JsonArray jsonArray = convertPostsToJsonArray(container.getPosts());
        printJsonArray(jsonArray, System.out);

        //print error messages
        List<String> errorMessages = container.getErrorMessages();
        for (String errorMessage : errorMessages) {
            System.err.println(errorMessage);
        }
    }

    public JsonArray convertPostsToJsonArray(List<Post> posts) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (Post post : posts) {
            jsonArrayBuilder.add(postToJsonConverter.createJsonFromPost(post));
        }
        return jsonArrayBuilder.build();
    }

    private static void showUsage() {
        System.err.println("Usage: hackernews --posts <number of posts>");
    }

    public void printJsonArray(JsonArray jsonArray, PrintStream ps) {
        Map<String, String> generatorConfig = new HashMap<>();
        generatorConfig.put(JsonGenerator.PRETTY_PRINTING, "true");
        JsonWriterFactory writerFactory = Json.createWriterFactory
                (generatorConfig);
        JsonWriter jsonWriter = writerFactory.createWriter(ps,
                StandardCharsets.UTF_8);
        jsonWriter.writeArray(jsonArray);
        jsonWriter.close();
    }
}
