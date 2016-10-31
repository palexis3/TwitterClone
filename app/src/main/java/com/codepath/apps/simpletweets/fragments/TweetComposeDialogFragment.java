package com.codepath.apps.simpletweets.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.simpletweets.R;

public class TweetComposeDialogFragment extends DialogFragment {

    private EditText tweetEditText;
    private Button tweetButton;
    private EditText tweetCharacterCount;

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
        tweetCharacterCount = (EditText) view.findViewById(R.id.etCharacterCount);
        //watch what's being typed in
        watchText();
    }

    //this method watches as user is typing into compose editText
    private void watchText() {
        tweetEditText.addTextChangedListener(new TextWatcher() {

            //update character count as user is typing in their content
            String val = tweetCharacterCount.getText().toString();
            int num = Integer.parseInt(val);
            int temp = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //change counter
                temp = num - count;

                //set button to clickable when editText has content
                if(tweetEditText.length() > 0) {
                    tweetButton.setEnabled(true);
                } else {
                    tweetButton.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                tweetCharacterCount.setText(String.valueOf(temp));
            }
        });
    }
}
