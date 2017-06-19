package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class Gameplay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        List<CustomButton> fields = new ArrayList<CustomButton>();

        for(int i=0;i<84;i++){
            fields.add(new CustomButton(getBaseContext()));
        }

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new CustomAdapter(this, fields));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
