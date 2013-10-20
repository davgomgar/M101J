import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 20/10/13
 * Time: 16:14
 * To change this template use File | Settings | File Templates.
 */
public class RemoveHomeworkWithLowestScore {
    public static void main(String[] args) {
        DBCollection collection = getDBCollection();
        DBCursor homeworks = null;
        try {
            homeworks = collection.find(new BasicDBObject("type", "homework"))
                                  .sort(new BasicDBObject("student_id", 1).append("score", 1));
            Integer currentId = Integer.MIN_VALUE;
            if (homeworks.hasNext()) {
                BasicDBObject firstObject = (BasicDBObject) homeworks.next();
                currentId = (Integer) firstObject.get("student_id");
                collection.remove(firstObject);
            }
            while (homeworks.hasNext()) {
                BasicDBObject obj = (BasicDBObject) homeworks.next();
                Integer objId = (Integer) obj.get("student_id");
                if (objId.equals(currentId)) {
                    continue;
                }
                currentId = objId;
                collection.remove(obj);
            }
        } finally {
            homeworks.close();
        }

    }

    private static DBCollection getDBCollection() {
        try {
            final MongoClient client = new MongoClient();
            DB db = client.getDB("students");
            return db.getCollection("grades");
        } catch (UnknownHostException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}
