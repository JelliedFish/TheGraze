package com.example.myapplication;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

public class Options extends AppCompatActivity {

    boolean musicState = false;
    byte diffState = 0;
    byte player1_textureState = 1;
    byte player2_textureState = 2;
    MediaPlayer mPlayer;
    Button stopButton ;
    Button startButton;
    Button pauseButton;
    SeekBar volumeControl;
    AudioManager audioManager;

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

        final ImageButton btn_diff_easy = (ImageButton) findViewById(R.id.options_btn_easy);
        if (diffState == 0)
            btn_diff_easy.setBackgroundResource(R.drawable.ic_settings_diff_easyselected);
        else
            btn_diff_easy.setBackgroundResource(R.drawable.ic_settings_diff_easy);

        final ImageButton btn_diff_medium = (ImageButton) findViewById(R.id.options_btn_medium);
        if (diffState == 1)
            btn_diff_medium.setBackgroundResource(R.drawable.ic_settings_diff_mediumselected);
        else
            btn_diff_medium.setBackgroundResource(R.drawable.ic_settings_diff_medium);

        final ImageButton btn_diff_hard = (ImageButton) findViewById(R.id.options_btn_hard);
        if (diffState == 2)
            btn_diff_hard.setBackgroundResource(R.drawable.ic_settings_diff_hardselected);
        else
            btn_diff_hard.setBackgroundResource(R.drawable.ic_settings_diff_hard);

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

