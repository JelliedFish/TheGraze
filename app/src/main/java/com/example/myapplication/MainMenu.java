package com.example.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;


public class MainMenu extends AppCompatActivity {


    String tag = "main_menu";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final ImageButton btn_play = (ImageButton) findViewById(R.id.btn1);
        btn_play.setBackgroundResource(R.drawable.ic_btn_mm_play);
        btn_play.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_play.setBackgroundResource(R.drawable.ic_btn_mm_playclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_play.setBackgroundResource(R.drawable.ic_btn_mm_play);
                        Intent main_to_play = new Intent(getBaseContext(), Gameplay.class);
                        startActivity(main_to_play);
                        break;
                }
                return true;
            }
        });

        final ImageButton btn_options = (ImageButton) findViewById(R.id.btn2);
        btn_options.setBackgroundResource(R.drawable.ic_btn_mm_options);
        btn_options.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_options.setBackgroundResource(R.drawable.ic_btn_mm_optionsclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_options.setBackgroundResource(R.drawable.ic_btn_mm_options);
                        Intent main_to_options = new Intent(getBaseContext(), Options.class);
                        startActivity(main_to_options);
                        break;
                }
                return true;
            }
        });

        final ImageButton btn_help = (ImageButton) findViewById(R.id.btn3);
        btn_help.setBackgroundResource(R.drawable.ic_btn_mm_help);
        btn_help.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_help.setBackgroundResource(R.drawable.ic_btn_mm_helpclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_help.setBackgroundResource(R.drawable.ic_btn_mm_help);
                        Intent main_to_help = new Intent(getBaseContext(), Help.class);
                        startActivity(main_to_help);
                        break;
                }
                return true;
            }
        });

        final ImageButton btn_exit = (ImageButton) findViewById(R.id.btn4);
        btn_exit.setBackgroundResource(R.drawable.ic_btn_mm_exit);
        btn_exit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_exit.setBackgroundResource(R.drawable.ic_btn_mm_exitclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_exit.setBackgroundResource(R.drawable.ic_btn_mm_exit);
                        AppExit();
                        break;
                }
                return true;
            }
        });

    }


    public void AppExit() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}