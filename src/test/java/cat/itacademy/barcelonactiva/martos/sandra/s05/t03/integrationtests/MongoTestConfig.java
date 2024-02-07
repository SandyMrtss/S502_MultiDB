package cat.itacademy.barcelonactiva.martos.sandra.s05.t03.integrationtests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MongoTestConfig {
    public static void startMongo() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017/DiceGameTestDB");
        MongoDatabase mongoDatabase = mongoClient.getDatabase("DiceGameTestDB");
        MongoCollection<Document> games = mongoDatabase.getCollection("games");
        Bson filter = Filters.eq("_class", "cat.itacademy.barcelonactiva.martos.sandra.s05.t03.model.domain.GameHistory");
        games.deleteMany(filter);

        List<Document> initialData = objectMapper.readValue(
                new File("src/test/resources/DiceGameTestDB_data.json"),
                new TypeReference<List<Document>>() {
                });

        games.insertMany(initialData);
        System.out.println("Mongo data initialized successfully");

    }
}
