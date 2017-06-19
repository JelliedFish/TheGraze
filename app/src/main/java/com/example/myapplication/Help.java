package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class Help extends AppCompatActivity {

    String tag="help";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Log.d(tag,"onCreate | ");


        ImageButton btn_help_to_main = (ImageButton)findViewById(R.id.btn_back1);
        btn_help_to_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent help_to_main = new Intent(getBaseContext(), MainActivity.class);
                startActivity(help_to_main);

            }
        });
    }
}
