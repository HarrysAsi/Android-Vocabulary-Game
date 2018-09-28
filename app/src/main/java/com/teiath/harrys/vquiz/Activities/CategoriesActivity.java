package com.teiath.harrys.vquiz.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.teiath.harrys.vquiz.R;

public class CategoriesActivity extends AppCompatActivity {
    private Button mRandomWordsBtn, mFootballWordsBtn;
    public String category = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        //Connect to xml
        findViews();
        String categorySelected = "0";
        final Intent menuActivity = new Intent(CategoriesActivity.this, MenuActivity.class);

        if (categorySelected.equals("0")) {
            mRandomWordsBtn.setFocusableInTouchMode(true);
            mRandomWordsBtn.setFocusable(true);
            mFootballWordsBtn.setFocusableInTouchMode(false);
            mFootballWordsBtn.setFocusable(false);

        } else {
            mRandomWordsBtn.setFocusableInTouchMode(false);
            mRandomWordsBtn.setFocusable(false);
            mFootballWordsBtn.setFocusableInTouchMode(true);
            mFootballWordsBtn.setFocusable(true);
        }

        mRandomWordsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "0";
                mRandomWordsBtn.setFocusableInTouchMode(true);
                mRandomWordsBtn.setFocusable(true);
                mFootballWordsBtn.setFocusableInTouchMode(false);
                mFootballWordsBtn.setFocusable(false);
            }
        });

        mFootballWordsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "1";
                mRandomWordsBtn.setFocusableInTouchMode(false);
                mRandomWordsBtn.setFocusable(false);
                mFootballWordsBtn.setFocusableInTouchMode(true);
                mFootballWordsBtn.setFocusable(true);
            }
        });
    }

    private void findViews() {
        mRandomWordsBtn = (Button) findViewById(R.id.random_words_txt);
        mFootballWordsBtn = (Button) findViewById(R.id.football_words_txt);

    }
}
