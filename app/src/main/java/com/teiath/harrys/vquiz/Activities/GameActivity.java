package com.teiath.harrys.vquiz.Activities;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.teiath.harrys.vquiz.DataGame;
import com.teiath.harrys.vquiz.GameObject;
import com.teiath.harrys.vquiz.R;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends Activity {
    public static final String MODE_TAG = "mode";
    public static final String TOTAL_ANSWERS_TAG = "totalAnswers";
    //Member variables
    private TextView mAttemptsTextView, mTimeTextView, mQuestionTextView, mLetter1TextView, mLetter2TextView, mLetter3TextView, mLetter4TextView, mLetter5TextView, mLetter6TextView, mDragedResultTextView;
    private Button mSubmitBtn, mResetBtn, mSkipBtn;
    private ImageView mClockImg;
    private String mMode; // Retrieve the data for the selected mode
    private int mTotalCorrectAnswers = 0;
    //Static variables
    private static int sAttempts;
    private static int sCurrQuestions;
    private static int sClockCnt;
    private static int sSize;
    private static int sIndex;
    private static String word = "-1";
    //Variables for the sound and vibration
    private static boolean sSoundEnabled;
    private static boolean sVibrationEnabled;
    private static String sTmpStrResult; //storing the results so we can compare if its true with the input
    private static GameObject sPlayer; //Player object
    private static ArrayList<String> sData; //Arraylist to keep the current data depends on the mode selected
    //Handler for the runnable method (Time Counter)
    private Handler handler = new Handler();
    // variables for generating random numbers
    private static Random ran = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Shared preferences for the game mode
        final SharedPreferences sharedPreferences = getSharedPreferences(MenuActivity.GLOBAL_SHARED_TAG, Context.MODE_PRIVATE);
        if (getIntent().hasExtra(MenuActivity.MODE_TAG))
            mMode = getIntent().getStringExtra(MenuActivity.MODE_TAG);
        else
            mMode = "0";

        //Connect to xml file
        findViews();

        //Listeners for drag and drop
        mLetter1TextView.setOnTouchListener(new ChoiceTouchListener());
        mLetter2TextView.setOnTouchListener(new ChoiceTouchListener());
        mLetter3TextView.setOnTouchListener(new ChoiceTouchListener());
        mLetter4TextView.setOnTouchListener(new ChoiceTouchListener());
        mLetter5TextView.setOnTouchListener(new ChoiceTouchListener());
        mLetter6TextView.setOnTouchListener(new ChoiceTouchListener());
        mDragedResultTextView.setOnDragListener(new ChoiceDragListener());
        //Listener for the Submit button
        mSubmitBtn.setOnClickListener(new OnClickSubmit());

        //Retreive data - if sound-vibration are enabled
        sSoundEnabled = sharedPreferences.getBoolean(MenuActivity.SOUND_TAG, true);
        sVibrationEnabled = sharedPreferences.getBoolean(MenuActivity.VIBRATION_TAG, true);
        // Initialize the player mode depends on the selected mode.
        initializePlayerMode();
        // Initialize variables with the current mode data ~ questions start from 1 always
        sAttempts = sPlayer.getMaxAttempts();
        sCurrQuestions = 1;
        sClockCnt = sPlayer.getMaxTime();
        // Retrieve the data of our app
        sData = sPlayer.getData();
        //Handler for the clock - Runnable function
        handler.postDelayed(runnable, 100);
        //Game begins - Loading data, updating the fields
        updateGame(sData, true);

        //Listener for reset button
        mResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sSoundEnabled) {
                    //Listener for the resetSound
                    MediaPlayer resetSound = MediaPlayer.create(GameActivity.this, R.raw.direct);
                    resetSound.setOnCompletionListener(new SoundOnCompletionListener());
                    resetSound.start();
                }
                reset();
            }
        });
        //Listener for the skip button
        mSkipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sSoundEnabled) {
                    //Listener for the resetSound
                    MediaPlayer skipSound = MediaPlayer.create(GameActivity.this, R.raw.skip_sound);
                    skipSound.setOnCompletionListener(new SoundOnCompletionListener());
                    skipSound.start();
                }
                updateGame(sData, true);
            }
        });

    }
    //Reset button and put them back to their position
    public void reset() {
        mDragedResultTextView.setText("");
        switch (mMode) {
            case "0":
                mLetter1TextView.setVisibility(View.VISIBLE);
                mLetter1TextView.setText(String.valueOf(word.charAt(0)));
                mLetter2TextView.setVisibility(View.VISIBLE);
                mLetter2TextView.setText(String.valueOf(word.charAt(1)));
                mLetter3TextView.setVisibility(View.VISIBLE);
                mLetter3TextView.setText(String.valueOf(word.charAt(2)));
                mLetter4TextView.setVisibility(View.VISIBLE);
                mLetter4TextView.setText(String.valueOf(word.charAt(3)));
                break;
            case "1":
                mLetter1TextView.setVisibility(View.VISIBLE);
                mLetter1TextView.setText(String.valueOf(word.charAt(0)));
                mLetter2TextView.setVisibility(View.VISIBLE);
                mLetter2TextView.setText(String.valueOf(word.charAt(1)));
                mLetter3TextView.setVisibility(View.VISIBLE);
                mLetter3TextView.setText(String.valueOf(word.charAt(2)));
                mLetter4TextView.setVisibility(View.VISIBLE);
                mLetter4TextView.setText(String.valueOf(word.charAt(3)));
                mLetter5TextView.setVisibility(View.VISIBLE);
                mLetter5TextView.setText(String.valueOf(word.charAt(4)));
                break;
            default:
                mLetter1TextView.setVisibility(View.VISIBLE);
                mLetter1TextView.setText(String.valueOf(word.charAt(0)));
                mLetter2TextView.setVisibility(View.VISIBLE);
                mLetter2TextView.setText(String.valueOf(word.charAt(1)));
                mLetter3TextView.setVisibility(View.VISIBLE);
                mLetter3TextView.setText(String.valueOf(word.charAt(2)));
                mLetter4TextView.setVisibility(View.VISIBLE);
                mLetter4TextView.setText(String.valueOf(word.charAt(3)));
                mLetter5TextView.setVisibility(View.VISIBLE);
                mLetter5TextView.setText(String.valueOf(word.charAt(4)));
                mLetter6TextView.setVisibility(View.VISIBLE);
                mLetter6TextView.setText(String.valueOf(word.charAt(5)));
                break;
        }
    }

    // Main function which refresh the data on the screen
    private void updateGame(ArrayList<String> data, Boolean setNewWord) {
        if (sCurrQuestions <= sPlayer.getMaxQuestions()) {
            if (setNewWord) {
                // Generating a random number in the array
                sSize = data.size();
                sIndex = ran.nextInt(sSize);
                // Refreshing the variables with new values - words
                sClockCnt = sPlayer.getMaxTime();
                sAttempts = sPlayer.getMaxAttempts();
                mQuestionTextView.setText("Question " + sCurrQuestions + "/" + sPlayer.getMaxQuestions());
                //Pseudo-mixing the word
                word = mixUp(data.get(sIndex));
                //In case the pseudo-mixing failed and the word is equal to the unmixed one
                while (word.equals("-1")) {
                    word = mixUp(data.get(sIndex));
                }
                reset();
                mAttemptsTextView.setText("Attempts left: " + GameActivity.sAttempts); // Printing the number of attempts
                sCurrQuestions++;
                // Storing the string we mixed up in a temp string variable so we can compare it with the input
                sTmpStrResult = data.get(sIndex);
                data.remove(sIndex); // Removing the string from the array list
            }
        } else {
            sCurrQuestions = 1;
            sAttempts = sPlayer.getMaxAttempts();
            Toast.makeText(GameActivity.this, "Game Finished", Toast.LENGTH_SHORT).show();
            Intent preStatsActivity = new Intent(getApplicationContext(), PreStatsActivity.class);
            preStatsActivity.putExtra(MODE_TAG, mMode);
            preStatsActivity.putExtra(TOTAL_ANSWERS_TAG, String.valueOf(mTotalCorrectAnswers));
            startActivity(preStatsActivity);
            handler.removeCallbacks(runnable); // Forcing the callback - runnable to stop
        }
    }

    //Initialize player mode function
    private void initializePlayerMode() {
        switch (mMode) {
            // beginner mode
            case "0":
                sPlayer = new GameObject(4, 20, 10, 0, DataGame.getDataBegginer());
                break;
            // Intermediate mode
            case "1":
                sPlayer = new GameObject(3, 20, 13, 1, DataGame.getDataInter());
                break;
            //Expert mode
            case "2":
                sPlayer = new GameObject(2, 20, 15, 2, DataGame.getDataHard());
                break;
            default:
                // By default beginner mode
                sPlayer = new GameObject(4, 20, 10, 0, DataGame.getDataBegginer());
                break;
        }
    }

    //Dynamically set image function for the clock
    private void setImageClocks(int counter) {
        switch (counter) {
            case 0:
                mClockImg.setImageResource(R.drawable.clock0);
                break;
            case 1:
                mClockImg.setImageResource(R.drawable.clock1);
                break;
            case 2:
                mClockImg.setImageResource(R.drawable.clock2);
                break;
            case 3:
                mClockImg.setImageResource(R.drawable.clock3);
                break;
            case 4:
                mClockImg.setImageResource(R.drawable.clock4);
                break;
            case 5:
                mClockImg.setImageResource(R.drawable.clock5);
                break;
            case 6:
                mClockImg.setImageResource(R.drawable.clock6);
                break;
            case 7:
                mClockImg.setImageResource(R.drawable.clock7);
                break;
            case 8:
                mClockImg.setImageResource(R.drawable.clock8);
                break;
            case 9:
                mClockImg.setImageResource(R.drawable.clock9);
                break;
            case 10:
                mClockImg.setImageResource(R.drawable.clock10);
                break;
            case 11:
                mClockImg.setImageResource(R.drawable.clock11);
                break;
            case 12:
                mClockImg.setImageResource(R.drawable.clock12);
                break;
            case 13:
                mClockImg.setImageResource(R.drawable.clock13);
                break;
            case 14:
                mClockImg.setImageResource(R.drawable.clock14);
                break;
            case 15:
                mClockImg.setImageResource(R.drawable.clock15);
                break;
            case 16:
                mClockImg.setImageResource(R.drawable.clock16);
                break;
            case 17:
                mClockImg.setImageResource(R.drawable.clock17);
                break;
            case 18:
                mClockImg.setImageResource(R.drawable.clock18);
                break;
            case 19:
                mClockImg.setImageResource(R.drawable.clock19);
                break;
            case 20:
                mClockImg.setImageResource(R.drawable.clock20);
                break;
            default:
                break;
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (sClockCnt <= 0) {
                sClockCnt = sPlayer.getMaxTime();
                updateGame(sData, true);
                handler.removeCallbacks(runnable);
            }
            sClockCnt--;
            setImageClocks(sClockCnt);
            mTimeTextView.setText("Seconds remaining: " + sClockCnt);
            handler.postDelayed(this, 1000);
        }
    };

    //Function to connect with xml file
    private void findViews() {
        mAttemptsTextView = (TextView) findViewById(R.id.attempts_txt);
        mTimeTextView = (TextView) findViewById(R.id.time_txt);
        mSubmitBtn = (Button) findViewById(R.id.submit_btn);
        mQuestionTextView = (TextView) findViewById(R.id.questions_txt);
        mClockImg = (ImageView) findViewById(R.id.clock_image);
        mLetter1TextView = (TextView) findViewById(R.id.let1_txt);
        mLetter2TextView = (TextView) findViewById(R.id.let2_txt);
        mLetter3TextView = (TextView) findViewById(R.id.let3_txt);
        mLetter4TextView = (TextView) findViewById(R.id.let4_txt);
        mLetter5TextView = (TextView) findViewById(R.id.let5_txt);
        mLetter6TextView = (TextView) findViewById(R.id.let6_txt);
        mDragedResultTextView = (TextView) findViewById(R.id.dragged_answers_txt);
        mResetBtn = (Button) findViewById(R.id.reset_btn);
        mSkipBtn = (Button) findViewById(R.id.skip_btn);
    }

    // Mixing up a String, returns the mixed string with spaces
    private String mixUp(String word) {
        if (word.length() <= 0) {
            return "";
        }
        if (word.length() == 1) {
            return word;
        }
        String resCmp = "";
        String res = "";
        Random ran = new Random();
        int size;
        int index;
        ArrayList<Character> chars = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            chars.add(Character.valueOf(word.charAt(i)));
        }
        while (!chars.isEmpty()) {
            size = chars.size();
            index = ran.nextInt(size);
            res += chars.get(index);
            resCmp += chars.get(index);
            chars.remove(index);
        }
        if (resCmp.equals(word)) {
            return "-1"; // -1 if the word still the same after mixing
        }
        return res;
    }

    //onTouch listener for the textvies
    private final class ChoiceTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            /*
             * Drag details: we only need default behavior
             * - clip data could be set to pass data as part of drag
             * - shadow can be tailored
             */
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    //onDrag listener
    private class ChoiceDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:
                    //handle the dragged view being dropped over a drop view
                    View view = (View) event.getLocalState();
                    //view dragged item is being dropped on
                    TextView dropTarget = (TextView) v;
                    //view being dragged and dropped
                    TextView dropped = (TextView) view;
                    //stop displaying the view where it was before it was dragged
                    view.setVisibility(View.INVISIBLE);
                    //update the text in the target view to reflect the data being dropped
                    dropTarget.setText(dropTarget.getText().toString() + dropped.getText().toString());
                    if (sSoundEnabled) {
                        //Initialize listener for the sound
                        MediaPlayer draggedSound = MediaPlayer.create(GameActivity.this, R.raw.appointed);
                        draggedSound.setOnCompletionListener(new SoundOnCompletionListener());
                        draggedSound.start();
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    //Release mp3 resources listener
    private final class SoundOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            mp.reset();
            mp.release();
        }
    }

    //Listener for the submitButton
    private final class OnClickSubmit implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //Services Vibrator - MediaPlayer
            final MediaPlayer successSound = MediaPlayer.create(GameActivity.this, R.raw.success);
            final MediaPlayer failSound = MediaPlayer.create(GameActivity.this, R.raw.failed);
            //Listeners for release mp3 resources
            successSound.setOnCompletionListener(new SoundOnCompletionListener());
            failSound.setOnCompletionListener(new SoundOnCompletionListener());
            //Vibrator service
            final Vibrator vibrator = (Vibrator) getSystemService(GameActivity.this.VIBRATOR_SERVICE);
            if (sAttempts != 0) {
                if (mDragedResultTextView.getText().toString().equals(sTmpStrResult)) {
                    if (sSoundEnabled)
                        successSound.start();
                    if (sVibrationEnabled)
                        vibrator.vibrate(200);
                    mTotalCorrectAnswers++;
                    updateGame(sData, true);
                    mDragedResultTextView.setText("");
                } else {
                    if (sSoundEnabled)
                        failSound.start();
                    if (sVibrationEnabled)
                        vibrator.vibrate(200);
                    sAttempts--;
                    mAttemptsTextView.setText("Attempts left: " + sAttempts);
                }
            } else {
                //Reset the clock counter and the attempts
                sClockCnt = 0;
                sAttempts = sPlayer.getMaxAttempts();
                updateGame(sData, false);
            }
            reset();
        }
    }

    //Override methods
    @Override
    public void onBackPressed() {
        // Forcing the callback - runnable to stop (terminating clock process)
        handler.removeCallbacks(runnable);
        //Terminating the activity
        finish();
        super.onBackPressed();
    }
}
