package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity {


    String tag="main_activity";


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(tag,"onCreate | ");

        ImageButton btn_help = (ImageButton)findViewById(R.id.btn3);
        btn_help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent main_to_help = new Intent(getBaseContext(), Help.class);
                startActivity(main_to_help);

            }
        });
        ImageButton btn_options = (ImageButton)findViewById(R.id.btn2);
        btn_options.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent main_to_options = new Intent(getBaseContext(), Options.class);
                startActivity(main_to_options);

            }
        });

            ImageButton btn_exit = (ImageButton) findViewById(R.id.btn4);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExit();
            }
        });

        ImageButton btn_play = (ImageButton)findViewById(R.id.btn1);
        btn_play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent main_to_play = new Intent(getBaseContext(), Gameplay.class);
                startActivity(main_to_play);

            }
        });


    }


    protected void onResume(){
        super.onResume();
        Log.d(tag,"onResume | ");
    }

    protected void onStop(){
        super.onStop();
        Log.d(tag,"onStop | ");
    }

    protected void onPause(){
        super.onPause();
        Log.d(tag,"onPause | ");
    }


    public void AppExit() {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}