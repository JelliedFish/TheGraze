package com.example.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class GameSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameselect);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ImageButton btn_gameselect_to_main = (ImageButton) findViewById(R.id.gameselect_return);
        btn_gameselect_to_main.setBackgroundResource(R.drawable.ic_options_help_return);
        btn_gameselect_to_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        final ImageButton btn_singleplayer = (ImageButton) findViewById(R.id.gameselect_singleplayer);
        btn_singleplayer.setBackgroundResource(R.drawable.ic_singleplayer);
        btn_singleplayer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_singleplayer.setBackgroundResource(R.drawable.ic_singleplayerclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_singleplayer.setBackgroundResource(R.drawable.ic_singleplayer);
                        break;
                }
                return true;
            }
        });

        final ImageButton btn_multiplayer = (ImageButton) findViewById(R.id.gameselect_multiplayer);
        btn_multiplayer.setBackgroundResource(R.drawable.ic_multiplayer);
        btn_multiplayer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_multiplayer.setBackgroundResource(R.drawable.ic_multiplayerclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_multiplayer.setBackgroundResource(R.drawable.ic_multiplayer);
                        Intent gs_to_sgf = new Intent(getBaseContext(), SetGameField.class);
                        gs_to_sgf.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(gs_to_sgf);
                        break;
                }
                return true;
            }
        });
    }


}
