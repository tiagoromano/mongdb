package blockly;

import cronapi.*;
import cronapi.rest.security.CronappSecurity;
import java.util.concurrent.Callable;

import org.bson.Document;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import static com.mongodb.client.model.Filters.eq;

@CronapiMetaData(type = "blockly")
@CronappSecurity
public class MongoExe1 {

public static final int TIMEOUT = 300;

/**
 *
 * @author Root
 * @since 13/03/2024, 11:08:02
 *
 */
public static Var Executar() throws Exception {
 return new Callable<Var>() {

   public Var call() throws Exception {


     MongoDBTests.call();


    return Var.VAR_NULL;
   }
 }.call();
}

}

