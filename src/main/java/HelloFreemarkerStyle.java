import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 9/10/13
 * Time: 19:06
 * To change this template use File | Settings | File Templates.
 */
public class HelloFreemarkerStyle {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloFreemarkerStyle.class, "/");

        try {
            Template template = configuration.getTemplate("hello.ftl");

            Map<String, Object> values = new HashMap<String, Object>();
            values.put("name", "Freemarker");

            StringWriter writer = new StringWriter();
            template.process(values, writer);
            System.out.println(writer);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
