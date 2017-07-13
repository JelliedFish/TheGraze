package com.example.myapplication.Abstract;

import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.myapplication.Data.Const;
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

    public static void setNumImg(ImageView img, int num, byte color) {
        switch (color) {

            case Const.COLOR_GRAY:
                switch (num) {
                    case 1: img.setImageResource(R.drawable.ic_setgamefield_num_1); break;
                    case 2: img.setImageResource(R.drawable.ic_setgamefield_num_2); break;
                    case 3: img.setImageResource(R.drawable.ic_setgamefield_num_3); break;
                    case 4: img.setImageResource(R.drawable.ic_setgamefield_num_4); break;
                    case 5: img.setImageResource(R.drawable.ic_setgamefield_num_5); break;
                    case 6: img.setImageResource(R.drawable.ic_setgamefield_num_6); break;
                    case 7: img.setImageResource(R.drawable.ic_setgamefield_num_7); break;
                    case 8: img.setImageResource(R.drawable.ic_setgamefield_num_8); break;
                    case 9: img.setImageResource(R.drawable.ic_setgamefield_num_9); break;
                    case 10: img.setImageResource(R.drawable.ic_setgamefield_num_10); break;
                    case 11: img.setImageResource(R.drawable.ic_setgamefield_num_11); break;
                    case 12: img.setImageResource(R.drawable.ic_setgamefield_num_12); break;
                    case 13: img.setImageResource(R.drawable.ic_setgamefield_num_13); break;
                    case 14: img.setImageResource(R.drawable.ic_setgamefield_num_14); break;
                    case 15: img.setImageResource(R.drawable.ic_setgamefield_num_15); break;
                    case 16: img.setImageResource(R.drawable.ic_setgamefield_num_16); break;
                    case 17: img.setImageResource(R.drawable.ic_setgamefield_num_17); break;
                    case 18: img.setImageResource(R.drawable.ic_setgamefield_num_18); break;
                }
                break;

            case Const.COLOR_YELLOW:
                switch (num) {
                    case 1: img.setImageResource(R.drawable.ic_gameplay_num_1); break;
                    case 2: img.setImageResource(R.drawable.ic_gameplay_num_2); break;
                    case 3: img.setImageResource(R.drawable.ic_gameplay_num_3); break;
                    case 4: img.setImageResource(R.drawable.ic_gameplay_num_4); break;
                }
                break;

        }
    }

}
