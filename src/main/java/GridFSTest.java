import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: david
 * Date: 23/10/13
 * Time: 19:58
 * To change this template use File | Settings | File Templates.
 */
public class GridFSTest {

    public static void main(String[] args) {

        try {

            if (args.length < 1) throw new IllegalArgumentException("Please, give a filename in the first argument");

            MongoClient client = new MongoClient();
            DB db = client.getDB("course");


            FileInputStream fis = new FileInputStream(args[0]);

            GridFS grid = new GridFS(db, "videos");
            GridFSFile gridFile = grid.createFile(fis, "wat.mov");
            gridFile.put("content-type", "video/x-quicktime");
            DBObject metadata = new BasicDBObject("description", "Funny things with Javascript and Ruby");
            metadata.put("tags", Arrays.asList("languages", "fun", "wat"));

            gridFile.setMetaData(metadata);
            gridFile.save();

            System.out.println("Inserted ObjectID --> " + gridFile.get("_id"));
            // Now, retrieve the saved video and store it with a different name

            GridFSDBFile dbFile = grid.findOne(new BasicDBObject("filename", "wat.mov"));
            InputStream inputStream = dbFile.getInputStream();

            Files.copy(inputStream, Paths.get("output.mov"), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("The file copy was done!");


        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
