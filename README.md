# Project 3 - TwitterClone

TwitterClone is an android app that allows a user to view his Twitter timeline and post a new tweet. The app utilizes [Twitter REST API](https://dev.twitter.com/rest/public).

Time spent: 20 hours spent in total

## User Stories

The following **required** functionality is completed:

* [x]	User can **sign in to Twitter** using OAuth login
* [x]	User can **view tweets from their home timeline**
  * [x] User is displayed the username, name, and body for each tweet
  * [x] User can view more tweets as they scroll with [infinite pagination](http://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView). Number of tweets is unlimited.
  * [x] User is displayed the [relative timestamp](https://gist.github.com/nesquena/f786232f5ef72f6e10a7) for each tweet "8m", "7h"
    However there are [Twitter Api Rate Limits](https://dev.twitter.com/rest/public/rate-limiting) in place.
* [x] User can **compose and post a new tweet**
  * [x] User can click a “Compose” icon in the Action Bar on the top right
  * [x] User can then enter a new tweet and post this to twitter
  * [x] User is taken back to home timeline with **new tweet visible** in timeline

The following **optional** features are implemented:

* [x] User can **see a counter with total number of characters left for tweet** on compose tweet page
* [x] User can **click a link within a tweet body** on tweet details view. The click will launch the web browser with relevant page opened.
* [x] User can **open the twitter app offline and see last loaded tweets**. Persisted in SQLite tweets are refreshed on every application launch. While "live data" is displayed when app can get it from Twitter API, it is also saved for use in offline mode.
* [x] User can tap a tweet to **open a detailed tweet view**
* [x] Improve the user interface and theme the app to feel "twitter branded"

The following **bonus** features are implemented:

* [x] Compose tweet functionality is build using modal overlay
* [x] Use Parcelable instead of Serializable using the popular [Parceler library](http://guides.codepath.com/android/Using-Parceler).
* [x] [Leverage RecyclerView](http://guides.codepath.com/android/Using-the-RecyclerView) as a replacement for the ListView and ArrayAdapter for all lists of tweets.
* [x] Move the "Compose" action to a [FloatingActionButton](https://github.com/codepath/android_guides/wiki/Floating-Action-Buttons) instead of on the AppBar.
* [ ] On the Twitter timeline, leverage the [CoordinatorLayout](http://guides.codepath.com/android/Handling-Scrolls-with-CoordinatorLayout#responding-to-scroll-events) to apply scrolling behavior that [hides / shows the toolbar](http://guides.codepath.com/android/Using-the-App-ToolBar#reacting-to-scroll).

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://github.com/palexis3/TwitterClone/blob/master/TwitterApiSecond.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

Describe any challenges encountered while building the app.

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Picasso](http://square.github.io/picasso/) - Image loading and caching library for Android

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.





# RestClientTemplate [![Build Status](https://travis-ci.org/codepath/android-rest-client-template.svg?branch=master)](https://travis-ci.org/codepath/android-rest-client-template)

## Overview

RestClientTemplate is a skeleton Android project that makes writing Android apps sourced from OAuth JSON REST APIs as easy as possible. This skeleton project
combines the best libraries and structure to enable quick development of rich API clients. The following things are supported out of the box:

 * Authenticating with any OAuth 1.0a or OAuth 2 API
 * Sending requests for and parsing JSON API data using a defined client
 * Persisting data to a local SQLite store through an ORM layer
 * Displaying and caching remote image data into views

The following libraries are used to make this possible:

 * [scribe-java](https://github.com/fernandezpablo85/scribe-java) - Simple OAuth library for handling the authentication flow.
 * [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
 * [codepath-oauth](https://github.com/thecodepath/android-oauth-handler) - Custom-built library for managing OAuth authentication and signing of requests
 * [Picasso](https://github.com/square/picasso) - Used for async image loading and caching them in memory and on disk.
 * [DBFlow](https://github.com/Raizlabs/DBFlow) - Simple ORM for persisting a local SQLite database on the Android device

## Usage

### 1. Configure the REST client

Open `src/com.codepath.apps.restclienttemplate/RestClient.java`. Configure the `REST_API_CLASS`, `REST_URL`, `REST_CONSUMER_KEY`, `REST_CONSUMER_SECRET` based on the values needed to connect to your particular API. The `REST_URL` should be the base URL used for connecting to the API (i.e `https://api.twitter.com`). The `REST_API_CLASS` should be the class defining the [service](https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api) you wish to connect to. Check out the [full list of services](https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api) you can select (i.e `FlickrApi.class`).

For example if I wanted to connect to Twitter:

```java
// RestClient.java
public class RestClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
    public static final String REST_URL = "https://api.twitter.com/1.1";
    public static final String REST_CONSUMER_KEY = "57fdgdfh345195e071f9a761d763ca0";
    public static final String REST_CONSUMER_SECRET = "d657sdsg34435435";
    // ...constructor and endpoints
}
```

Next, change the REST_CALLBACK_URL to a unique name that is special for this application.
This is used for the OAuth authentication flow:

```java
// RestClient.java
public static final String REST_CALLBACK_URL = "oauth://codepathtweets";
```

Also, be sure to **change this value** in the `AndroidManifest.xml` to match the same host:

```java
// AndroidManifest.xml
// manifest => application => activity
<intent-filter>
    <action android:name="android.intent.action.VIEW" />

    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />

    <data
        android:host="codepathtweets"
        android:scheme="oauth" />
</intent-filter>
```

Next, you want to define the endpoints which you want to retrieve data from or send data to within your client:

```java
// RestClient.java
public void getHomeTimeline(int page, AsyncHttpResponseHandler handler) {
  String apiUrl = getApiUrl("statuses/home_timeline.json");
  RequestParams params = new RequestParams();
  params.put("page", String.valueOf(page));
  getClient().get(apiUrl, params, handler);
}
```

Note we are using `getApiUrl` to get the full URL from the relative fragment and `RequestParams` to control the request parameters.
You can easily send post requests (or put or delete) using a similar approach:

```java
// RestClient.java
public void postTweet(String body, AsyncHttpResponseHandler handler) {
    String apiUrl = getApiUrl("statuses/update.json");
    RequestParams params = new RequestParams();
    params.put("status", body);
    getClient().post(apiUrl, params, handler);
}
```

These endpoint methods will automatically execute asynchronous requests signed with the authenticated access token. To use JSON endpoints, simply invoke the method
with a `JsonHttpResponseHandler` handler:

```java
// SomeActivity.java
RestClient client = RestApplication.getRestClient();
client.getHomeTimeline(1, new JsonHttpResponseHandler() {
    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
    // Response is automatically parsed into a JSONArray
    // json.getJSONObject(0).getLong("id");
  }
});
```

Based on the JSON response (array or object), you need to declare the expected type inside the OnSuccess signature i.e
`public void onSuccess(JSONObject json)`. If the endpoint does not return JSON, then you can use the `AsyncHttpResponseHandler`:

```java
RestClient client = RestApplication.getRestClient();
client.getSomething(new AsyncHttpResponseHandler() {
    @Override
    public void onSuccess(int statusCode, Header[] headers, String response) {
        System.out.println(response);
    }
});
```
Check out [Android Async HTTP Docs](http://loopj.com/android-async-http/) for more request creation details.

### 2. Define the Models

In the `src/com.codepath.apps.restclienttemplate.models`, create the models that represent the key data to be parsed and persisted within your application.
For example, if you were connecting to Twitter, you would want a Tweet model as follows:

```java
// models/Tweet.java
package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;


@Table(database = MyDatabase.class)
public class Tweet extends BaseModel {
  // Define database columns and associated fields
  @PrimaryKey @Column 
  Long id;
  @Column
  String userId;
  @Column
  String userHandle;
  @Column
  String timestamp;
  @Column
  String body;
}
```

Notice here we specify the SQLite table for a resource, the columns for that table, and a constructor for
turning the JSON object fetched from the API into this object. For more information on creating a model,
check out the [DBFlow Wiki](https://github.com/Raizlabs/DBFlow/blob/master/usage2/Intro.md).

In addition, we can also add functions into the model to support parsing JSON attributes in order to instantiate the model based on API data. This might look like:

```java
// models/Tweet.java
@Table(database = MyDatabase.class)
public class Tweet extends BaseModel {
  // ...existing code from above...

  // Add a constructor that creates an object from the JSON response
  public Tweet(JSONObject object){
    super();

    try {
      this.userId = object.getString("user_id");
      this.userHandle = object.getString("user_username");
      this.timestamp = object.getString("timestamp");
      this.body = object.getString("body");
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
    ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

    for (int i=0; i < jsonArray.length(); i++) {
        JSONObject tweetJson = null;
        try {
            tweetJson = jsonArray.getJSONObject(i);
        } catch (Exception e) {
            e.printStackTrace();
            continue;
        }

        Tweet tweet = new Tweet(tweetJson);
        tweet.save();
        tweets.add(tweet);
    }

    return tweets;
  }
}
```

Now you have a model that supports proper creation based on JSON. Create models for all the resources
necessary for your mobile client.

### 3. Setup Your Authenticated Activities

Open `src/com.codepath.apps.restclienttemplate/LoginActivity.java` and configure the `onLoginSuccess` method
which fires once your app has access to the authenticated API. Launch an activity and begin using your REST client:

```java
// LoginActivity.java
@Override
public void onLoginSuccess() {
  Intent i = new Intent(this, TimelineActivity.class);
  startActivity(i);
}
```

In your new authenticated activity, you can access your client anywhere with:

```java
RestClient client = RestApplication.getRestClient();
client.getHomeTimeline(1, new JsonHttpResponseHandler() {
  public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
    Log.d("DEBUG", "timeline: " + jsonArray.toString());
    // Load json array into model classes
  }
});
```

You can then load the data into your models from a `JSONArray` using:

```java
ArrayList<Tweet> tweets = Tweet.fromJSON(jsonArray);
```

or load the data from a single `JSONObject` with:

```java
Tweet t = new Tweet(json);
// t.body = "foo"
t.save();
```

That's all you need to get started. From here, hook up your activities and their behavior, adjust your models and add more REST endpoints.

### Extras

#### Loading Images with Picasso

If you want to load a remote image url into a particular ImageView, you can use Picasso to do that with:

```java
Picasso.with(this).load(imageUrl).
  noFade().fit().into(imageView);
```

This will load an image into the specified ImageView and resize the image to fit.

#### Logging Out

You can log out by clearing the access token at any time through the client object:

```java
RestClient client = RestApplication.getRestClient();
client.clearAccessToken();
```

### Troubleshooting

* If you receive the following error `org.scribe.exceptions.OAuthException: Cannot send unauthenticated requests for TwitterApi client. Please attach an access token!` then check the following:
 * Is your intent-filter with `<data>` attached to the `LoginActivity`? If not, make sure that the `LoginActivity` receives the request after OAuth authorization.
 * Is the `onLoginSuccess` method being executed in the `LoginActivity`. On launch of your app, be sure to start the app on the LoginActivity so authentication routines execute on launch and take you to the authenticated activity. 
