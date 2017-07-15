package com.example.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.myapplication.Abstract.PopupWindow;
import com.example.myapplication.Data.GameSettings;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends AppCompatActivity {

    public static ImageView options_warning;

    public void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        final List<ImageButton> mainMenuButtons = new ArrayList<>();



        //—————ВСПЛЫВАЮЩЕЕ ОКНО О ЗАКРЫТИИ ИГРЫ—————//



        final RelativeLayout popup_exit = (RelativeLayout) findViewById(R.id.mm_popup_exit);



        final ImageButton popup_exit_yes = (ImageButton) findViewById(R.id.mm_popup_exit_yes);
        final ImageButton popup_exit_no = (ImageButton) findViewById(R.id.mm_popup_exit_no);

        popup_exit_yes.setBackgroundResource(R.drawable.ic_popup_exit_yes);
        popup_exit_no.setBackgroundResource(R.drawable.ic_popup_exit_no);



        popup_exit_yes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        popup_exit_yes.setBackgroundResource(R.drawable.ic_popup_exit_yesclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        popup_exit_yes.setBackgroundResource(R.drawable.ic_popup_exit_yes);
                        PopupWindow.hide(popup_exit, mainMenuButtons);
                        AppExit();
                        break;
                }
                return true;
            }
        });

        popup_exit_no.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        popup_exit_no.setBackgroundResource(R.drawable.ic_popup_exit_noclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        popup_exit_no.setBackgroundResource(R.drawable.ic_popup_exit_no);
                        PopupWindow.hide(popup_exit, mainMenuButtons);
                        break;
                }
                return true;
            }
        });



        //—————ВСПЛЫВАЮЩЕЕ ОКНО О СОХРАНЁННОЙ ИГРЕ [1]—————//



        final RelativeLayout popup_reserved_exist = (RelativeLayout) findViewById(R.id.mm_popup_reserved_exist);



        final ImageButton popup_reserved_exist_yes = (ImageButton) findViewById(R.id.mm_popup_reserved_exist_yes);
        final ImageButton popup_reserved_exist_no = (ImageButton) findViewById(R.id.mm_popup_reserved_exist_no);

        popup_reserved_exist_yes.setBackgroundResource(R.drawable.ic_popup_exit_yes);
        popup_reserved_exist_no.setBackgroundResource(R.drawable.ic_popup_exit_no);



        popup_reserved_exist_yes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        popup_reserved_exist_yes.setBackgroundResource(R.drawable.ic_popup_exit_yesclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        popup_reserved_exist_yes.setBackgroundResource(R.drawable.ic_popup_exit_yes);
                        PopupWindow.hide(popup_reserved_exist, mainMenuButtons);
                        break;
                }
                return true;
            }
        });

        popup_reserved_exist_no.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        popup_reserved_exist_no.setBackgroundResource(R.drawable.ic_popup_exit_noclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        popup_reserved_exist_no.setBackgroundResource(R.drawable.ic_popup_exit_no);
                        PopupWindow.hide(popup_reserved_exist, mainMenuButtons);
                        break;
                }
                return true;
            }
        });



        //—————ВСПЛЫВАЮЩЕЕ ОКНО О СОХРАНЁННОЙ ИГРЕ [2]—————//



        final RelativeLayout popup_reserved_missing = (RelativeLayout) findViewById(R.id.mm_popup_reserved_missing);



        final ImageButton popup_reserved_missing_ok = (ImageButton) findViewById(R.id.mm_popup_reserved_missing_ok);

        popup_reserved_missing_ok.setBackgroundResource(R.drawable.ic_popup_gameplay_ok);



        popup_reserved_missing_ok.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        popup_reserved_missing_ok.setBackgroundResource(R.drawable.ic_popup_gameplay_okclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        popup_reserved_missing_ok.setBackgroundResource(R.drawable.ic_popup_gameplay_ok);
                        PopupWindow.hide(popup_reserved_missing, mainMenuButtons);
                        break;
                }
                return true;
            }
        });



        //—————ГЛАВНОЕ МЕНЮ—————//



        final ImageButton btn_play = (ImageButton) findViewById(R.id.btn_play);
        final ImageButton btn_options = (ImageButton) findViewById(R.id.btn_options);
        final ImageButton btn_help = (ImageButton) findViewById(R.id.btn_help);
        final ImageButton btn_exit = (ImageButton) findViewById(R.id.btn_exit);
    //    final ImageButton btn_mapeditor = (ImageButton) findViewById(R.id.btn_mapeditor);
        final ImageButton btn_reserved = (ImageButton) findViewById(R.id.btn_reserved);
        options_warning = (ImageView) findViewById(R.id.options_warning);

        mainMenuButtons.add(btn_play);
        mainMenuButtons.add(btn_options);
        mainMenuButtons.add(btn_help);
        mainMenuButtons.add(btn_exit);
    //    mainMenuButtons.add(btn_mapeditor);
        mainMenuButtons.add(btn_reserved);

        btn_play.setBackgroundResource(R.drawable.ic_btn_mm_play);
        btn_options.setBackgroundResource(R.drawable.ic_btn_mm_options);
        btn_help.setBackgroundResource(R.drawable.ic_btn_mm_help);
        btn_exit.setBackgroundResource(R.drawable.ic_btn_mm_exit);
    //    btn_mapeditor.setBackgroundResource(R.drawable.ic_btn_mm_mapeditor);
        btn_reserved.setBackgroundResource((GameSettings.isReservedGameExist())? R.drawable.ic_mm_reserved_active : R.drawable.ic_mm_reserved );
        options_warning.setBackgroundResource((GameSettings.texturesListMatch()[0])? R.drawable.ic_mm_warning : R.drawable.ic_alpha );



        btn_play.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_play.setBackgroundResource(R.drawable.ic_btn_mm_playclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_play.setBackgroundResource(R.drawable.ic_btn_mm_play);
                        Intent main_to_sgft = new Intent(getBaseContext(), SetGameFieldType.class);
                        main_to_sgft.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(main_to_sgft);
                        break;
                }
                return true;
            }
        });

        btn_options.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_options.setBackgroundResource(R.drawable.ic_btn_mm_optionsclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_options.setBackgroundResource(R.drawable.ic_btn_mm_options);
                        Intent main_to_options = new Intent(getBaseContext(), Options.class);
                        startActivity(main_to_options);
                        break;
                }
                return true;
            }
        });

        btn_help.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_help.setBackgroundResource(R.drawable.ic_btn_mm_helpclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_help.setBackgroundResource(R.drawable.ic_btn_mm_help);
                        Intent main_to_help = new Intent(getBaseContext(), Help.class);
                        startActivity(main_to_help);
                        break;
                }
                return true;
            }
        });

        btn_exit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_exit.setBackgroundResource(R.drawable.ic_btn_mm_exitclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_exit.setBackgroundResource(R.drawable.ic_btn_mm_exit);
                        PopupWindow.display(popup_exit, mainMenuButtons);
                        break;
                }
                return true;
            }
        });

/*        btn_mapeditor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_mapeditor.setBackgroundResource(R.drawable.ic_btn_mm_mapeditorclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_mapeditor.setBackgroundResource(R.drawable.ic_btn_mm_mapeditor);
                        break;
                }
                return true;
            }
        });*/

        btn_reserved.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (GameSettings.isReservedGameExist()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            btn_reserved.setBackgroundResource(R.drawable.ic_mm_reserved_active_clicked);
                            break;
                        case MotionEvent.ACTION_UP:
                            btn_reserved.setBackgroundResource(R.drawable.ic_mm_reserved_active);
                            PopupWindow.display(popup_reserved_exist, mainMenuButtons);
                            break;
                    }
                } else {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            btn_reserved.setBackgroundResource(R.drawable.ic_mm_reserved_clicked);
                            break;
                        case MotionEvent.ACTION_UP:
                            btn_reserved.setBackgroundResource(R.drawable.ic_mm_reserved);
                            PopupWindow.display(popup_reserved_missing, mainMenuButtons);
                            break;
                    }
                }

                return true;
            }
        });



    }



    public void AppExit() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}