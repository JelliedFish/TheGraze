package com.example.myapplication.CustomObjects;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.Data.GameSettings;
import com.example.myapplication.R;

public class SwitcherBoolean {



    private ImageButton onButton;
    private ImageButton offButton;
    private boolean value;

    public SwitcherBoolean(ImageButton newOnButton, ImageButton newOffButton, boolean primalValue) {
        this.onButton = newOnButton;
        this.offButton = newOffButton;
        this.value = primalValue;

        setImages(primalValue);

        onButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!value) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            onButton.setBackgroundResource(R.drawable.ic_btn_settings_onclicked);
                            break;
                        case MotionEvent.ACTION_UP:
                            value = true;
                            GameSettings.setMusicState(true);
                            setImages(true);
                            break;
                    }
                }
                return true;
            }
        });

        offButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (value) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            offButton.setBackgroundResource(R.drawable.ic_btn_settings_offclicked);
                            break;
                        case MotionEvent.ACTION_UP:
                            value = false;
                            GameSettings.setMusicState(false);
                            setImages(false);
                            break;
                    }
                }
                return true;
            }
        });
    }



    public void setImages(boolean value) {
        if (value) {
            onButton.setBackgroundResource(R.drawable.ic_btn_settings_onactive);
            offButton.setBackgroundResource(R.drawable.ic_btn_settings_off);
        } else {
            onButton.setBackgroundResource(R.drawable.ic_btn_settings_on);
            offButton.setBackgroundResource(R.drawable.ic_btn_settings_offactive);
        }
    }



}
