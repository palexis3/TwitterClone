package com.codepath.apps.simpletweets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codepath.apps.simpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    //access the twitter client
    private TwitterClient client;
    private TweetsArrayAdapter tweetsArrayAdapter;
    private ArrayList<Tweet> tweetArrayList;
    private ListView lvTweets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        lvTweets = (ListView) findViewById(R.id.lvTweets);
        tweetArrayList = new ArrayList<>();
        tweetsArrayAdapter = new TweetsArrayAdapter(this, tweetArrayList);
        lvTweets.setAdapter(tweetsArrayAdapter);

        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    //send an api request to get the timeline json
    //fill the listview by creating the tweet objects from the json
    private void populateTimeline() {

        client.getHomeTimeLine(new JsonHttpResponseHandler() {
            //success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
              Log.d("DEBUG", json.toString());
                //JSON comes in here
                //deserialize json
                //create models and add them to the model
                //load the model data into listview
                tweetsArrayAdapter.addAll(Tweet.fromJSONArray(json));
            }

            //failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
              Log.d("DEBUG", errorResponse.toString());
            }
        });

    }

}
