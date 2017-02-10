package scraper;

import data.Post;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PostToJsonConverter {
    public JsonObject createJsonFromPost(Post post) {
        return Json.createObjectBuilder()
                        .add("title", post.getTitle())
                        .add("uri", post.getUri())
                        .add("author", post.getAuthor())
                        .add("points", post.getPoints())
                        .add("comments", post.getComments())
                        .add("rank", post.getRank())
                        .build();
    }

    //TODO: optimize generator
    //TODO: change pretty printing pattern
    public String createStringFromJsonObject(JsonObject jsonObject) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Map<String, String> generatorConfig = new HashMap<>();
        generatorConfig.put(JsonGenerator.PRETTY_PRINTING, "true");
        JsonGeneratorFactory factory = Json.createGeneratorFactory(generatorConfig);
        JsonGenerator gen = factory.createGenerator(baos, StandardCharsets.UTF_8);
        gen.writeStartObject()
                .write("title", jsonObject.getString("title"))
                .write("uri", jsonObject.getString("uri"))
                .write("author", jsonObject.getString("author"))
                .write("points", jsonObject.getInt("points"))
                .write("comments", jsonObject.getInt("comments"))
                .write("rank", jsonObject.getInt("rank"))
                .writeEnd();
        gen.close();
        return new String(baos.toByteArray(), StandardCharsets.UTF_8);
    }
}
