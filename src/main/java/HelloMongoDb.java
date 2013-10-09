import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 8/10/13
 * Time: 19:05
 * To change this template use File | Settings | File Templates.
 */

public class HelloMongoDb {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB database = client.getDB("test");
        DBCollection names = database.getCollection("names");
        System.out.println(names.findOne());
    }
}
