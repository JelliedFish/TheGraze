package com.example.myapplication;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Options extends AppCompatActivity {

    static boolean musicState = false;
    static byte[] players_textureState = {1, 2, 3, 4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        ImageButton btn_options_to_main = (ImageButton) findViewById(R.id.options_return);
        btn_options_to_main.setBackgroundResource(R.drawable.ic_options_help_return);
        btn_options_to_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        final ImageButton btn_music_onoff = (ImageButton) findViewById(R.id.options_btn_music_onoff);
        if (musicState)
            btn_music_onoff.setBackgroundResource(R.drawable.ic_btn_settings_on);
        else
            btn_music_onoff.setBackgroundResource(R.drawable.ic_btn_settings_off);

        final ImageButton btn_reset = (ImageButton) findViewById(R.id.options_btn_reset);
        btn_reset.setBackgroundResource(R.drawable.ic_settings_reset);


        btn_music_onoff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (musicState) {
                    musicState = false;
                    btn_music_onoff.setBackgroundResource(R.drawable.ic_btn_settings_off);
                } else {
                    musicState = true;
                    btn_music_onoff.setBackgroundResource(R.drawable.ic_btn_settings_on);
                }
            }
        });


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

        setPlayersPicturesForVar(players_textureState[0], player1_leftArrow, player1_texture, player1_rightArrow);
        setPlayersPicturesForVar(players_textureState[1], player2_leftArrow, player2_texture, player2_rightArrow);
        setPlayersPicturesForVar(players_textureState[2], player3_leftArrow, player3_texture, player3_rightArrow);
        setPlayersPicturesForVar(players_textureState[3], player4_leftArrow, player4_texture, player4_rightArrow);


        player1_leftArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (players_textureState[0] > 1) {
                    Gameplay.players_textures[0]--;
                    players_textureState[0]--;
                    setPlayersPicturesForVar(players_textureState[0], player1_leftArrow, player1_texture, player1_rightArrow);
                }
            }
        });

        player1_rightArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (players_textureState[0] < 4) {
                    Gameplay.players_textures[0]++;
                    players_textureState[0]++;
                    setPlayersPicturesForVar(players_textureState[0], player1_leftArrow, player1_texture, player1_rightArrow);
                }
            }
        });

        player2_leftArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (players_textureState[1] > 1) {
                    Gameplay.players_textures[1]--;
                    players_textureState[1]--;
                    setPlayersPicturesForVar(players_textureState[1], player2_leftArrow, player2_texture, player2_rightArrow);
                }
            }
        });

        player2_rightArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (players_textureState[1] < 4) {
                    Gameplay.players_textures[1]++;
                    players_textureState[1]++;
                    setPlayersPicturesForVar(players_textureState[1], player2_leftArrow, player2_texture, player2_rightArrow);
                }
            }
        });

        player3_leftArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (players_textureState[2] > 1) {
                    Gameplay.players_textures[2]--;
                    players_textureState[2]--;
                    setPlayersPicturesForVar(players_textureState[2], player3_leftArrow, player3_texture, player3_rightArrow);
                }
            }
        });

        player3_rightArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (players_textureState[2] < 4) {
                    Gameplay.players_textures[2]++;
                    players_textureState[2]++;
                    setPlayersPicturesForVar(players_textureState[2], player3_leftArrow, player3_texture, player3_rightArrow);
                }
            }
        });

        player4_leftArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (players_textureState[3] > 1) {
                    Gameplay.players_textures[3]--;
                    players_textureState[3]--;
                    setPlayersPicturesForVar(players_textureState[3], player4_leftArrow, player4_texture, player4_rightArrow);
                }
            }
        });

        player4_rightArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (players_textureState[3] < 4) {
                    Gameplay.players_textures[3]++;
                    players_textureState[3]++;
                    setPlayersPicturesForVar(players_textureState[3], player4_leftArrow, player4_texture, player4_rightArrow);
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

                        musicState = false;
                        btn_music_onoff.setBackgroundResource(R.drawable.ic_btn_settings_off);

                        players_textureState[0] = 1;
                        players_textureState[1] = 2;
                        players_textureState[2] = 3;
                        players_textureState[3] = 4;
                        setPlayersPicturesForVar(players_textureState[0], player1_leftArrow, player1_texture, player1_rightArrow);
                        setPlayersPicturesForVar(players_textureState[1], player2_leftArrow, player2_texture, player2_rightArrow);
                        setPlayersPicturesForVar(players_textureState[2], player3_leftArrow, player3_texture, player3_rightArrow);
                        setPlayersPicturesForVar(players_textureState[3], player4_leftArrow, player4_texture, player4_rightArrow);
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

}
