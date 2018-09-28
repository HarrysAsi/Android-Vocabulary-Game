package com.teiath.harrys.vquiz.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.teiath.harrys.vquiz.R;

public class SettingsActivity extends AppCompatActivity {
    //Member variables
    private Switch mVibration, mSound;
    private Button mResetSwitches;
    //Static variables - handling the sound and vibration
    private static boolean sVibrEnabled;
    private static boolean sSoundEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //Connect to xml
        findViews();

        //Shared preferences for the sound and vibration
        final SharedPreferences sharedPreferences = getSharedPreferences(MenuActivity.GLOBAL_SHARED_TAG, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        sVibrEnabled = sharedPreferences.getBoolean(MenuActivity.VIBRATION_TAG, true);
        sSoundEnabled = sharedPreferences.getBoolean(MenuActivity.SOUND_TAG, true);
        mVibration.setChecked(sVibrEnabled);
        mSound.setChecked(sSoundEnabled);
        //Listener to change the value of vibration(true-false)
        mVibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sVibrEnabled = isChecked;
                editor.putBoolean(MenuActivity.VIBRATION_TAG, sVibrEnabled);
                editor.commit();
            }
        });
        //Listener to change the value of sound(true-false)
        mSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sSoundEnabled = isChecked;
                editor.putBoolean(MenuActivity.SOUND_TAG, sSoundEnabled);
                editor.commit();
            }
        });
        //Listener for the reset button
        mResetSwitches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSound.setChecked(true);
                mVibration.setChecked(true);
                editor.putBoolean(MenuActivity.SOUND_TAG, true);
                editor.putBoolean(MenuActivity.VIBRATION_TAG, true);
                editor.commit();
            }
        });
    }

    //Connect to xml
    public void findViews() {
        mResetSwitches = (Button) findViewById(R.id.reset_btn);
        mVibration = (Switch) findViewById(R.id.vibration_switch);
        mSound = (Switch) findViewById(R.id.sound_switch);
    }
}
