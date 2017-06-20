package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ImageButton btn_help_to_main = (ImageButton)findViewById(R.id.help_return);
        btn_help_to_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent help_to_main = new Intent(getBaseContext(), MainMenu.class);
                startActivity(help_to_main);

            }
        });

    }
}
