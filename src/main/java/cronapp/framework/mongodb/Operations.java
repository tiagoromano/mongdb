package cronapp.framework.mongodb;

import cronapi.CronapiMetaData;
import cronapi.Var;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.bson.Document;
import java.util.function.Consumer;
import com.mongodb.client.*;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.result.DeleteResult;

/**
 * Classe que representa ...
 * 
 * @author Romano
 * @version 1.0
 * @since 2024-03-13
 *
 */
 
@CronapiMetaData
public final class Operations {

	private static final Logger log = LoggerFactory.getLogger(Operations.class);

	public static Var find(Var uriConnection, Var database, Var collection, Var filter) {


       try (MongoClient mongoClient = MongoClients.create(uriConnection.getObjectAsString())) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(database.getObjectAsString());
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection.getObjectAsString());
           
			var parser = new JsonParser();
			var array = new JsonArray();
			Document mongoFilter = null;
			FindIterable<Document> documents = null;
			
			if (filter != null && !filter.isEmpty()) {
				mongoFilter = Document.parse(filter.getObjectAsString());
				documents = mongoCollection.find(mongoFilter);
			}
			else {
				documents = mongoCollection.find();
			}
			documents.forEach((Consumer<? super Document>) (Document da) -> array.add(parser.parse(da.toJson())));
			return Var.valueOf(array);
		}


	}

	public static Var insert(Var uriConnection, Var database, Var collection, Var jsonDocument) {
		try (MongoClient mongoClient = MongoClients.create(uriConnection.getObjectAsString())) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(database.getObjectAsString());
			
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection.getObjectAsString());
			Document document = Document.parse(jsonDocument.getObjectAsString());

			mongoCollection.insertOne(document);
			Object id = document.get("_id");
			return Var.valueOf(id);
        } 
	}

	public static Var update(Var uriConnection, Var database, Var collection, Var filter, Var jsonDocument) {
		try (MongoClient mongoClient = MongoClients.create(uriConnection.getObjectAsString())) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(database.getObjectAsString());
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection.getObjectAsString());

			Document mongoFilter = Document.parse(filter.getObjectAsString());
			Document updateDocument = Document.parse(jsonDocument.getObjectAsString());
			UpdateOptions options = new UpdateOptions().upsert(true);
			
			
			UpdateResult result = mongoCollection.updateOne(mongoFilter, updateDocument, options);
			
			return Var.valueOf(result.getModifiedCount());
        } 
	}

	public static Var delete(Var uriConnection, Var database, Var collection, Var filter) {
		try (MongoClient mongoClient = MongoClients.create(uriConnection.getObjectAsString())) {
            MongoDatabase mongoDatabase = mongoClient.getDatabase(database.getObjectAsString());
			MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collection.getObjectAsString());

			Document mongoFilter = Document.parse(filter.getObjectAsString());
			DeleteResult result = mongoCollection.deleteOne(mongoFilter);
			
			return Var.valueOf(result.getDeletedCount());
        } 
	}
}
