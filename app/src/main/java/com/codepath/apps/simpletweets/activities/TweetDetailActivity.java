package com.codepath.apps.simpletweets.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.models.Tweet;
import com.codepath.apps.simpletweets.models.User;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class TweetDetailActivity extends AppCompatActivity {

    ImageView profileImage;
    TextView name;
    TextView screenName;
    TextView body;
    TextView createdAt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_detail);

        Toolbar toolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);


        // Display twitter icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_twitter);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //get data from intent using parcerler
        Tweet tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        User user = tweet.getUser();

        //initialize our subviews
        profileImage = (ImageView) findViewById(R.id.ivDetailProfile);
        name = (TextView) findViewById(R.id.tvDetailName);
        screenName = (TextView) findViewById(R.id.tvDetailScreenName);
        body = (TextView) findViewById(R.id.tvDetailBody);
        createdAt = (TextView) findViewById(R.id.tvDetailCreatedAt);

        name.setText(user.getName());
        screenName.setText("@" + user.getScreenName());
        body.setText(tweet.getBody());
        String [] arr = tweet.getCreatedAt().split(" ");
        createdAt.setText(arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[5]);

        Picasso.with(this).load(user.getProfileImageUrl()).into(profileImage);

    }
}
