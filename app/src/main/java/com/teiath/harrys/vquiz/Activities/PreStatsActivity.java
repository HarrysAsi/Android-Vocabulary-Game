package com.teiath.harrys.vquiz.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.teiath.harrys.vquiz.dbConnector.CustomRequest;
import com.teiath.harrys.vquiz.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PreStatsActivity extends AppCompatActivity {
    //Dialog Tags
    private static final String DIALOG_OK_TAG = "Ok";
    private static final String DIALOG_CANCEL_TAG = "Cancel";
    //Member variables
    private EditText mUsernameEditText;
    private TextView mScoreTextView, mModeTextView;
    private Button mSubmitBtn;
    private RadioButton mMaleRadioButton, mFemaleRadioButton;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_stats);
        //Connect to xml
        findViews();

        //Retreiving the data
        final String mode = this.getIntent().getStringExtra(GameActivity.MODE_TAG);
        final String totalAnswers = this.getIntent().getStringExtra(GameActivity.TOTAL_ANSWERS_TAG);

        // Dialog, if user doesn't want to add the score he is transferred back to menu activity
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage("Do you want to save your stats?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, DIALOG_CANCEL_TAG, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent menuActivity = new Intent(PreStatsActivity.this, MenuActivity.class);
                startActivity(menuActivity);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, DIALOG_OK_TAG, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        alertDialog.show();

        //Initialize the mode - Menu activity - sends "0-1-2"
        final String modeTmp;
        if (mode.equals("0")) {
            modeTmp = "Beginner";
        } else if (mode.equals("1")) {
            modeTmp = "Intermediate";
        } else {
            modeTmp = "Expert";
        }
        //Set the values in the user interface fields
        mScoreTextView.setText("Score: " + totalAnswers);
        mModeTextView.setText("Mode: " + modeTmp);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://79.167.39.214:80/AndroidVquiz/Insert.php";

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hiding the keyboard on button click
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {}

                //Getting the values from the input fields

                String usernameTrimmed = mUsernameEditText.getText().toString().trim();
                if (usernameTrimmed.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please insert a valid username", Toast.LENGTH_SHORT).show();
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    boolean maleChecked = mMaleRadioButton.isChecked();
                    boolean femaleChecked = mFemaleRadioButton.isChecked();
                    if (usernameTrimmed.isEmpty()) {
                        AlertDialog alert = new AlertDialog.Builder(PreStatsActivity.this).create();
                        alert.setMessage("");
                    }
                    String gender;
                    if (maleChecked) {
                        gender = "1";
                    } else {
                        gender = "0";
                    }
                    //Hashmap for the values
                    Map<String, String> params = new HashMap<>();
                    params.put("gender", gender);
                    params.put("username", usernameTrimmed);
                    params.put("mode", modeTmp);
                    params.put("score", totalAnswers);

                    CustomRequest myRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(), "Score inserted successfully!", Toast.LENGTH_SHORT).show();
                            Intent menuActivity = new Intent(PreStatsActivity.this, MenuActivity.class);
                            startActivity(menuActivity);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (!isConnectedToNet()) {
                                Toast.makeText(getApplicationContext(), "You are not connected to internet, please try again.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Server is not responding, try again later", Toast.LENGTH_SHORT).show();
                            }
                            Intent menuAcitivy = new Intent(PreStatsActivity.this, MenuActivity.class);
                            startActivity(menuAcitivy);
                            mProgressBar.setVisibility(View.GONE);
                        }
                    });
                    queue.add(myRequest);
                }
            }
        });
    }

    //Boolean function which returns true if network is enabled
    private boolean isConnectedToNet() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    //Connect to xml
    private void findViews() {
        mUsernameEditText = (EditText) findViewById(R.id.username_edit_text);
        mModeTextView = (TextView) findViewById(R.id.mode_edit_text);
        mScoreTextView = (TextView) findViewById(R.id.score_edit_text);
        mSubmitBtn = (Button) findViewById(R.id.submit_btn);
        mMaleRadioButton = (RadioButton) findViewById(R.id.male_radio_btn);
        mFemaleRadioButton = (RadioButton) findViewById(R.id.female_radio_btn);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }
}
