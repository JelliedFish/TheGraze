package com.example.myapplication;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.myapplication.CustomObjects.SwitcherBoolean;
import com.example.myapplication.Data.GameSettings;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        final ImageButton btn_options_to_main = (ImageButton) findViewById(R.id.options_return);

        final ImageButton btn_music_on = (ImageButton) findViewById(R.id.options_btn_music_on);
        final ImageButton btn_music_off = (ImageButton) findViewById(R.id.options_btn_music_off);

        final ImageButton player1_leftArrow = (ImageButton) findViewById(R.id.options_player1_prev);
        final ImageView player1_texture = (ImageView) findViewById(R.id.options_player1_texture);
        final ImageButton player1_rightArrow = (ImageButton) findViewById(R.id.options_player1_next);

        final ImageButton player2_leftArrow = (ImageButton) findViewById(R.id.options_player2_prev);
        final ImageView player2_texture = (ImageView) findViewById(R.id.options_player2_texture);
        final ImageButton player2_rightArrow = (ImageButton) findViewById(R.id.options_player2_next);

        final ImageButton player3_leftArrow = (ImageButton) findViewById(R.id.options_player3_prev);
        final ImageView player3_texture = (ImageView) findViewById(R.id.options_player3_texture);
        final ImageButton player3_rightArrow = (ImageButton) findViewById(R.id.options_player3_next);

        final ImageButton player4_leftArrow = (ImageButton) findViewById(R.id.options_player4_prev);
        final ImageView player4_texture = (ImageView) findViewById(R.id.options_player4_texture);
        final ImageButton player4_rightArrow = (ImageButton) findViewById(R.id.options_player4_next);

        final ImageButton btn_reset = (ImageButton) findViewById(R.id.options_btn_reset);

        btn_options_to_main.setBackgroundResource(R.drawable.ic_options_help_return);

        updateMusicButtons(btn_music_on, btn_music_off);
        setPlayersPicturesForVar(GameSettings.getPlayers_textureState(0), player1_leftArrow, player1_texture, player1_rightArrow);
        setPlayersPicturesForVar(GameSettings.getPlayers_textureState(1), player2_leftArrow, player2_texture, player2_rightArrow);
        setPlayersPicturesForVar(GameSettings.getPlayers_textureState(2), player3_leftArrow, player3_texture, player3_rightArrow);
        setPlayersPicturesForVar(GameSettings.getPlayers_textureState(3), player4_leftArrow, player4_texture, player4_rightArrow);

        btn_reset.setBackgroundResource(R.drawable.ic_settings_reset);



        btn_options_to_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_options_to_main.setBackgroundResource(R.drawable.ic_options_help_returnclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_options_to_main.setBackgroundResource(R.drawable.ic_options_help_return);
                        finish();
                        break;
                }
                return true;
            }
        });

        btn_music_on.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!GameSettings.getMusicState()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            btn_music_on.setBackgroundResource(R.drawable.ic_btn_settings_onclicked);
                            break;
                        case MotionEvent.ACTION_UP:
                            GameSettings.setMusicState(true);
                            updateMusicButtons(btn_music_on, btn_music_off);
                            break;
                    }
                }
                return true;
            }
        });

        btn_music_off.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (GameSettings.getMusicState()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            btn_music_off.setBackgroundResource(R.drawable.ic_btn_settings_offclicked);
                            break;
                        case MotionEvent.ACTION_UP:
                            GameSettings.setMusicState(false);
                            updateMusicButtons(btn_music_on, btn_music_off);
                            break;
                    }
                }
                return true;
            }
        });

        player1_leftArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (GameSettings.getPlayers_textureState(0) > 1) {
                    GameSettings.addToTextureState(0, -1);
                    setPlayersPicturesForVar(GameSettings.getPlayers_textureState(0), player1_leftArrow, player1_texture, player1_rightArrow);
                }
            }
        });

        player1_rightArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (GameSettings.getPlayers_textureState(0) < 4) {
                    GameSettings.addToTextureState(0, 1);
                    setPlayersPicturesForVar(GameSettings.getPlayers_textureState(0), player1_leftArrow, player1_texture, player1_rightArrow);
                }
            }
        });

        player2_leftArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (GameSettings.getPlayers_textureState(1) > 1) {
                    GameSettings.addToTextureState(1, -1);
                    setPlayersPicturesForVar(GameSettings.getPlayers_textureState(1), player2_leftArrow, player2_texture, player2_rightArrow);
                }
            }
        });

        player2_rightArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (GameSettings.getPlayers_textureState(1) < 4) {
                    GameSettings.addToTextureState(1, 1);
                    setPlayersPicturesForVar(GameSettings.getPlayers_textureState(1), player2_leftArrow, player2_texture, player2_rightArrow);
                }
            }
        });

        player3_leftArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (GameSettings.getPlayers_textureState(2) > 1) {
                    GameSettings.addToTextureState(2, -1);
                    setPlayersPicturesForVar(GameSettings.getPlayers_textureState(2), player3_leftArrow, player3_texture, player3_rightArrow);
                }
            }
        });

        player3_rightArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (GameSettings.getPlayers_textureState(2) < 4) {
                    GameSettings.addToTextureState(2, 1);
                    setPlayersPicturesForVar(GameSettings.getPlayers_textureState(2), player3_leftArrow, player3_texture, player3_rightArrow);
                }
            }
        });

        player4_leftArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (GameSettings.getPlayers_textureState(3) > 1) {
                    GameSettings.addToTextureState(3, -1);
                    setPlayersPicturesForVar(GameSettings.getPlayers_textureState(3), player4_leftArrow, player4_texture, player4_rightArrow);
                }
            }
        });

        player4_rightArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (GameSettings.getPlayers_textureState(3) < 4) {
                    GameSettings.addToTextureState(3, 1);
                    setPlayersPicturesForVar(GameSettings.getPlayers_textureState(3), player4_leftArrow, player4_texture, player4_rightArrow);
                }
            }
        });

        btn_reset.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_reset.setBackgroundResource(R.drawable.ic_settings_resetlicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_reset.setBackgroundResource(R.drawable.ic_settings_reset);

                        GameSettings.reset();

                        updateMusicButtons(btn_music_on, btn_music_off);
                        setPlayersPicturesForVar(GameSettings.getPlayers_textureState(0), player1_leftArrow, player1_texture, player1_rightArrow);
                        setPlayersPicturesForVar(GameSettings.getPlayers_textureState(1), player2_leftArrow, player2_texture, player2_rightArrow);
                        setPlayersPicturesForVar(GameSettings.getPlayers_textureState(2), player3_leftArrow, player3_texture, player3_rightArrow);
                        setPlayersPicturesForVar(GameSettings.getPlayers_textureState(3), player4_leftArrow, player4_texture, player4_rightArrow);
                        break;
                }
                return true;
            }
        });



    }

    private static void setPlayersPicturesForVar(byte var, ImageButton leftArrowBtn, ImageView img, ImageButton rightArrowBtn) {
        if (var == 1)
            leftArrowBtn.setBackgroundResource(R.drawable.ic_btn_settings_left);
        else
            leftArrowBtn.setBackgroundResource(R.drawable.ic_btn_settings_leftactive);

        if (var == 4)
            rightArrowBtn.setBackgroundResource(R.drawable.ic_btn_settings_right);
        else
            rightArrowBtn.setBackgroundResource(R.drawable.ic_btn_settings_rightactive);

        switch (var) {
            case 1:
                img.setBackgroundResource(R.drawable.grnd_black);
                break;
            case 2:
                img.setBackgroundResource(R.drawable.grnd_grace);
                break;
            case 3:
                img.setBackgroundResource(R.drawable.grnd_lava);
                break;
            case 4:
                img.setBackgroundResource(R.drawable.grnd_sand);
                break;
        }
    }

    public void updateMusicButtons(ImageButton onButton, ImageButton offButton) {
        if (GameSettings.getMusicState()) {
            onButton.setBackgroundResource(R.drawable.ic_btn_settings_onactive);
            offButton.setBackgroundResource(R.drawable.ic_btn_settings_off);
        } else {
            onButton.setBackgroundResource(R.drawable.ic_btn_settings_on);
            offButton.setBackgroundResource(R.drawable.ic_btn_settings_offactive);
        }
    }

}
