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

    // Defines the listener interface with a method passing back data result.
    public interface TweetComposeDialogListener {
        void onComposeFinished(String input);
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
            String before = "";
            String after = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                before = tweetEditText.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                after = tweetEditText.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

                int offset  = after.length() - before.length();
                int num = 5;
                tweetCharacterCount.setText(String.valueOf(140 - num));

                /*
                if(after.length() > before.length()) {
                    offset = after.length() - before.length();
                    tweetCharacterCount.setText(String.valueOf(140 - offset));
                } else {
                    offset = before.length() - after.length();
                    tweetCharacterCount.setText(String.valueOf(before.length() + offset));
                }
                */

                //set button to clickable when editText has content
                if (tweetEditText.length() > 0 && tweetEditText.length() <= 140) {
                    tweetButton.setEnabled(true);
                    //watch the button listener
                    tweetButtonListener();
                } else {
                    tweetButton.setEnabled(false);
                }
            }
        });
    }


    //user clicks 'tweet' button
    private void tweetButtonListener() {
        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return input text back to activity through the implemented listener
                TweetComposeDialogListener listener = (TweetComposeDialogListener) getActivity();
                listener.onComposeFinished(tweetEditText.getText().toString());
                // Close the dialog and return back to the parent activity
                dismiss();

            }
        });
    }
}
