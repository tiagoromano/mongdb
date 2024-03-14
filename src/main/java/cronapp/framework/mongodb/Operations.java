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

	public static Var find(Var uriConnection, Var database, Var collection, Var filter) {


       try (var mongoClient = MongoClients.create(uriConnection.getObjectAsString())) {
            var mongoDatabase = mongoClient.getDatabase(database.getObjectAsString());
            var mongoCollection = mongoDatabase.getCollection(collection.getObjectAsString());
           
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
		try (var mongoClient = MongoClients.create(uriConnection.getObjectAsString())) {
            var mongoDatabase = mongoClient.getDatabase(database.getObjectAsString());
			
			var<Document> mongoCollection = mongoDatabase.getCollection(collection.getObjectAsString());
			var document = Document.parse(jsonDocument.getObjectAsString());

			mongoCollection.insertOne(document);
			var id = document.get("_id");
			return Var.valueOf(id);
        } 
	}

	public static Var update(Var uriConnection, Var database, Var collection, Var filter, Var jsonDocument) {
		try (var mongoClient = MongoClients.create(uriConnection.getObjectAsString())) {
            var mongoDatabase = mongoClient.getDatabase(database.getObjectAsString());
			var mongoCollection = mongoDatabase.getCollection(collection.getObjectAsString());

			var mongoFilter = Document.parse(filter.getObjectAsString());
			var updateDocument = Document.parse(jsonDocument.getObjectAsString());
			var options = new UpdateOptions().upsert(true);
			
			
			var result = mongoCollection.updateOne(mongoFilter, updateDocument, options);
			
			return Var.valueOf(result.getModifiedCount());
        } 
	}

	public static Var delete(Var uriConnection, Var database, Var collection, Var filter) {
		try (var mongoClient = MongoClients.create(uriConnection.getObjectAsString())) {
            var mongoDatabase = mongoClient.getDatabase(database.getObjectAsString());
			var mongoCollection = mongoDatabase.getCollection(collection.getObjectAsString());

			var mongoFilter = Document.parse(filter.getObjectAsString());
			var result = mongoCollection.deleteOne(mongoFilter);
			
			return Var.valueOf(result.getDeletedCount());
        } 
	}
}
