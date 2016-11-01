package com.codepath.apps.simpletweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.simpletweets.DividerItemDecoration;
import com.codepath.apps.simpletweets.EndlessRecyclerViewScrollListener;
import com.codepath.apps.simpletweets.ItemClickSupport;
import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.adapters.TweetsAdapter;
import com.codepath.apps.simpletweets.clients.TwitterClient;
import com.codepath.apps.simpletweets.fragments.TweetComposeDialogFragment;
import com.codepath.apps.simpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

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

    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        Toolbar toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);


        // Display twitter icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_twitter);
        getSupportActionBar().setDisplayUseLogoEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.rvTweets);
        //initializing arraylist to hold tweets
        tweetArrayList = new ArrayList<>();
        //initializing recycleview adapter
        adapter = new TweetsAdapter(this, tweetArrayList);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        //set the layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //set floating action button
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fbButton);

        //add item view decorations
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecoration);

        client = TwitterApplication.getRestClient();
        //at the beginning receive first 25 tweets
        populateTimeline(25);
        floatingActionButtonListener();
        itemClickListener();


        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };

        // Adds the scroll listener to RecyclerView
        recyclerView.addOnScrollListener(scrollListener);
    }

    public void loadNextDataFromApi(int offset) {
          int temp = client.getCount() + offset;
          populateTimeline(temp); //run api request when user has scrolled down
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

    //an item in view has been clicked on
    private void itemClickListener() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        //start tweet detail activity
                        Tweet tweet = tweetArrayList.get(position);
                        Intent i = new Intent(TimelineActivity.this, TweetDetailActivity.class);
                        i.putExtra("tweet", Parcels.wrap(tweet));
                        startActivity(i);
                    }
                }
        );

    }

    //send an api request to get the timeline json
    //fill the recyclerview by creating the tweet objects from the json
    private void populateTimeline(int val) {

        client.getHomeTimeLine(val, new JsonHttpResponseHandler() {
            //success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                // Log.d("DEBUG", json.toString());
                //JSON comes in here
                //deserialize json
                //create models and add them to the model
                //load the model data into listview

                // record this value before making any changes to the existing list
                int curSize = adapter.getItemCount();
                //add tweets to arrayList
                tweetArrayList.addAll(Tweet.fromJSONArray(json));
                //curSize should represent the first element that got added
                adapter.notifyItemRangeChanged(curSize, tweetArrayList.size());
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
                //Log.d("DEBUG", response.toString());
                Toast.makeText(getApplicationContext(), "I've successfully tweeted", Toast.LENGTH_LONG).show();
                Tweet tweet = Tweet.fromJSON(response);
                tweetArrayList.add(0, tweet);
                adapter.notifyItemRangeInserted(0, 1);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
               //Log.d("DEBUG", errorResponse.toString());
               Toast.makeText(getApplicationContext(), "Failed Tweet", Toast.LENGTH_LONG).show();
            }
        });

    }



}
