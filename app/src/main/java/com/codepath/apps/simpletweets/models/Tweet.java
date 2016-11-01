package com.codepath.apps.simpletweets.models;

//parse the json and store the data
//encapsulate state or display logic

import com.codepath.apps.simpletweets.MyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
@Table(database = MyDatabase.class)
public class Tweet extends BaseModel {


    //list out the attributes
    @Column
    private String body;

    @Column
    @PrimaryKey
    private long uid; //unique id for the tweet

    @Column
    private String createdAt;

    @Column
    @ForeignKey(saveForeignKeyModel = false)
    private User user; //store an embedded user object

    // empty constructor needed by the Parceler library
    public Tweet() {
    }


    //Deserialize the JSON and build
    //Tweet.fromJson("{...}") => <Tweet>
    public static Tweet fromJSON(JSONObject jsonObject)  {

        //extract the values from json, store them
        //return the tweet object
        Tweet tweet = new Tweet();

        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    //Tweet.fromJSONArray ([{}, {}, ..]) => List<Tweet>
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        //iterate the json array and create tweets

        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = fromJSON(tweetJson);
                if(tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

        }

        return tweets;
    }


    //getters
    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    //setters
    public void setBody(String body) {
        this.body = body;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
