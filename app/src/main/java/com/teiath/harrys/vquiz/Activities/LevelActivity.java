package com.teiath.harrys.vquiz.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teiath.harrys.vquiz.R;

public class LevelActivity extends AppCompatActivity {
    //Member variables
    private Button mBeginnerBtn,mInterBtn,mExpertBtn;

    public String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        //Connect to xml
        findViews();

        final Intent menuActivity = new Intent(LevelActivity.this, MenuActivity.class);

        if(getIntent().hasExtra(MenuActivity.MODE_TAG))
            mode = getIntent().getStringExtra(MenuActivity.MODE_TAG);
        else
            mode ="0";

        //Initialize buttons depends on the selected mode
        if(mode.equals("0")){
            mBeginnerBtn.setFocusableInTouchMode(true);
            mBeginnerBtn.setFocusable(true);
        } else if(mode.equals("1")){
            mInterBtn.setFocusableInTouchMode(true);
            mInterBtn.setFocusable(true);
        } else {
            mExpertBtn.setFocusableInTouchMode(true);
            mExpertBtn.setFocusable(true);
        }

        mBeginnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "0";
                mBeginnerBtn.setFocusableInTouchMode(true);
                mBeginnerBtn.setFocusable(true);
                mInterBtn.setFocusableInTouchMode(false);
                mInterBtn.setFocusable(false);
                mExpertBtn.setFocusableInTouchMode(false);
                mExpertBtn.setFocusable(false);

                menuActivity.putExtra(MenuActivity.MODE_TAG, mode);
                startActivity(menuActivity);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        mInterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "1";
                mInterBtn.setFocusableInTouchMode(true);
                mInterBtn.setFocusable(true);
                mBeginnerBtn.setFocusableInTouchMode(false);
                mBeginnerBtn.setFocusable(false);
                mExpertBtn.setFocusableInTouchMode(false);
                mExpertBtn.setFocusable(false);

                menuActivity.putExtra(MenuActivity.MODE_TAG, mode);
                startActivity(menuActivity);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        mExpertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode = "2";
                mBeginnerBtn.setFocusableInTouchMode(false);
                mBeginnerBtn.setFocusable(false);
                mInterBtn.setFocusableInTouchMode(false);
                mInterBtn.setFocusable(false);
                mExpertBtn.setFocusableInTouchMode(true);
                mExpertBtn.setFocusable(true);

                menuActivity.putExtra(MenuActivity.MODE_TAG, mode);
                startActivity(menuActivity);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
    }
    private void findViews(){
        mBeginnerBtn = (Button) findViewById(R.id.beginnerBtn);
        mInterBtn = (Button) findViewById(R.id.interBtn);
        mExpertBtn = (Button) findViewById(R.id.expertBtn);
    }
}