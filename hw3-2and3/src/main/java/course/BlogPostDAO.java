/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package course;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class BlogPostDAO {
    DBCollection postsCollection;

    public BlogPostDAO(final DB blogDatabase) {
        postsCollection = blogDatabase.getCollection("posts");
    }

    // Return a single post corresponding to a permalink
    public DBObject findByPermalink(String permalink) {

        DBObject post = null;
        post = postsCollection.findOne(new BasicDBObject("permalink", permalink));

        return post;
    }

    // Return a list of posts in descending order. Limit determines
    // how many posts are returned.
    public List<DBObject> findByDateDescending(int limit) {

        List<DBObject> posts = null;
        // Return a list of DBObjects, each one a post from the posts collection
        posts = postsCollection.find().sort(new BasicDBObject("date", -1)).limit(limit).toArray(limit);

        return posts;
    }


    public String addPost(String title, String body, List tags, String username) {

        System.out.println("inserting blog entry " + title + " " + body);

        String permalink = title.replaceAll("\\s", "_"); // whitespace becomes _
        permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
        permalink = permalink.toLowerCase();


        BasicDBObject post = new BasicDBObject();

        // Build the post object and insert it
        post.put("author", username);
        post.put("title", title);
        post.put("body", body);
        post.put("permalink", permalink);
        post.put("date", Calendar.getInstance().getTime());
        post.put("tags", tags);
        post.put("comments", Collections.emptyList());

        postsCollection.insert(post);

        return permalink;
    }


    // Append a comment to a blog post
    public void addPostComment(final String name, final String email, final String body,
                               final String permalink) {


        BasicDBObject comment = new BasicDBObject("author", name).append("body", body);
        if (!StringUtils.isEmpty(email)) {
            comment.append("email", email);
        }

        postsCollection.update(new BasicDBObject("permalink", permalink), new BasicDBObject("$set",
                new BasicDBObject("comments", Arrays.asList(comment))), true, false);

    }


}
