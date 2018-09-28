package com.teiath.harrys.vquiz.Activities;

import com.teiath.harrys.vquiz.GlobalStatistics;
import com.teiath.harrys.vquiz.R;
import com.teiath.harrys.vquiz.GraphPieStatistics;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.LinearLayout;
import android.widget.PopupWindow;


public class StatisticsActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    //Member variables
    private ViewPager mViewPager;
    private PopupWindow mPwLevel, pwAboutUs;
    // Random words mode by default
    public String mCategory = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bar_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handling action bar items by id
        int id = item.getItemId();
        if (id == R.id.quit) {
            final SharedPreferences sharedPreferences = getSharedPreferences(MenuActivity.GLOBAL_SHARED_TAG, Context.MODE_PRIVATE);
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear().commit();
            finishAffinity();
            System.exit(1);
        }

        if (id == R.id.action_settings) {
            Intent settingsActivity = new Intent(StatisticsActivity.this, SettingsActivity.class);
            settingsActivity.putExtra(MenuActivity.CATEGORY_TAG, mCategory);
            startActivity(settingsActivity);
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }

        if (id == R.id.about_us) {
            LayoutInflater flater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View aboutUsView = flater.inflate(R.layout.activity_about_us, null);
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            final boolean focusable = true; // lets taps outside the popup also dismiss it
            pwAboutUs = new PopupWindow(aboutUsView, width, height, focusable);
            pwAboutUs.showAtLocation(aboutUsView, Gravity.CENTER, 0, 0);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //position starts from zero, so we plus by 1
            switch (position + 1) {
                case 1:
                    GlobalStatistics global = new GlobalStatistics();
                    return global;
                case 2:
                    GraphPieStatistics graph_pie = new GraphPieStatistics();
                    return graph_pie;
                default:
                    GlobalStatistics defaultView = new GlobalStatistics();
                    return defaultView;
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }
}
