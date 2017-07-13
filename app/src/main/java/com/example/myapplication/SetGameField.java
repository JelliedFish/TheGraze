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
import com.example.myapplication.Data.Const;

public class SetGameField extends AppCompatActivity {



    IntSelector fieldWidth = new IntSelector(8, 6, 18);
    IntSelector fieldHeight = new IntSelector(12, 6, 18);
    IntSelector teamsCount = new IntSelector(2, 2, 4);



    ImageButton setgamefield_width_left;
    ImageButton setgamefield_width_right;
    ImageButton setgamefield_width_left_super;
    ImageButton setgamefield_width_right_super;
    ImageView setgamefield_width;

    ImageButton setgamefield_height_left;
    ImageButton setgamefield_height_right;
    ImageButton setgamefield_height_left_super;
    ImageButton setgamefield_height_right_super;
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
        setgamefield_width_left_super = (ImageButton) findViewById(R.id.setgamefield_width_left_super);
        setgamefield_width_right_super = (ImageButton) findViewById(R.id.setgamefield_width_right_super);
        setgamefield_width = (ImageView) findViewById(R.id.setgamefield_width);

        setgamefield_height_left = (ImageButton) findViewById(R.id.setgamefield_height_left);
        setgamefield_height_right = (ImageButton) findViewById(R.id.setgamefield_height_right);
        setgamefield_height_left_super = (ImageButton) findViewById(R.id.setgamefield_height_left_super);
        setgamefield_height_right_super = (ImageButton) findViewById(R.id.setgamefield_height_right_super);
        setgamefield_height = (ImageView) findViewById(R.id.setgamefield_height);

        setgamefield_teamscount_left = (ImageButton) findViewById(R.id.ic_setgamefield_teamscount_left);
        setgamefield_teamscount_right = (ImageButton) findViewById(R.id.ic_setgamefield_teamscount_right);
        setgamefield_teamscount = (ImageView) findViewById(R.id.ic_setgamefield_teamscount);

        final ImageButton setgamefield_start = (ImageButton) findViewById(R.id.setgamefield_play);
        final ImageButton setgamefield_reset = (ImageButton) findViewById(R.id.setgamefield_reset);

        setgamefield_start.setBackgroundResource(R.drawable.ic_setgamefield_play);
        setgamefield_reset.setBackgroundResource(R.drawable.ic_settings_reset);



        resetAll();



