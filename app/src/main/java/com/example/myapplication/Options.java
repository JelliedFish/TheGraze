package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class Options extends AppCompatActivity {
    String tag="options";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Log.d(tag,"onCreate | ");

        ImageButton btn_options_to_main = (ImageButton)findViewById(R.id.btn_back2);
        btn_options_to_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent options_to_main = new Intent(getBaseContext(), MainMenu.class);
                startActivity(options_to_main);

            }
        });

    }
}
