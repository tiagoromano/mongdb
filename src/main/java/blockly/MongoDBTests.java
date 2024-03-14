package blockly;

import org.bson.Document;
import org.bson.conversions.Bson;

import cronapi.Var;
import cronapp.framework.mongodb.Operations;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import static com.mongodb.client.model.Filters.eq;

/**
 * Classe que representa ...
 * 
 * @author Root
 * @version 1.0
 * @since 2024-03-13
 *
 */
 
public class MongoDBTests {

	/**
	 * Construtor
	 **/
	public MongoDBTests (){
	}

	public static void call() {

        Var uri = Var.valueOf("mongodb+srv://tiagoromano:uEgafmRDPImO7gCf@cluster0.0bvglnp.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0");

		// Var result = Operations.find(uri, 
		// Var.valueOf("sample_mflix"), 
		// Var.valueOf("movies"), 
		// Var.valueOf("{ '$or': [ { 'title': 'The Room' }, { 'type': 'dds' } ] }"));
        // System.out.println("printing:");
		// System.out.println(result);

        // Var result = Operations.insert(uri, 
		// Var.valueOf("sample_mflix"), 
		// Var.valueOf("movies"), 
		// Var.valueOf("{'title': 'Teste Romano', 'id': 'xpto'}"));
        // System.out.println("printing:");
		// System.out.println(result);

        //  Var result = Operations.update(uri, 
		// Var.valueOf("sample_mflix"), 
		// Var.valueOf("movies"), 
		// Var.valueOf("{ 'id':'xpto' }"),
        // Var.valueOf("{'$set' : {'title': 'Teste Romano 2'} }"));
        // System.out.println("printing:");
		// System.out.println(result);
		
Var result = Operations.delete(uri, 
		Var.valueOf("sample_mflix"), 
		Var.valueOf("movies"), 
		Var.valueOf("{ 'id':'xpto' }"));
        System.out.println("printing:");
		System.out.println(result);


	}

	public static void call3() {
			String uri = "mongodb+srv://tiagoromano:uEgafmRDPImO7gCf@cluster0.0bvglnp.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

       try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");
            // Creates instructions to project two document fields
            
            
            Bson projectionFields = Projections.fields(
                    Projections.include("title", "imdb"),
                    Projections.excludeId());
            // Retrieves the first matching document, applying a projection and a descending sort to the results
            // Document doc = collection.find(eq("title", "The Room"))
                    // .projection(projectionFields)
                    // .sort(Sorts.descending("imdb.rating"))
                    // .first();

		// Document filter = new Document();
        // filter.append("title", "The Room");
        // filter.append("type", "movi2e");


		// var filter = Filters.and(
		// 		Filters.or(
		// 			Filters.eq("title", "The Room"),
		// 			Filters.eq("type", "movi2e")
		// 		),
		// 		Filters.and(
		// 			Filters.eq("title", "The Room")
		// 		)
		// 	);

            // Prints a message if there are no result documents, or prints the result document as JSON
            // if (doc == null) {
            //     System.out.println("No results found.");
            // } else {
            //     System.out.println(doc.toJson());
            // }
		
			String json = "{ '$or': [ { 'title': 'The Room' }, { 'type': 'dds' } ] }";
			Document filter = Document.parse(json);


			collection.find(filter).forEach((Document da) -> {
				System.out.println("printing:");
				System.out.println(da.toJson());
			});

			// for (Document document : collection.find(filter)) {
			// 	System.out.println("printing:");
			//     System.out.println(document);
			// }
		}
	}
	public static void call2() {
			String uri = "mongodb+srv://tiagoromano:uEgafmRDPImO7gCf@cluster0.0bvglnp.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";

       try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            MongoCollection<Document> collection = database.getCollection("movies");
            // Creates instructions to project two document fields
            
            
            Bson projectionFields = Projections.fields(
                    Projections.include("title", "imdb"),
                    Projections.excludeId());
            // Retrieves the first matching document, applying a projection and a descending sort to the results
            Document doc = collection.find(eq("title", "The Room"))
                    .projection(projectionFields)
                    .sort(Sorts.descending("imdb.rating"))
                    .first();
            // Prints a message if there are no result documents, or prints the result document as JSON
            if (doc == null) {
                System.out.println("No results found.");
            } else {
                System.out.println(doc.toJson());
            }
        }
	}

}
