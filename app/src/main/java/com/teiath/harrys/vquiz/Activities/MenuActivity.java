package com.teiath.harrys.vquiz.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.teiath.harrys.vquiz.R;


public class MenuActivity extends AppCompatActivity {
    // Constants
    public static final String MODE_TAG = "mode";
    public static final String CATEGORY_TAG = "category";
    public static final String VIBRATION_TAG = "vibration";
    public static final String SOUND_TAG = "sound";
    public static final String GLOBAL_SHARED_TAG = "shared";

    // Member variables
    private Button mPlayBtn, mLevelBtn, mCategoryBtn, mStatisticsBtn;
    private PopupWindow mPwLevel, mPwAboutUs;

    public String mMode; // Beginner mode by default
    public String mCategory = "0"; // Random words mode by default


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Connect to xml file
        findViews();

        //Activities
        final Intent categoriesActivity = new Intent(MenuActivity.this, CategoriesActivity.class);
        final Intent levelActivity = new Intent(MenuActivity.this, LevelActivity.class);

        //Listener for the play button, enables the game activity
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().hasExtra(MODE_TAG))
                    mMode = getIntent().getStringExtra(MODE_TAG);
                else
                    mMode = "0";
                Intent gameActivity = new Intent(getApplicationContext(), GameActivity.class);
                gameActivity.putExtra(MODE_TAG, mMode);
                startActivity(gameActivity);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        // Listener for the level button, shows up a pop-up window
        mLevelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().hasExtra(MODE_TAG))
                    mMode = getIntent().getStringExtra(MODE_TAG);
                else
                    mMode = "0";
                Log.d("MODE", "onClick: ~~~~~~~~~~~~~~~~~~~~~~~~" + mMode);
                levelActivity.putExtra(MODE_TAG, mMode);
                startActivity(levelActivity);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        // Listener for the category button- sets a new category tag that should be
        // checked in-game mode and retrieve the data based on this attribute
        mCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoriesActivity.hasExtra(CATEGORY_TAG)) {
                    mCategory = categoriesActivity.getStringExtra(CATEGORY_TAG);

                }
                categoriesActivity.putExtra(CATEGORY_TAG, mCategory);
                startActivity(categoriesActivity);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        // Listener for the Statistics button
        mStatisticsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent statisticsActivity = new Intent(MenuActivity.this, StatisticsActivity.class);
                startActivity(statisticsActivity);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bar_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.quit) {
            final SharedPreferences sharedPreferences = getSharedPreferences(MenuActivity.GLOBAL_SHARED_TAG, Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear().commit();
            finishAffinity();
            System.exit(1);
        }
        if (id == R.id.action_settings) {
            Intent settingsActivity = new Intent(MenuActivity.this, SettingsActivity.class);
            settingsActivity.putExtra(CATEGORY_TAG, mCategory);
            startActivity(settingsActivity);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }

        if (id == R.id.about_us) {
            LayoutInflater flater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View aboutUsView = flater.inflate(R.layout.activity_about_us, null);
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            final boolean focusable = true; // lets taps outside the popup also dismiss it
            mPwAboutUs = new PopupWindow(aboutUsView, width, height, focusable);
            mPwAboutUs.showAtLocation(aboutUsView, Gravity.CENTER, 0, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    //Connecting with xml file
    private void findViews() {
        mPlayBtn = (Button) findViewById(R.id.playBtn);
        mCategoryBtn = (Button) findViewById(R.id.categoryBtn);
        mLevelBtn = (Button) findViewById(R.id.levelBtn);
        mStatisticsBtn = (Button) findViewById(R.id.statisticsBtn);
    }
}
