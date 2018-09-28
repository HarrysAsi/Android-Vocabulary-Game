package com.teiath.harrys.vquiz.Activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import com.teiath.harrys.vquiz.R;

/**
 * Created by harrys on 9/2/2018.
 */

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.drawable.background_main);

        //final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.martian_gun);
        //mediaPlayer.start();

        /*mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        /**    @Override
        /**    public void onCompletion(MediaPlayer mp) {
        /**        mp.reset();
        /**        mp.release();
        /**    }
        });*/

        Handler handler = new Handler();
        handler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, MenuActivity.class));
                        finish();//destroying Splash activity
                    }
                }, 2500);

    }
}
