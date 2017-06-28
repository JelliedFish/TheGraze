package com.example.myapplication;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Help extends AppCompatActivity {

    int helpPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ImageButton btn_help_to_main = (ImageButton)findViewById(R.id.help_return);
        btn_help_to_main.setBackgroundResource(R.drawable.ic_options_help_return);
        btn_help_to_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });



        final ImageButton switcher_left = (ImageButton) findViewById(R.id.switcher_left);
        final ImageButton switcher_right = (ImageButton) findViewById(R.id.switcher_right);

        final ImageView switcher_page1_icon = (ImageView) findViewById(R.id.switcher_page1_icon);
        final ImageView switcher_page2_icon = (ImageView) findViewById(R.id.switcher_page2_icon);
        final ImageView switcher_page3_icon = (ImageView) findViewById(R.id.switcher_page3_icon);

        final ImageView help_txt = (ImageView) findViewById(R.id.help_text);

        helpPage = 1;
        updateSwitcherTextures(1, switcher_left, switcher_right, switcher_page1_icon, switcher_page2_icon, switcher_page3_icon);
        updateHelpPage(1, help_txt);


        switcher_left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (helpPage > 1) {
                    helpPage--;
                    updateSwitcherTextures(helpPage, switcher_left, switcher_right, switcher_page1_icon, switcher_page2_icon, switcher_page3_icon);
                    updateHelpPage(helpPage, help_txt);
                }
            }
        });
        switcher_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (helpPage < 3) {
                    helpPage++;
                    updateSwitcherTextures(helpPage, switcher_left, switcher_right, switcher_page1_icon, switcher_page2_icon, switcher_page3_icon);
                    updateHelpPage(helpPage, help_txt);
                }
            }
        });
    }


    private static void updateSwitcherTextures(int currentHelpPage, ImageButton left, ImageButton right, ImageView p1, ImageView p2, ImageView p3) {
        switch (currentHelpPage) {
            case 1:
                left.setBackgroundResource(R.drawable.ic_help_switcher_left);
                right.setBackgroundResource(R.drawable.ic_help_switcher_rightactive);
                p1.setBackgroundResource(R.drawable.ic_help_switcher_page1active);
                p2.setBackgroundResource(R.drawable.ic_help_switcher_page2);
                p3.setBackgroundResource(R.drawable.ic_help_switcher_page3);
                break;
            case 2:
                left.setBackgroundResource(R.drawable.ic_help_switcher_leftactive);
                right.setBackgroundResource(R.drawable.ic_help_switcher_rightactive);
                p1.setBackgroundResource(R.drawable.ic_help_switcher_page1);
                p2.setBackgroundResource(R.drawable.ic_help_switcher_page2active);
                p3.setBackgroundResource(R.drawable.ic_help_switcher_page3);
                break;
            case 3:
                left.setBackgroundResource(R.drawable.ic_help_switcher_leftactive);
                right.setBackgroundResource(R.drawable.ic_help_switcher_right);
                p1.setBackgroundResource(R.drawable.ic_help_switcher_page1);
                p2.setBackgroundResource(R.drawable.ic_help_switcher_page2);
                p3.setBackgroundResource(R.drawable.ic_help_switcher_page3active);
                break;
        }
    }

    private static void updateHelpPage(int currentHelpPage, ImageView txt) {
        switch (currentHelpPage) {
            case 1:
                txt.setBackgroundResource(R.drawable.ic_help_page1_text);
                break;
            case 2:
                txt.setBackgroundResource(R.drawable.ic_help_page2_text);
                break;
            case 3:
                txt.setBackgroundResource(R.drawable.ic_help_page3_text);
                break;
        }
    }
}