        setgamefield_width_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!fieldWidth.isMin()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            setgamefield_width_left.setBackgroundResource(R.drawable.ic_btn_settings_leftclicked_rect);
                            break;
                        case MotionEvent.ACTION_UP:
                            fieldWidth.minus();
                            updateWidthButtons();
                            break;
                    }
                }
                return true;
            }
        });

        setgamefield_width_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!fieldWidth.isMax()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            setgamefield_width_right.setBackgroundResource(R.drawable.ic_btn_settings_rightclicked_rect);
                            break;
                        case MotionEvent.ACTION_UP:
                            fieldWidth.plus();
                            updateWidthButtons();
                            break;
                    }
                }
                return true;
            }
        });

        setgamefield_width_left_super.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!fieldWidth.isMin()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            setgamefield_width_left_super.setBackgroundResource(R.drawable.ic_btn_settings_leftclicked_super);
                            break;
                        case MotionEvent.ACTION_UP:
                            fieldWidth.minus(5);
                            updateWidthButtons();
                            break;
                    }
                }
                return true;
            }
        });

        setgamefield_width_right_super.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!fieldWidth.isMax()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            setgamefield_width_right_super.setBackgroundResource(R.drawable.ic_btn_settings_rightclicked_super);
                            break;
                        case MotionEvent.ACTION_UP:
                            fieldWidth.plus(5);
                            updateWidthButtons();
                            break;
                    }
                }
                return true;
            }
        });

        setgamefield_height_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!fieldHeight.isMin()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            setgamefield_height_left.setBackgroundResource(R.drawable.ic_btn_settings_leftclicked_rect);
                            break;
                        case MotionEvent.ACTION_UP:
                            fieldHeight.minus();
                            updateHeightButtons();
                            break;
                    }
                }
                return true;
            }
        });

        setgamefield_height_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!fieldHeight.isMax()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            setgamefield_height_right.setBackgroundResource(R.drawable.ic_btn_settings_rightclicked_rect);
                            break;
                        case MotionEvent.ACTION_UP:
                            fieldHeight.plus();
                            updateHeightButtons();
                            break;
                    }
                }
                return true;
            }
        });

        setgamefield_height_left_super.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!fieldHeight.isMin()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            setgamefield_height_left_super.setBackgroundResource(R.drawable.ic_btn_settings_leftclicked_super);
                            break;
                        case MotionEvent.ACTION_UP:
                            fieldHeight.minus(5);
                            updateHeightButtons();
                            break;
                    }
                }
                return true;
            }
        });

        setgamefield_height_right_super.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!fieldHeight.isMax()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            setgamefield_height_right_super.setBackgroundResource(R.drawable.ic_btn_settings_rightclicked_super);
                            break;
                        case MotionEvent.ACTION_UP:
                            fieldHeight.plus(5);
                            updateHeightButtons();
                            break;
                    }
                }
                return true;
            }
        });

        /*setgamefield_width_left.setOnClickListener(new View.OnClickListener() {
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
        });*/

        setgamefield_teamscount_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!teamsCount.isMin()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            setgamefield_teamscount_left.setBackgroundResource(R.drawable.ic_btn_settings_leftclicked);
                            break;
                        case MotionEvent.ACTION_UP:
                            teamsCount.minus();
                            updateTeamsCountButtons();
                            break;
                    }
                }
                return true;
            }
        });

        setgamefield_teamscount_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!teamsCount.isMax()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            setgamefield_teamscount_right.setBackgroundResource(R.drawable.ic_btn_settings_rightclicked);
                            break;
                        case MotionEvent.ACTION_UP:
                            teamsCount.plus();
                            updateTeamsCountButtons();
                            break;
                    }
                }
                return true;
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
        if (fieldWidth.isMin()) {
            setgamefield_width_left.setBackgroundResource(R.drawable.ic_btn_settings_left_rect);
            setgamefield_width_left_super.setBackgroundResource(R.drawable.ic_btn_settings_left_super);
        } else {
            setgamefield_width_left.setBackgroundResource(R.drawable.ic_btn_settings_leftactive_rect);
            setgamefield_width_left_super.setBackgroundResource(R.drawable.ic_btn_settings_leftactive_super);
        }

        if (fieldWidth.isMax()) {
            setgamefield_width_right.setBackgroundResource(R.drawable.ic_btn_settings_right_rect);
            setgamefield_width_right_super.setBackgroundResource(R.drawable.ic_btn_settings_right_super);
        } else {
            setgamefield_width_right.setBackgroundResource(R.drawable.ic_btn_settings_rightactive_rect);
            setgamefield_width_right_super.setBackgroundResource(R.drawable.ic_btn_settings_rightactive_super);
        }

        ViewPatterns.setNumImg(setgamefield_width, fieldWidth.getVal(), Const.COLOR_GRAY);
    }

    private void updateHeightButtons() {
        if (fieldHeight.isMin()) {
            setgamefield_height_left.setBackgroundResource(R.drawable.ic_btn_settings_left_rect);
            setgamefield_height_left_super.setBackgroundResource(R.drawable.ic_btn_settings_left_super);
        } else {
            setgamefield_height_left.setBackgroundResource(R.drawable.ic_btn_settings_leftactive_rect);
            setgamefield_height_left_super.setBackgroundResource(R.drawable.ic_btn_settings_leftactive_super);
        }

        if (fieldHeight.isMax()) {
            setgamefield_height_right.setBackgroundResource(R.drawable.ic_btn_settings_right_rect);
            setgamefield_height_right_super.setBackgroundResource(R.drawable.ic_btn_settings_right_super);
        } else {
            setgamefield_height_right.setBackgroundResource(R.drawable.ic_btn_settings_rightactive_rect);
            setgamefield_height_right_super.setBackgroundResource(R.drawable.ic_btn_settings_rightactive_super);
        }

        ViewPatterns.setNumImg(setgamefield_height, fieldHeight.getVal(), Const.COLOR_GRAY);
    }

    private void updateTeamsCountButtons() {

        ViewPatterns.setNumImg(setgamefield_teamscount, teamsCount.getVal(), Const.COLOR_GRAY);

        if (teamsCount.isMin()) {
            setgamefield_teamscount_left.setBackgroundResource(R.drawable.ic_btn_settings_left);
            setgamefield_teamscount_right.setBackgroundResource(R.drawable.ic_btn_settings_rightactive);
        }

        if (teamsCount.isMax()) {
            setgamefield_teamscount_left.setBackgroundResource(R.drawable.ic_btn_settings_leftactive);
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
