package com.example.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SetGameField extends AppCompatActivity {

    int fieldWidth = 8;
    int fieldHeight = 12;
    int teamsCount = 2;

    static final int MIN_WIDTH = 5;
    static final int MAX_WIDTH = 12;
    static final int MIN_HEIGHT = 6;
    static final int MAX_HEIGHT = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setgamefield);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        final ImageButton btn_setgamefield_to_main = (ImageButton) findViewById(R.id.setgamefield_return);

        final ImageButton setgamefield_width_left = (ImageButton) findViewById(R.id.setgamefield_width_left);
        final ImageButton setgamefield_width_right = (ImageButton) findViewById(R.id.setgamefield_width_right);
        final ImageButton setgamefield_height_left = (ImageButton) findViewById(R.id.setgamefield_height_left);
        final ImageButton setgamefield_height_right = (ImageButton) findViewById(R.id.setgamefield_height_right);
        final ImageView setgamefield_width = (ImageView) findViewById(R.id.setgamefield_width);
        final ImageView setgamefield_height = (ImageView) findViewById(R.id.setgamefield_height);
        final ImageButton setgamefield_teamscount_left = (ImageButton) findViewById(R.id.ic_setgamefield_teamscount_left);
        final ImageButton setgamefield_teamscount_right = (ImageButton) findViewById(R.id.ic_setgamefield_teamscount_right);
        final ImageView setgamefield_teamscount = (ImageView) findViewById(R.id.ic_setgamefield_teamscount);

        final ImageButton setgamefield_start = (ImageButton) findViewById(R.id.setgamefield_play);
        final ImageButton setgamefield_reset = (ImageButton) findViewById(R.id.setgamefield_reset);

        btn_setgamefield_to_main.setBackgroundResource(R.drawable.ic_options_help_return);

        updateWidthButtons(fieldWidth, setgamefield_width_left, setgamefield_width, setgamefield_width_right);
        updateHeightButtons(fieldHeight, setgamefield_height_left, setgamefield_height, setgamefield_height_right);

        setgamefield_teamscount.setBackgroundResource(R.drawable.ic_setgamefield_num_2);
        setgamefield_teamscount_left.setBackgroundResource(R.drawable.ic_btn_settings_left);
        setgamefield_teamscount_right.setBackgroundResource(R.drawable.ic_btn_settings_rightactive);

        setgamefield_start.setBackgroundResource(R.drawable.ic_setgamefield_play);
        setgamefield_reset.setBackgroundResource(R.drawable.ic_settings_reset);



        btn_setgamefield_to_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_setgamefield_to_main.setBackgroundResource(R.drawable.ic_options_help_returnclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_setgamefield_to_main.setBackgroundResource(R.drawable.ic_options_help_return);
                        finish();
                        break;
                }
                return true;
            }
        });

        setgamefield_width_left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (fieldWidth > MIN_WIDTH) {
                    fieldWidth--;
                    updateWidthButtons(fieldWidth, setgamefield_width_left, setgamefield_width, setgamefield_width_right);
                }
            }
        });

        setgamefield_width_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (fieldWidth < MAX_WIDTH) {
                    fieldWidth++;
                    updateWidthButtons(fieldWidth, setgamefield_width_left, setgamefield_width, setgamefield_width_right);
                }
            }
        });

        setgamefield_height_left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (fieldHeight > MIN_HEIGHT) {
                    fieldHeight--;
                    updateHeightButtons(fieldHeight, setgamefield_height_left, setgamefield_height, setgamefield_height_right);
                }
            }
        });

        setgamefield_height_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (fieldHeight < MAX_HEIGHT) {
                    fieldHeight++;
                    updateHeightButtons(fieldHeight, setgamefield_height_left, setgamefield_height, setgamefield_height_right);
                }
            }
        });

        setgamefield_teamscount_left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (teamsCount == 4) {
                    setgamefield_teamscount.setBackgroundResource(R.drawable.ic_setgamefield_num_2);
                    setgamefield_teamscount_left.setBackgroundResource(R.drawable.ic_btn_settings_left);
                    setgamefield_teamscount_right.setBackgroundResource(R.drawable.ic_btn_settings_rightactive);
                    teamsCount = 2;
                }
            }
        });

        setgamefield_teamscount_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (teamsCount == 2) {
                    setgamefield_teamscount.setBackgroundResource(R.drawable.ic_setgamefield_num_4);
                    setgamefield_teamscount_left.setBackgroundResource(R.drawable.ic_btn_settings_leftactive);
                    setgamefield_teamscount_right.setBackgroundResource(R.drawable.ic_btn_settings_right);
                    teamsCount = 4;
                }
            }
        });


        setgamefield_start.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setgamefield_start.setBackgroundResource(R.drawable.ic_setgamefield_playclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        setgamefield_start.setBackgroundResource(R.drawable.ic_setgamefield_play);
                        Intent sgf_to_gameplay = new Intent(getBaseContext(), Gameplay.class);

                        sgf_to_gameplay.putExtra("GAME_FIELD_KEY_WIDTH", fieldWidth);
                        sgf_to_gameplay.putExtra("GAME_FIELD_KEY_HEIGHT", fieldHeight);
                        sgf_to_gameplay.putExtra("GAME_FIELD_KEY_TEAMSCOUNT", teamsCount);
                        if (teamsCount == 2)
                            sgf_to_gameplay.putExtra("GAME_FIELD_KEY_CASTLESCOORDS", "0;" + (fieldWidth * fieldHeight - 1));
                        if (teamsCount == 4)
                            sgf_to_gameplay.putExtra("GAME_FIELD_KEY_CASTLESCOORDS", "0;" + (fieldWidth - 1) + ";" + (fieldWidth * fieldHeight - 1) + ";" + (fieldWidth * (fieldHeight - 1)));
                        sgf_to_gameplay.putExtra("GAME_FIELD_KEY_BUTTONSDATA", getButtonsDataForRect(fieldWidth, fieldHeight));

                        startActivity(sgf_to_gameplay);
                        break;
                }
                return true;
            }
        });

        setgamefield_reset.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setgamefield_reset.setBackgroundResource(R.drawable.ic_settings_resetlicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        setgamefield_reset.setBackgroundResource(R.drawable.ic_settings_reset);
                        fieldWidth = 8;
                        fieldHeight = 12;
                        teamsCount = 2;
                        updateWidthButtons(fieldWidth, setgamefield_width_left, setgamefield_width, setgamefield_width_right);
                        updateHeightButtons(fieldHeight, setgamefield_height_left, setgamefield_height, setgamefield_height_right);
                        break;
                }
                return true;
            }
        });



    }

    public static void updateWidthButtons(int value, ImageButton left, ImageView num, ImageButton right) {
        if (value > MIN_WIDTH)
            left.setBackgroundResource(R.drawable.ic_btn_settings_leftactive);
        else
            left.setBackgroundResource(R.drawable.ic_btn_settings_left);

        if (value < MAX_WIDTH)
            right.setBackgroundResource(R.drawable.ic_btn_settings_rightactive);
        else
            right.setBackgroundResource(R.drawable.ic_btn_settings_right);

        switch (value) {
            case 5: num.setImageResource(R.drawable.ic_setgamefield_num_5); break;
            case 6: num.setImageResource(R.drawable.ic_setgamefield_num_6); break;
            case 7: num.setImageResource(R.drawable.ic_setgamefield_num_7); break;
            case 8: num.setImageResource(R.drawable.ic_setgamefield_num_8); break;
            case 9: num.setImageResource(R.drawable.ic_setgamefield_num_9); break;
            case 10: num.setImageResource(R.drawable.ic_setgamefield_num_10); break;
            case 11: num.setImageResource(R.drawable.ic_setgamefield_num_11); break;
            case 12: num.setImageResource(R.drawable.ic_setgamefield_num_12); break;
        }
    }

    public static void updateHeightButtons(int value, ImageButton left, ImageView num, ImageButton right) {
        if (value > MIN_HEIGHT)
            left.setBackgroundResource(R.drawable.ic_btn_settings_leftactive);
        else
            left.setBackgroundResource(R.drawable.ic_btn_settings_left);

        if (value < MAX_HEIGHT)
            right.setBackgroundResource(R.drawable.ic_btn_settings_rightactive);
        else
            right.setBackgroundResource(R.drawable.ic_btn_settings_right);

        switch (value) {
            case 5: num.setImageResource(R.drawable.ic_setgamefield_num_5); break;
            case 6: num.setImageResource(R.drawable.ic_setgamefield_num_6); break;
            case 7: num.setImageResource(R.drawable.ic_setgamefield_num_7); break;
            case 8: num.setImageResource(R.drawable.ic_setgamefield_num_8); break;
            case 9: num.setImageResource(R.drawable.ic_setgamefield_num_9); break;
            case 10: num.setImageResource(R.drawable.ic_setgamefield_num_10); break;
            case 11: num.setImageResource(R.drawable.ic_setgamefield_num_11); break;
            case 12: num.setImageResource(R.drawable.ic_setgamefield_num_12); break;
            case 13: num.setImageResource(R.drawable.ic_setgamefield_num_13); break;
            case 14: num.setImageResource(R.drawable.ic_setgamefield_num_14); break;
            case 15: num.setImageResource(R.drawable.ic_setgamefield_num_15); break;
            case 16: num.setImageResource(R.drawable.ic_setgamefield_num_16); break;
            case 17: num.setImageResource(R.drawable.ic_setgamefield_num_17); break;
            case 18: num.setImageResource(R.drawable.ic_setgamefield_num_18); break;
        }
    }

    public static String getButtonsDataForRect(int width, int height) {
        String res = "";
        for (int i = 0; i < width * height; i++) {
            res += "1";
        }
        return res;
    }
}
