import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 9/10/13
 * Time: 18:54
 * To change this template use File | Settings | File Templates.
 */
public class HelloSparkStyle {
    public static void main(String[] args) {
        Spark.get(new Route("/") {

            @Override
            public Object handle(Request request, Response response) {
                return "Hello World From Spark";  //To change body of implemented methods use File | Settings | File Templates.
            }
        });


    }
}
