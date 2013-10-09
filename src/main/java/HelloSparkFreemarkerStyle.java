import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 9/10/13
 * Time: 19:11
 * To change this template use File | Settings | File Templates.
 */
public class HelloSparkFreemarkerStyle {


    public static void main(String[] args) {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloSparkFreemarkerStyle.class, "/");

        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                StringWriter writer = null;
                try {
                    Template template = configuration.getTemplate("hello.ftl");

                    Map<String, Object> values = new HashMap<String, Object>();
                    values.put("name", "FreeMarker");
                    writer = new StringWriter();

                    template.process(values, writer);
                } catch (IOException e) {
                    e.printStackTrace();
                    halt(500);
                } catch (TemplateException e) {
                    e.printStackTrace();
                    halt(500);
                }
                return writer;
            }
        });
    }
}
