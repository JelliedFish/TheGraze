package com.example.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class SetGameFieldType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setgamefieldtype);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        ImageButton btn_setgamefieldtype_to_main = (ImageButton) findViewById(R.id.setgamefieldtype_return);
        btn_setgamefieldtype_to_main.setBackgroundResource(R.drawable.ic_options_help_return);
        btn_setgamefieldtype_to_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


        final ImageButton btn_rect = (ImageButton) findViewById(R.id.setgamefieldtype_btn_rect);
        btn_rect.setBackgroundResource(R.drawable.ic_setgamefield_btn_rect);
        btn_rect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_rect.setBackgroundResource(R.drawable.ic_setgamefield_btn_rectclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_rect.setBackgroundResource(R.drawable.ic_setgamefield_btn_rect);
                        Intent sgft_to_sgf = new Intent(getBaseContext(), SetGameField.class);
                        sgft_to_sgf.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(sgft_to_sgf);
                        break;
                }
                return true;
            }
        });

        final ImageButton btn_freeform = (ImageButton) findViewById(R.id.setgamefieldtype_btn_freeform);
        btn_freeform.setBackgroundResource(R.drawable.ic_setgamefield_btn_freeform);
        btn_freeform.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_freeform.setBackgroundResource(R.drawable.ic_setgamefield_btn_freeformclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_freeform.setBackgroundResource(R.drawable.ic_setgamefield_btn_freeform);
                        Intent sgft_to_sgf2 = new Intent(getBaseContext(), SetGameField2.class);
                        sgft_to_sgf2.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(sgft_to_sgf2);
                        break;
                }
                return true;
            }
        });

        /*final ImageButton btn_freeformme = (ImageButton) findViewById(R.id.setgamefieldtype_btn_freeformme);
        btn_freeformme.setBackgroundResource(R.drawable.ic_setgamefield_btn_freeformme);
        btn_freeformme.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_freeformme.setBackgroundResource(R.drawable.ic_setgamefield_btn_freeformmeclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_freeformme.setBackgroundResource(R.drawable.ic_setgamefield_btn_freeformme);
                        break;
                }
                return true;
            }
        });*/



    }

}
