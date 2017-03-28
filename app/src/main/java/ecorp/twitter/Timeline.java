package ecorp.twitter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.twitter.sdk.android.tweetcomposer.TweetComposer;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class Timeline extends AppCompatActivity implements TextView.OnEditorActionListener, View.OnLayoutChangeListener {

    EditText search;
    ImageView displayPic;
    String input;
    FloatingActionButton fab;
    ListView lv;
    ProgressDialog progress;

    private static final String SEARCH_QUERY = "studioshawkeye8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);


        lv = (ListView) findViewById(R.id.timeline);
        lv.addOnLayoutChangeListener(this);
        // TODO: 21/03/17 add loading pic
        //lv.setEmptyView(findViewById(R.id.loading)); d'ont forget to add loading pic
       search = (EditText) findViewById(R.id.search);
        displayPic = (ImageView) findViewById(R.id.dp);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        /// Using the keyboard to search for selected keywords
        
        search.setOnEditorActionListener(this);


        /// Creating a new tweet Builder on pressing the floating action button

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                TweetComposer.Builder builder = new TweetComposer.Builder(Timeline.this)
                        .text("just setting up my Fabric.");

                builder.show();
            }
        });
        
        
        /// To fetch enter timeline

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(MainActivity.userName)
                .build();

        /// setting the timeline adapter

        final TweetTimelineListAdapter timelineAdapter = new TweetTimelineListAdapter(this, userTimeline);
        lv.setAdapter(timelineAdapter);

    }

    /// Code to perform search when Enter is clicked on user keyboard

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            input = search.getText().toString();
            if(input.isEmpty()) {
                Toast.makeText(Timeline.this, "Enter Something", Toast.LENGTH_SHORT).show();
            }
            else
            {
                progress = new ProgressDialog(this);
                progress.setTitle("Loading");
                progress.setMessage("Loading...");
                progress.setCancelable(true); // disable dismiss by tapping outside of the dialog
                progress.show();
// To dismiss the dialog

                SearchTimeline searchTimeline = new SearchTimeline.Builder()
                        .query(input)
                        .build();
                performSearch();
                TweetTimelineListAdapter timelineAdapter = new TweetTimelineListAdapter(this, searchTimeline);
                lv.setAdapter(timelineAdapter);




            }
            return true;
        }
        return false;
    }

    /// Code to hide keyboard as soon as enter is pressed on user keyboard

    private void performSearch() {
        search.clearFocus();
        InputMethodManager in = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(search.getWindowToken(), 0);
    }

    /// Code that detects when listView has been populated and then dismisses the loading dialog

    @Override
    public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {

        if(lv.getChildCount()>=1)
        {
            if(progress!=null) {
                progress.dismiss();
            }
        }
    }
}
