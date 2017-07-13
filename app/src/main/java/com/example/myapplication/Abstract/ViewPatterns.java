package com.example.myapplication.Abstract;

import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.example.myapplication.R;

public abstract class ViewPatterns {

    public static void generateReturnButton(final ImageButton button, final AppCompatActivity activity) {
        button.setBackgroundResource(R.drawable.ic_options_help_return);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        button.setBackgroundResource(R.drawable.ic_options_help_returnclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        button.setBackgroundResource(R.drawable.ic_options_help_return);
                        activity.finish();
                        break;
                }
                return true;
            }
        });
    }

}
