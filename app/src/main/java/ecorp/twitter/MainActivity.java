package ecorp.twitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.twitter.sdk.android.tweetui.TweetUi;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private TwitterLoginButton loginButton;
    static String userName;
    EditText enterUserName;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "VLaMbUjondPMFyibiXqIjTKY8";
    private static final String TWITTER_SECRET = "XNNdlf1bduK5gf03wybQLoFxv9EtNfDIjFDLSxlDixzTVoCZOx";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig),new TweetUi(),new TweetComposer());
        setContentView(R.layout.activity_main);

        enterUserName = (EditText) findViewById(R.id.enterUserName);
        loginButton = (TwitterLoginButton) findViewById(R.id.loginButton);

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                userName = enterUserName.getText().toString();
                TwitterAuthToken token = session.getAuthToken();
                String userToken  = token.token;
                String tokenSecret = token.secret;
                Log.i("usertoken",userToken);
                Log.i("tokensecret",tokenSecret);

                    String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(MainActivity.this, Timeline.class);
                    startActivity(intent);


            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            // Make sure that the loginButton hears the result from any
            // Activity that is triggered.
            loginButton.onActivityResult(requestCode, resultCode, data);
        }

    }