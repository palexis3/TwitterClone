package com.codepath.apps.simpletweets.clients;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1/"; //base API URL
	public static final String REST_CONSUMER_KEY = "NU9mPGg9pElEW9KidVU5tssFe";
	public static final String REST_CONSUMER_SECRET = "JDkcxpDgYEJfFwnyFFrcug1tcZXb2SNPd7LepFk9YyzSodlKWF";
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets";

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// DEFINE METHODS for different API endpoints here

	//HomeTimeLine - Gets us the home timeline
	public void getHomeTimeLine(AsyncHttpResponseHandler handler) {
		String APIUrl = getApiUrl("statuses/home_timeline.json");
		//specify the params
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("since_id", 1);
		//execute the request
		getClient().get(APIUrl, params, handler);
	}

	//composing a tweet
	public void composeTweet(AsyncHttpResponseHandler handler) {
		String APIUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		//params.put("status", status);
		getClient().post(APIUrl, params, handler);

	}
}
