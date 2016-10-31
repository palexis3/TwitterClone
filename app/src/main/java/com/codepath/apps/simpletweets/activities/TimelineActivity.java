package com.codepath.apps.simpletweets.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.adapters.TweetsAdapter;
import com.codepath.apps.simpletweets.clients.TwitterClient;
import com.codepath.apps.simpletweets.fragments.TweetComposeDialogFragment;
import com.codepath.apps.simpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity implements TweetComposeDialogFragment.TweetComposeDialogListener{

    //access the twitter client
    private TwitterClient client;
    //item_tweet subviews
    private RecyclerView recyclerView;
    private ArrayList<Tweet> tweetArrayList;
    private TweetsAdapter adapter;
    //floating button
    private FloatingActionButton floatingActionButton;
    private String input; //holds the input that is sent through dialog


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        recyclerView = (RecyclerView) findViewById(R.id.rvTweets);
        //initializing arraylist to hold tweets
        tweetArrayList = new ArrayList<>();
        //initializing recycleview adapter
        adapter = new TweetsAdapter(this, tweetArrayList);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        //set the layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //set floating action button
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fbButton);

        client = TwitterApplication.getRestClient();
        populateTimeline();
        floatingActionButtonListener();
    }

    //call dialog fragment when button clicked
    private void floatingActionButtonListener() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showComposeDialog();
            }
        });
    }

    //calling compose dialog fragment
    private void showComposeDialog() {
        FragmentManager fm = getSupportFragmentManager();
        TweetComposeDialogFragment fragment = TweetComposeDialogFragment.newInstance("Twitter");
        fragment.show(fm, "dialog_compose_tweet");
    }




    //send an api request to get the timeline json
    //fill the recyclerview by creating the tweet objects from the json
    private void populateTimeline() {

        client.getHomeTimeLine(new JsonHttpResponseHandler() {
            //success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                // Log.d("DEBUG", json.toString());
                //JSON comes in here
                //deserialize json
                //create models and add them to the model
                //load the model data into listview
                tweetArrayList.addAll(Tweet.fromJSONArray(json));
                adapter.notifyDataSetChanged();
            }

            //failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });

    }

    // This method is invoked in the activity when the listener is triggered
    // Access the data result passed to the activity here
    public void onComposeFinished(String status) {
        input = status;
        postTweet();//call post tweet method
    }

    //method sends an api post request to timeline
    private void postTweet() {

         client.composeTweet(input, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                Toast.makeText(getApplicationContext(), "I've successfully tweeted", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
               Log.d("DEBUG", errorResponse.toString());
                Toast.makeText(getApplicationContext(), "I've failed tweeted", Toast.LENGTH_LONG).show();
            }
        });

    }



}
