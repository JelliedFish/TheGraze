package com.example.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import java.io.FileNotFoundException;

public class Options extends AppCompatActivity {

    public Options() throws FileNotFoundException {}


    String tag = "options";

    boolean musicState = false;
    byte diffState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        ImageButton btn_options_to_main = (ImageButton) findViewById(R.id.options_return);
        btn_options_to_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent options_to_main = new Intent(getBaseContext(), MainMenu.class);
                startActivity(options_to_main);
            }
        });

        final ImageButton btn_music = (ImageButton) findViewById(R.id.options_music_onoff);
        btn_music.setBackgroundResource(R.drawable.ic_btn_settings_off);
        btn_music.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (musicState) {
                    musicState = false;
                    btn_music.setBackgroundResource(R.drawable.ic_btn_settings_off);
                } else {
                    musicState = true;
                    btn_music.setBackgroundResource(R.drawable.ic_btn_settings_on);
                }
            }
        });
    }
}
