package com.codepath.apps.simpletweets.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.simpletweets.R;

public class TweetComposeDialogFragment extends DialogFragment {

    private EditText tweetEditText;
    private Button tweetButton;

    public TweetComposeDialogFragment() {
        //Empty constructor required for dialog fragment
    }

    public static TweetComposeDialogFragment newInstance(String title) {
        TweetComposeDialogFragment frag = new TweetComposeDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_compose_tweet, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //get fields from view
        tweetEditText = (EditText) view.findViewById(R.id.etCompose);
        tweetButton = (Button) view.findViewById(R.id.btnTweet);
        //fetch arguments from bundle and set title
        if(tweetEditText.length() > 0) {
            tweetButton.setEnabled(true);
        } else {
            tweetButton.setEnabled(false);
        }
    }
}
