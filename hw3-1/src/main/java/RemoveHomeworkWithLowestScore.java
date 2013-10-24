import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 24/10/13
 * Time: 20:11
 * To change this template use File | Settings | File Templates.
 */
public class RemoveHomeworkWithLowestScore {

    public static void main(String[] args) {
        MongoClient client = null;
        try {
            client = new MongoClient();
        } catch (UnknownHostException e) {
            System.err.println("Error trying to connect with MongoDB");
            System.exit(1);
        }
        final DB db = client.getDB("school");
        final DBCollection students = db.getCollection("students");
        DBCursor allStudents = null;
        try {
            allStudents = students.find();
            while (allStudents.hasNext()) {
                final DBObject currentStudent = allStudents.next();
                List<BasicDBObject> homework = homeworksWithLowestScoreRemoved(currentStudent);
                currentStudent.put("scores", homework);
                students.update(new BasicDBObject("_id", currentStudent.get("_id")), currentStudent, true, false);
            }
        } finally {
            allStudents.close();
        }


    }

    private static List<BasicDBObject> homeworksWithLowestScoreRemoved(final DBObject currentStudent) {
        List<BasicDBObject> homework = (List) currentStudent.get("scores");

        BasicDBObject toRemove = getHomeworkWithMinimumScore(homework);
        homework.remove(toRemove);

        return homework;
    }

    private static BasicDBObject getHomeworkWithMinimumScore(final List<BasicDBObject> homework) {
        double minScore = Double.MAX_VALUE;
        BasicDBObject toRemove = null;
        for (BasicDBObject hw : homework) {
            if (isHomework(hw)) {
                double currentScore = (Double) hw.get("score");
                if (currentScore < minScore) {
                    minScore = currentScore;
                    toRemove = hw;
                }
            }
        }
        return toRemove;
    }

    private static boolean isHomework(final BasicDBObject hw) {
        return ((String) hw.get("type")).equalsIgnoreCase("homework");
    }


}
