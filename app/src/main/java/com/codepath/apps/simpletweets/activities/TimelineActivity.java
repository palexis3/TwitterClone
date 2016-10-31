package com.codepath.apps.simpletweets.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

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

public class TimelineActivity extends AppCompatActivity {

    //access the twitter client
    private TwitterClient client;
    //item_tweet subviews
    private RecyclerView recyclerView;
    private ArrayList<Tweet> tweetArrayList;
    private TweetsAdapter adapter;
    //floating button
    private FloatingActionButton floatingActionButton;


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

}
