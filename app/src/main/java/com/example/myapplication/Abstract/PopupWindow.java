package com.example.myapplication.Abstract;

import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.List;

public abstract class PopupWindow {

    public static void display(RelativeLayout popupLayout, List<ImageButton> mainLayoutButtons) {
        popupLayout.setVisibility(RelativeLayout.VISIBLE);
        for (ImageButton button : mainLayoutButtons)
            button.setEnabled(false);
    }

    public static void hide(RelativeLayout popupLayout, List<ImageButton> mainLayoutButtons) {
        popupLayout.setVisibility(RelativeLayout.INVISIBLE);
        for (ImageButton button : mainLayoutButtons)
            button.setEnabled(true);
    }

}
