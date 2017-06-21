package com.example.myapplication;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class Options extends AppCompatActivity {

    boolean musicState = false;
    byte diffState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ImageButton btn_options_to_main = (ImageButton) findViewById(R.id.options_return);
        btn_options_to_main.setBackgroundResource(R.drawable.ic_options_help_return);
        btn_options_to_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        /*final ImageButton btn_music_onoff = (ImageButton) findViewById(R.id.options_btn_music_onoff);
        if (musicState)
            btn_music_onoff.setBackgroundResource(R.drawable.ic_btn_settings_on);
        else
            btn_music_onoff.setBackgroundResource(R.drawable.ic_btn_settings_off);

        final ImageButton btn_diff_easy = (ImageButton) findViewById(R.id.options_btn_easy);
        if (diffState == 0)
            btn_diff_easy.setBackgroundResource(R.drawable.ic_settings_diff_easyselected);
        else
            btn_diff_easy.setBackgroundResource(R.drawable.ic_settings_diff_easy);

        final ImageButton btn_diff_medium = (ImageButton) findViewById(R.id.options_btn_medium);
        if (diffState == 1)
            btn_diff_medium.setBackgroundResource(R.drawable.ic_settings_diff_mediumselected);
        else
            btn_diff_medium.setBackgroundResource(R.drawable.ic_settings_diff_medium);

        final ImageButton btn_diff_hard = (ImageButton) findViewById(R.id.options_btn_hard);
        if (diffState == 2)
            btn_diff_hard.setBackgroundResource(R.drawable.ic_settings_diff_hardselected);
        else
            btn_diff_hard.setBackgroundResource(R.drawable.ic_settings_diff_hard);

        final ImageButton btn_reset = (ImageButton) findViewById(R.id.options_btn_reset);
        btn_reset.setBackgroundResource(R.drawable.ic_settings_reset);


        btn_music_onoff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (musicState) {
                    musicState = false;
                    btn_music_onoff.setBackgroundResource(R.drawable.ic_btn_settings_off);
                } else {
                    musicState = true;
                    btn_music_onoff.setBackgroundResource(R.drawable.ic_btn_settings_on);
                }
            }
        });

        btn_diff_easy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (diffState != 0) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            btn_diff_easy.setBackgroundResource(R.drawable.ic_settings_diff_easyclicked);
                            break;
                        case MotionEvent.ACTION_UP:
                            diffState = 0;
                            btn_diff_easy.setBackgroundResource(R.drawable.ic_settings_diff_easyselected);
                            btn_diff_medium.setBackgroundResource(R.drawable.ic_settings_diff_medium);
                            btn_diff_hard.setBackgroundResource(R.drawable.ic_settings_diff_hard);
                            break;
                    }
                }
                return true;
            }
        });

        btn_diff_medium.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (diffState != 1) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            btn_diff_medium.setBackgroundResource(R.drawable.ic_settings_diff_mediumclicked);
                            break;
                        case MotionEvent.ACTION_UP:
                            diffState = 1;
                            btn_diff_easy.setBackgroundResource(R.drawable.ic_settings_diff_easy);
                            btn_diff_medium.setBackgroundResource(R.drawable.ic_settings_diff_mediumselected);
                            btn_diff_hard.setBackgroundResource(R.drawable.ic_settings_diff_hard);
                            break;
                    }
                }
                return true;
            }
        });

        btn_diff_hard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (diffState != 2) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            btn_diff_hard.setBackgroundResource(R.drawable.ic_settings_diff_hardclicked);
                            break;
                        case MotionEvent.ACTION_UP:
                            diffState = 2;
                            btn_diff_easy.setBackgroundResource(R.drawable.ic_settings_diff_easy);
                            btn_diff_medium.setBackgroundResource(R.drawable.ic_settings_diff_medium);
                            btn_diff_hard.setBackgroundResource(R.drawable.ic_settings_diff_hardselected);
                            break;
                    }
                }
                return true;
            }
        });

        btn_reset.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (diffState != 2) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            btn_reset.setBackgroundResource(R.drawable.ic_settings_resetlicked);
                            break;
                        case MotionEvent.ACTION_UP:
                            btn_reset.setBackgroundResource(R.drawable.ic_settings_reset);
                            musicState = false;
                            diffState = 0;
                            btn_music_onoff.setBackgroundResource(R.drawable.ic_btn_settings_off);
                            btn_diff_easy.setBackgroundResource(R.drawable.ic_settings_diff_easyselected);
                            btn_diff_medium.setBackgroundResource(R.drawable.ic_settings_diff_medium);
                            btn_diff_hard.setBackgroundResource(R.drawable.ic_settings_diff_hard);
                            break;
                    }
                }
                return true;
            }
        });*/

    }
}
