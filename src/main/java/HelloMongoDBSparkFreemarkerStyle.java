import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 9/10/13
 * Time: 19:21
 * To change this template use File | Settings | File Templates.
 */
public class HelloMongoDBSparkFreemarkerStyle {
    public static void main(String[] args) throws UnknownHostException {
       final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloMongoDBSparkFreemarkerStyle.class, "/");

        final MongoClient client = new MongoClient();
        final DB database = client.getDB("test");


        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                StringWriter writer = new StringWriter();
                try {
                    Template template = configuration.getTemplate("hello.ftl");
                    DBCollection collection = database.getCollection("names");
                    DBObject object = collection.findOne();

                    template.process(object, writer);
                } catch (Exception e) {
                    halt(500);
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                return writer;
            }
        });

    }
}
