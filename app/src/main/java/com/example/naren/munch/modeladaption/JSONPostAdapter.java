package com.example.naren.munch.modeladaption;

import com.example.naren.munch.model.RedditPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class JSONPostAdapter {

    public Collection<RedditPost> adapt(JSONObject json) throws JSONException {
        Collection<RedditPost> posts = new ArrayList<>();
        JSONArray childrenArray = json.getJSONObject("data").getJSONArray("children");

        for (int i = 0; i < childrenArray.length(); i++) {

            RedditPost post = new RedditPost();

            String title = childrenArray.getJSONObject(i).getJSONObject("data").getString("title");
            String url = childrenArray.getJSONObject(i).getJSONObject("data").getString("url");
            String thumbnail = childrenArray.getJSONObject(i).getJSONObject("data").getString("thumbnail");
            String after = json.getJSONObject("data").getString("after");


            String author = childrenArray.getJSONObject(i).getJSONObject("data").getString("author");
            String subreddit = childrenArray.getJSONObject(i).getJSONObject("data").getString("subreddit");
            String domain = childrenArray.getJSONObject(i).getJSONObject("data").getString("domain");
            String permalink = childrenArray.getJSONObject(i).getJSONObject("data").getString("permalink");
            String selfttext_html = childrenArray.getJSONObject(i).getJSONObject("data").getString("selftext_html");

            int score = childrenArray.getJSONObject(i).getJSONObject("data").getInt("score");
            int comments = childrenArray.getJSONObject(i).getJSONObject("data").getInt("num_comments");
            long time = childrenArray.getJSONObject(i).getJSONObject("data").getInt("created_utc");

            //thumbnail for youtube videos
            if (domain.equals("youtube.com") || domain.equals("youtu.be")
                    || domain.equals("m.youtube.com")) {

                String youtube_thumbnail = childrenArray.getJSONObject(i).getJSONObject("data").getJSONObject("media").getJSONObject("oembed").getString("thumbnail_url");
                post.setYoutubeThumbnail(youtube_thumbnail);


            }

            //set the data for each post
            post.setThumbnail(thumbnail);
            post.setUrl(url);
            post.setAfter(after);
            post.setTitle(title);
            post.setPermalink(permalink);
            post.setAuthor(author);
            post.setSubreddit(subreddit);
            post.setDomain(domain);
            post.setTime(time);
            post.setScore(score);
            post.setComments(comments);
            post.setSelftext_html(selfttext_html);

            posts.add(post);
        }
        return posts;
    }
}
