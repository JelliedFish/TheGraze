package com.example.myapplication;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.myapplication.Abstract.ViewPatterns;
import com.example.myapplication.Data.GameSettings;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        ViewPatterns.generateReturnButton((ImageButton) findViewById(R.id.options_return), this);



        final ImageButton btn_music_on = (ImageButton) findViewById(R.id.options_btn_music_on);
        final ImageButton btn_music_off = (ImageButton) findViewById(R.id.options_btn_music_off);
        final ImageButton btn_reset = (ImageButton) findViewById(R.id.options_btn_reset);

        updateMusicButtons(btn_music_on, btn_music_off);
        btn_reset.setBackgroundResource(R.drawable.ic_settings_reset);



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



        //——————————//



        final ImageButton[] pt_leftArrows = new ImageButton[4];
        final ImageView[] pt_textures = new ImageView[4];
        final ImageButton[] pt_rightArrows = new ImageButton[4];


        for (int i = 0; i < 4; i++) {
            final int finalI = i;

            pt_leftArrows[i] = (ImageButton) findViewById(getResID_leftArrow(i));
            pt_textures[i] = (ImageView) findViewById(getResID_texture(i));
            pt_rightArrows[i] = (ImageButton) findViewById(getResID_rightArrow(i));

            updatePlayersSwitchers(i, pt_leftArrows[i], pt_textures[i], pt_rightArrows[i]);

            pt_leftArrows[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (GameSettings.getPlayers_textureState(finalI) > 1) {
                        GameSettings.addToTextureState(finalI, -1);
                        updatePlayersSwitchers(finalI, pt_leftArrows[finalI], pt_textures[finalI], pt_rightArrows[finalI]);
                    }
                }
            });

            pt_rightArrows[i].setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (GameSettings.getPlayers_textureState(finalI) < 4) {
                        GameSettings.addToTextureState(finalI, 1);
                        updatePlayersSwitchers(finalI, pt_leftArrows[finalI], pt_textures[finalI], pt_rightArrows[finalI]);
                    }
                }
            });

        }



        //——————————//



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
                        for (int i = 0; i < 4; i++) {
                            updatePlayersSwitchers(i, pt_leftArrows[i], pt_textures[i], pt_rightArrows[i]);
                        }

                        break;
                }
                return true;
            }
        });



    }


    private static int getResID_leftArrow(int index) {
        switch (index) {
            case 0:
                return R.id.options_player1_prev;
            case 1:
                return R.id.options_player2_prev;
            case 2:
                return R.id.options_player3_prev;
            case 3:
                return R.id.options_player4_prev;
        }
        return 0;
    }

    private static int getResID_texture(int index) {
        switch (index) {
            case 0:
                return R.id.options_player1_texture;
            case 1:
                return R.id.options_player2_texture;
            case 2:
                return R.id.options_player3_texture;
            case 3:
                return R.id.options_player4_texture;
        }
        return 0;
    }

    private static int getResID_rightArrow(int index) {
        switch (index) {
            case 0:
                return R.id.options_player1_next;
            case 1:
                return R.id.options_player2_next;
            case 2:
                return R.id.options_player3_next;
            case 3:
                return R.id.options_player4_next;
        }
        return 0;
    }


    private void updateMusicButtons(ImageButton onButton, ImageButton offButton) {
        if (GameSettings.getMusicState()) {
            onButton.setBackgroundResource(R.drawable.ic_btn_settings_onactive);
            offButton.setBackgroundResource(R.drawable.ic_btn_settings_off);
        } else {
            onButton.setBackgroundResource(R.drawable.ic_btn_settings_on);
            offButton.setBackgroundResource(R.drawable.ic_btn_settings_offactive);
        }
    }

    private static void updatePlayersSwitchers(int playerNum, ImageButton leftArrowBtn, ImageView img, ImageButton rightArrowBtn) {
        int textureState = GameSettings.getPlayers_textureState(playerNum);

        if (textureState == 1)
            leftArrowBtn.setBackgroundResource(R.drawable.ic_btn_settings_left);
        else
            leftArrowBtn.setBackgroundResource(R.drawable.ic_btn_settings_leftactive);

        if (textureState == 4)
            rightArrowBtn.setBackgroundResource(R.drawable.ic_btn_settings_right);
        else
            rightArrowBtn.setBackgroundResource(R.drawable.ic_btn_settings_rightactive);

        img.setBackgroundResource(GameSettings.getPlayerTextureID(playerNum + 1));
    }

}