        btn_diff_easy.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (diffState != 0) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            btn_diff_easy.setBackgroundResource(R.drawable.ic_settings_diff_easyclicked);
                            break;
                        case MotionEvent.ACTION_UP:
                            diffState = 0;
                            btn_diff_easy.setBackgroundResource(R.drawable.ic_settings_diff_easyselected);
                            btn_diff_medium.setBackgroundResource(R.drawable.ic_settings_diff_medium);
                            btn_diff_hard.setBackgroundResource(R.drawable.ic_settings_diff_hard);
                            break;
                    }
                }
                return true;
            }
        });

        btn_diff_medium.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (diffState != 1) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            btn_diff_medium.setBackgroundResource(R.drawable.ic_settings_diff_mediumclicked);
                            break;
                        case MotionEvent.ACTION_UP:
                            diffState = 1;
                            btn_diff_easy.setBackgroundResource(R.drawable.ic_settings_diff_easy);
                            btn_diff_medium.setBackgroundResource(R.drawable.ic_settings_diff_mediumselected);
                            btn_diff_hard.setBackgroundResource(R.drawable.ic_settings_diff_hard);
                            break;
                    }
                }
                return true;
            }
        });

        btn_diff_hard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (diffState != 2) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            btn_diff_hard.setBackgroundResource(R.drawable.ic_settings_diff_hardclicked);
                            break;
                        case MotionEvent.ACTION_UP:
                            diffState = 2;
                            btn_diff_easy.setBackgroundResource(R.drawable.ic_settings_diff_easy);
                            btn_diff_medium.setBackgroundResource(R.drawable.ic_settings_diff_medium);
                            btn_diff_hard.setBackgroundResource(R.drawable.ic_settings_diff_hardselected);
                            break;
                    }
                }
                return true;
            }
        });



        final ImageButton player1_leftArrow = (ImageButton) findViewById(R.id.options_player1_prev);
        final ImageView player1_texture = (ImageView) findViewById(R.id.options_player1_texture);
        final ImageButton player1_rightArrow = (ImageButton) findViewById(R.id.options_player1_next);

        final ImageButton player2_leftArrow = (ImageButton) findViewById(R.id.options_player2_prev);
        final ImageView player2_texture = (ImageView) findViewById(R.id.options_player2_texture);
        final ImageButton player2_rightArrow = (ImageButton) findViewById(R.id.options_player2_next);

        final ImageButton textures_swap = (ImageButton) findViewById(R.id.options_textures_swap);

        setPlayersPicturesForVar(player1_textureState, player1_leftArrow, player1_texture, player1_rightArrow);
        setPlayersPicturesForVar(player2_textureState, player2_leftArrow, player2_texture, player2_rightArrow);
        textures_swap.setBackgroundResource(R.drawable.ic_btn_settings_swap);


        player1_leftArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (player1_textureState > 1) {
                    if (player1_textureState - 1 == player2_textureState) {
                        player2_textureState++;
                        setPlayersPicturesForVar(player2_textureState, player2_leftArrow, player2_texture, player2_rightArrow);
                    }
                    player1_textureState--;
                    setPlayersPicturesForVar(player1_textureState, player1_leftArrow, player1_texture, player1_rightArrow);
                }
            }
        });

        player1_rightArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (player1_textureState < 4) {
                    if (player1_textureState + 1 == player2_textureState) {
                        player2_textureState--;
                        setPlayersPicturesForVar(player2_textureState, player2_leftArrow, player2_texture, player2_rightArrow);
                    }
                    player1_textureState++;
                    setPlayersPicturesForVar(player1_textureState, player1_leftArrow, player1_texture, player1_rightArrow);
                }
            }
        });

        player2_leftArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (player2_textureState > 1) {
                    if (player2_textureState - 1 == player1_textureState) {
                        player1_textureState++;
                        setPlayersPicturesForVar(player1_textureState, player1_leftArrow, player1_texture, player1_rightArrow);
                    }
                    player2_textureState--;
                    setPlayersPicturesForVar(player2_textureState, player2_leftArrow, player2_texture, player2_rightArrow);
                }
            }
        });

        player2_rightArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (player2_textureState < 4) {
                    if (player2_textureState + 1 == player1_textureState) {
                        player1_textureState--;
                        setPlayersPicturesForVar(player1_textureState, player1_leftArrow, player1_texture, player1_rightArrow);
                    }
                    player2_textureState++;
                    setPlayersPicturesForVar(player2_textureState, player2_leftArrow, player2_texture, player2_rightArrow);
                }
            }
        });

        textures_swap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                byte memory = player2_textureState;
                player2_textureState = player1_textureState;
                player1_textureState = memory;
                setPlayersPicturesForVar(player1_textureState, player1_leftArrow, player1_texture, player1_rightArrow);
                setPlayersPicturesForVar(player2_textureState, player2_leftArrow, player2_texture, player2_rightArrow);
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
                        diffState = 0;
                        btn_music_onoff.setBackgroundResource(R.drawable.ic_btn_settings_off);
                        btn_diff_easy.setBackgroundResource(R.drawable.ic_settings_diff_easyselected);
                        btn_diff_medium.setBackgroundResource(R.drawable.ic_settings_diff_medium);
                        btn_diff_hard.setBackgroundResource(R.drawable.ic_settings_diff_hard);
                        player1_textureState = 1;
                        player2_textureState = 2;
                        setPlayersPicturesForVar(player1_textureState, player1_leftArrow, player1_texture, player1_rightArrow);
                        setPlayersPicturesForVar(player2_textureState, player2_leftArrow, player2_texture, player2_rightArrow);
                        break;
                }
                return true;
            }
        });
        mPlayer=MediaPlayer.create(this, R.raw.big_russian_boss);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlay();
            }
        });
        stopButton = (Button) findViewById(R.id.options_btn_music_stop);
        startButton = (Button) findViewById(R.id.options_btn_music_start);
        pauseButton = (Button) findViewById(R.id.options_btn_music_pause);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curValue = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeControl = (SeekBar) findViewById(R.id.volumeControl);
        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(curValue);
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
    }
    private void stopPlay(){
        mPlayer.stop();
        pauseButton.setEnabled(false);
        stopButton.setEnabled(false);
        try {
            mPlayer.prepare();
            mPlayer.seekTo(0);
            startButton.setEnabled(true);
        }
        catch (Throwable t) {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void play(View view){

        mPlayer.start();
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        stopButton.setEnabled(true);
    }
    public void pause(View view){

        mPlayer.pause();
        startButton.setEnabled(true);
        pauseButton.setEnabled(false);
        stopButton.setEnabled(true);
    }
    public void stop(View view){
        stopPlay();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer.isPlaying()) {
            stopPlay();
        }
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
