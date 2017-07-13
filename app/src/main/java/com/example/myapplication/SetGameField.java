package com.example.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.myapplication.Abstract.ViewPatterns;
import com.example.myapplication.CustomObjects.IntSelector;

public class SetGameField extends AppCompatActivity {



    IntSelector fieldWidth = new IntSelector(8, 6, 12);
    IntSelector fieldHeight = new IntSelector(12, 6, 18);
    IntSelector teamsCount = new IntSelector(2, 2, 4);



    ImageButton setgamefield_width_left;
    ImageButton setgamefield_width_right;
    ImageView setgamefield_width;
    ImageButton setgamefield_height_left;
    ImageButton setgamefield_height_right;
    ImageView setgamefield_height;
    ImageButton setgamefield_teamscount_left;
    ImageButton setgamefield_teamscount_right;
    ImageView setgamefield_teamscount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setgamefield);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        ViewPatterns.generateReturnButton((ImageButton) findViewById(R.id.setgamefield_return), this);



        setgamefield_width_left = (ImageButton) findViewById(R.id.setgamefield_width_left);
        setgamefield_width_right = (ImageButton) findViewById(R.id.setgamefield_width_right);
        setgamefield_height_left = (ImageButton) findViewById(R.id.setgamefield_height_left);
        setgamefield_height_right = (ImageButton) findViewById(R.id.setgamefield_height_right);
        setgamefield_width = (ImageView) findViewById(R.id.setgamefield_width);
        setgamefield_height = (ImageView) findViewById(R.id.setgamefield_height);
        setgamefield_teamscount_left = (ImageButton) findViewById(R.id.ic_setgamefield_teamscount_left);
        setgamefield_teamscount_right = (ImageButton) findViewById(R.id.ic_setgamefield_teamscount_right);
        setgamefield_teamscount = (ImageView) findViewById(R.id.ic_setgamefield_teamscount);

        final ImageButton setgamefield_start = (ImageButton) findViewById(R.id.setgamefield_play);
        final ImageButton setgamefield_reset = (ImageButton) findViewById(R.id.setgamefield_reset);

        setgamefield_start.setBackgroundResource(R.drawable.ic_setgamefield_play);
        setgamefield_reset.setBackgroundResource(R.drawable.ic_settings_reset);



        resetAll();



        setgamefield_width_left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fieldWidth.minus();
                updateWidthButtons();
            }
        });

        setgamefield_width_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fieldWidth.plus();
                updateWidthButtons();
            }
        });

        setgamefield_height_left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fieldHeight.minus();
                updateHeightButtons();
            }
        });

        setgamefield_height_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fieldHeight.plus();
                updateHeightButtons();
            }
        });

        setgamefield_teamscount_left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                teamsCount.minus();
                updateTeamsCountButtons();
            }
        });

        setgamefield_teamscount_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                teamsCount.plus();
                updateTeamsCountButtons();
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

                        sgf_to_gameplay.putExtra("GAME_FIELD_KEY_WIDTH", fieldWidth.getVal());
                        sgf_to_gameplay.putExtra("GAME_FIELD_KEY_HEIGHT", fieldHeight.getVal());
                        sgf_to_gameplay.putExtra("GAME_FIELD_KEY_TEAMSCOUNT", teamsCount.getVal());
                        if (teamsCount.getVal() == 2)
                            sgf_to_gameplay.putExtra("GAME_FIELD_KEY_CASTLESCOORDS", "0;" + (fieldWidth.getVal() * fieldHeight.getVal() - 1));
                        if (teamsCount.getVal() == 4)
                            sgf_to_gameplay.putExtra("GAME_FIELD_KEY_CASTLESCOORDS", "0;" + (fieldWidth.getVal() - 1) + ";" + (fieldWidth.getVal() * fieldHeight.getVal() - 1) + ";" + (fieldWidth.getVal() * (fieldHeight.getVal() - 1)));
                        sgf_to_gameplay.putExtra("GAME_FIELD_KEY_BUTTONSDATA", getButtonsDataForRect(fieldWidth.getVal(), fieldHeight.getVal()));

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
                        resetAll();
                        break;
                }
                return true;
            }
        });



    }

    private void updateWidthButtons() {
        if (fieldWidth.isMin())
            setgamefield_width_left.setBackgroundResource(R.drawable.ic_btn_settings_left);
        else
            setgamefield_width_left.setBackgroundResource(R.drawable.ic_btn_settings_leftactive);

        if (fieldWidth.isMax())
            setgamefield_width_right.setBackgroundResource(R.drawable.ic_btn_settings_right);
        else
            setgamefield_width_right.setBackgroundResource(R.drawable.ic_btn_settings_rightactive);

        switch (fieldWidth.getVal()) {
            case 6: setgamefield_width.setImageResource(R.drawable.ic_setgamefield_num_6); break;
            case 7: setgamefield_width.setImageResource(R.drawable.ic_setgamefield_num_7); break;
            case 8: setgamefield_width.setImageResource(R.drawable.ic_setgamefield_num_8); break;
            case 9: setgamefield_width.setImageResource(R.drawable.ic_setgamefield_num_9); break;
            case 10: setgamefield_width.setImageResource(R.drawable.ic_setgamefield_num_10); break;
            case 11: setgamefield_width.setImageResource(R.drawable.ic_setgamefield_num_11); break;
            case 12: setgamefield_width.setImageResource(R.drawable.ic_setgamefield_num_12); break;
        }
    }

    private void updateHeightButtons() {
        if (fieldHeight.isMin())
            setgamefield_height_left.setBackgroundResource(R.drawable.ic_btn_settings_left);
        else
            setgamefield_height_left.setBackgroundResource(R.drawable.ic_btn_settings_leftactive);

        if (fieldHeight.isMax())
            setgamefield_height_right.setBackgroundResource(R.drawable.ic_btn_settings_right);
        else
            setgamefield_height_right.setBackgroundResource(R.drawable.ic_btn_settings_rightactive);

        switch (fieldHeight.getVal()) {
            case 5: setgamefield_height.setImageResource(R.drawable.ic_setgamefield_num_5); break;
            case 6: setgamefield_height.setImageResource(R.drawable.ic_setgamefield_num_6); break;
            case 7: setgamefield_height.setImageResource(R.drawable.ic_setgamefield_num_7); break;
            case 8: setgamefield_height.setImageResource(R.drawable.ic_setgamefield_num_8); break;
            case 9: setgamefield_height.setImageResource(R.drawable.ic_setgamefield_num_9); break;
            case 10: setgamefield_height.setImageResource(R.drawable.ic_setgamefield_num_10); break;
            case 11: setgamefield_height.setImageResource(R.drawable.ic_setgamefield_num_11); break;
            case 12: setgamefield_height.setImageResource(R.drawable.ic_setgamefield_num_12); break;
            case 13: setgamefield_height.setImageResource(R.drawable.ic_setgamefield_num_13); break;
            case 14: setgamefield_height.setImageResource(R.drawable.ic_setgamefield_num_14); break;
            case 15: setgamefield_height.setImageResource(R.drawable.ic_setgamefield_num_15); break;
            case 16: setgamefield_height.setImageResource(R.drawable.ic_setgamefield_num_16); break;
            case 17: setgamefield_height.setImageResource(R.drawable.ic_setgamefield_num_17); break;
            case 18: setgamefield_height.setImageResource(R.drawable.ic_setgamefield_num_18); break;
        }
    }

    private void updateTeamsCountButtons() {
        if (teamsCount.getVal() == 2) {
            setgamefield_teamscount_left.setBackgroundResource(R.drawable.ic_btn_settings_left);
            setgamefield_teamscount.setBackgroundResource(R.drawable.ic_setgamefield_num_2);
            setgamefield_teamscount_right.setBackgroundResource(R.drawable.ic_btn_settings_rightactive);
        }
        if (teamsCount.getVal() == 4) {
            setgamefield_teamscount_left.setBackgroundResource(R.drawable.ic_btn_settings_leftactive);
            setgamefield_teamscount.setBackgroundResource(R.drawable.ic_setgamefield_num_4);
            setgamefield_teamscount_right.setBackgroundResource(R.drawable.ic_btn_settings_right);
        }
    }


    private String getButtonsDataForRect(int width, int height) {
        String res = "";
        for (int i = 0; i < width * height; i++) {
            res += "1";
        }
        return res;
    }


    private void resetSelectors() {
        fieldWidth.setVal(8);
        fieldHeight.setVal(12);
        teamsCount.setVal(2);
        teamsCount.clearBL();
        teamsCount.addToBL(3);
    }

    private void resetAll() {
        resetSelectors();
        updateWidthButtons();
        updateHeightButtons();
        updateTeamsCountButtons();
    }

}
