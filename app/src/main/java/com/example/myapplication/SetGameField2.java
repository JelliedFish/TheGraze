package com.example.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.myapplication.Abstract.ViewPatterns;
import com.example.myapplication.CustomObjects.CustomAdapter;
import com.example.myapplication.CustomObjects.CustomButton;
import com.example.myapplication.Abstract.LayoutSetter;
import com.example.myapplication.CustomObjects.IntSelector;
import com.example.myapplication.Data.Const;
import com.example.myapplication.Data.MapData;

import java.util.ArrayList;
import java.util.List;

public class SetGameField2 extends AppCompatActivity {

    // КОНСТАНТЫ

        private static final int[] MAPS_NUMBER = {8, 5, 8};                                         // количество карт в базе данных (для 2, 3 и 4 игроков соответственно)

    //

    IntSelector teamsCount = new IntSelector(2, 2, 4);
    IntSelector currentMap = new IntSelector(1, 1, MAPS_NUMBER[0]);

    static MapData map;

    static int mapWidth;
    static int mapHeight;
    static int[] mapCastleCoords;
    static boolean[] mapButtonsData;



    static List<CustomButton> previewButtons;
    GridView preview_gridView;
    RelativeLayout previewLayout;
    RelativeLayout.LayoutParams layoutParams;



    ImageButton setgamefield2_teamscount_left;
    ImageButton setgamefield2_teamscount_right;
    ImageView setgamefield2_teamscount;
    ImageView setgamefield2_mapteamscount;

    ImageButton setgamefield2_map_arrow_left;
    ImageButton setgamefield2_map_arrow_right;
    ImageButton setgamefield2_map_arrow_left_super;
    ImageButton setgamefield2_map_arrow_right_super;
    ImageView setgamefield2_map_number;



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setgamefield2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);



        ViewPatterns.generateReturnButton((ImageButton) findViewById(R.id.setgamefield2_return), this);



        setgamefield2_teamscount_left = (ImageButton) findViewById(R.id.setgamefield2_teamscount_left);
        setgamefield2_teamscount_right = (ImageButton) findViewById(R.id.setgamefield2_teamscount_right);
        setgamefield2_teamscount = (ImageView) findViewById(R.id.setgamefield2_teamscount);
        setgamefield2_mapteamscount = (ImageView) findViewById(R.id.setgamefield2_mapteamscount);

        setgamefield2_map_arrow_left = (ImageButton) findViewById(R.id.setgamefield2_map_prev);
        setgamefield2_map_arrow_right = (ImageButton) findViewById(R.id.setgamefield2_map_next);
        setgamefield2_map_arrow_left_super = (ImageButton) findViewById(R.id.setgamefield2_map_prev_super);
        setgamefield2_map_arrow_right_super = (ImageButton) findViewById(R.id.setgamefield2_map_next_super);
        setgamefield2_map_number = (ImageView) findViewById(R.id.setgamefield2_mapnumber);

        teamsCount.setVal(2);
        currentMap.setVal(1);
        updateTeamsCountTextures();
        resetMap();



        previewButtons = new ArrayList<>();
        preview_gridView = (GridView) findViewById(R.id.setgamefield2_preview);
        preview_gridView.setAdapter(new CustomAdapter(this, previewButtons));
        previewLayout = (RelativeLayout) findViewById(R.id.setgamefield2_previewLayout2);
        preview_gridView.setNumColumns(mapWidth);

        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        preview_gridView.setLayoutParams(layoutParams);


        updateMapPreview();



        setgamefield2_teamscount_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!teamsCount.isMin()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            setgamefield2_teamscount_left.setBackgroundResource(R.drawable.ic_btn_settings_leftclicked);
                            break;
                        case MotionEvent.ACTION_UP:
                            teamsCount.minus();
                            updateTeamsCountTextures();
                            resetMap();
                            updateMapPreview();
                            break;
                    }
                }
                return true;
            }
        });

        setgamefield2_teamscount_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!teamsCount.isMax()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            setgamefield2_teamscount_right.setBackgroundResource(R.drawable.ic_btn_settings_rightclicked);
                            break;
                        case MotionEvent.ACTION_UP:
                            teamsCount.plus();
                            updateTeamsCountTextures();
                            resetMap();
                            updateMapPreview();
                            break;
                    }
                }
                return true;
            }
        });

        setgamefield2_map_arrow_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!currentMap.isMin()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            setgamefield2_map_arrow_left.setBackgroundResource(R.drawable.ic_btn_settings_leftclicked_rect);
                            break;
                        case MotionEvent.ACTION_UP:
                            currentMap.minus();
                            updateMapDataAndTextures();
                            updateMapPreview();
                            break;
                    }
                }
                return true;
            }
        });

        setgamefield2_map_arrow_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!currentMap.isMax()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            setgamefield2_map_arrow_right.setBackgroundResource(R.drawable.ic_btn_settings_rightclicked_rect);
                            break;
                        case MotionEvent.ACTION_UP:
                            currentMap.plus();
                            updateMapDataAndTextures();
                            updateMapPreview();
                            break;
                    }
                }
                return true;
            }
        });

        setgamefield2_map_arrow_left_super.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!currentMap.isMin()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            setgamefield2_map_arrow_left_super.setBackgroundResource(R.drawable.ic_btn_settings_leftclicked_super);
                            break;
                        case MotionEvent.ACTION_UP:
                            currentMap.minus(5);
                            updateMapDataAndTextures();
                            updateMapPreview();
                            break;
                    }
                }
                return true;
            }
        });

        setgamefield2_map_arrow_right_super.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!currentMap.isMax()) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            setgamefield2_map_arrow_right_super.setBackgroundResource(R.drawable.ic_btn_settings_rightclicked_super);
                            break;
                        case MotionEvent.ACTION_UP:
                            currentMap.plus(5);
                            updateMapDataAndTextures();
                            updateMapPreview();
                            break;
                    }
                }
                return true;
            }
        });



        final ImageButton setgamefield2_play = (ImageButton) findViewById(R.id.setgamefield2_play);

        setgamefield2_play.setBackgroundResource(R.drawable.ic_setgamefield_play);

        setgamefield2_play.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setgamefield2_play.setBackgroundResource(R.drawable.ic_setgamefield_playclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        setgamefield2_play.setBackgroundResource(R.drawable.ic_setgamefield_play);

                        Intent sgf2_to_gameplay = new Intent(getBaseContext(), Gameplay.class);

                        sgf2_to_gameplay.putExtra("GAME_FIELD_KEY_WIDTH", mapWidth);
                        sgf2_to_gameplay.putExtra("GAME_FIELD_KEY_HEIGHT", mapHeight);
                        sgf2_to_gameplay.putExtra("GAME_FIELD_KEY_TEAMSCOUNT", teamsCount.getVal());
                        sgf2_to_gameplay.putExtra("GAME_FIELD_KEY_CASTLESCOORDS", map.getCastleCoords());
                        sgf2_to_gameplay.putExtra("GAME_FIELD_KEY_BUTTONSDATA", map.getButtonsData());

                        startActivity(sgf2_to_gameplay);
                        break;
                }
                return true;
            }
        });



    }

    private void updateTeamsCountTextures() {
        switch (teamsCount.getVal()) {
            case 2:
                setgamefield2_teamscount_left.setBackgroundResource(R.drawable.ic_btn_settings_left);
                setgamefield2_teamscount_right.setBackgroundResource(R.drawable.ic_btn_settings_rightactive);
                break;
            case 3:
                setgamefield2_teamscount_left.setBackgroundResource(R.drawable.ic_btn_settings_leftactive);
                setgamefield2_teamscount_right.setBackgroundResource(R.drawable.ic_btn_settings_rightactive);
                break;
            case 4:
                setgamefield2_teamscount_left.setBackgroundResource(R.drawable.ic_btn_settings_leftactive);
                setgamefield2_teamscount_right.setBackgroundResource(R.drawable.ic_btn_settings_right);
                break;
        }
        ViewPatterns.setNumImg(setgamefield2_teamscount, teamsCount.getVal(), Const.COLOR_GRAY);
        ViewPatterns.setNumImg(setgamefield2_mapteamscount, teamsCount.getVal(), Const.COLOR_GRAY);
    }

    private void updateMapDataAndTextures() {
        map = MapData.getMap(teamsCount.getVal(), currentMap.getVal());
        mapWidth = map.getWidth();
        mapHeight = map.getHeight();
        mapCastleCoords = MapData.decryptCastleCoords(map.getCastleCoords());
        mapButtonsData = MapData.decryptButtonsData(map.getButtonsData());

        if (currentMap.isMin()) {
            setgamefield2_map_arrow_left.setBackgroundResource(R.drawable.ic_btn_settings_left_rect);
            setgamefield2_map_arrow_left_super.setBackgroundResource(R.drawable.ic_btn_settings_left_super);
        } else {
            setgamefield2_map_arrow_left.setBackgroundResource(R.drawable.ic_btn_settings_leftactive_rect);
            setgamefield2_map_arrow_left_super.setBackgroundResource(R.drawable.ic_btn_settings_leftactive_super);
        }

        if (currentMap.isMax()) {
            setgamefield2_map_arrow_right.setBackgroundResource(R.drawable.ic_btn_settings_right_rect);
            setgamefield2_map_arrow_right_super.setBackgroundResource(R.drawable.ic_btn_settings_right_super);
        } else {
            setgamefield2_map_arrow_right.setBackgroundResource(R.drawable.ic_btn_settings_rightactive_rect);
            setgamefield2_map_arrow_right_super.setBackgroundResource(R.drawable.ic_btn_settings_rightactive_super);
        }

        ViewPatterns.setNumImg(setgamefield2_map_number, currentMap.getVal(), Const.COLOR_GRAY);
    }

    private void updateMapPreview() {

        double multLeftRight = (double) getResources().getDisplayMetrics().widthPixels / 390;
        double multUpDown = (double) getResources().getDisplayMetrics().heightPixels / 390;

        previewButtons = new ArrayList<>();

        for (int i = 0; i < mapWidth * mapHeight; i++) {
            CustomButton tmp = new CustomButton(getBaseContext());
            if (mapButtonsData[i]) {
                Gameplay.updateDataAndTexture(tmp, 0, Const.STATE_NEUTRAL);
            } else {
                Gameplay.updateDataAndTexture(tmp, 0, Const.STATE_MISSING);
            }
            previewButtons.add(tmp);
        }

        for (int i = 0; i < teamsCount.getVal(); i++) {
            Gameplay.updateDataAndTexture(previewButtons.get(mapCastleCoords[i]), i + 1, Const.STATE_CASTLE);
        }

        preview_gridView.setAdapter(new CustomAdapter(this, previewButtons));
        preview_gridView.setNumColumns(mapWidth);

        int[] gridMargins = LayoutSetter.getMargins(225, 225, (double) mapWidth / mapHeight, 0, 0, 0, 0);
        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins((int) (gridMargins[0] * multLeftRight) + 30, (int) (gridMargins[1] * multUpDown) + 30, (int) (gridMargins[2] * multLeftRight) + 25, (int) (gridMargins[3] * multUpDown) + 25);
        previewLayout.setLayoutParams(layoutParams);
    }

    private void resetMap() {
        currentMap = new IntSelector(1, 1, MAPS_NUMBER[teamsCount.getVal() - 2]);
        updateMapDataAndTextures();
    }

}