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

import com.example.myapplication.CustomObjects.CustomAdapter;
import com.example.myapplication.CustomObjects.CustomButton;
import com.example.myapplication.Abstract.LayoutSetter;
import com.example.myapplication.Data.MapData;

import java.util.ArrayList;
import java.util.List;

public class SetGameField2 extends AppCompatActivity {

    // КОНСТАНТЫ

        private static final int[] MAPS_NUMBER = {8, 5, 8};                                         // количество карт в базе данных (для 2, 3 и 4 игроков соответственно)

    //

    static int teamsCount;
    static int currentMap;

    static MapData map;

    static int mapWidth;
    static int mapHeight;
    static int[] mapCastleCoords;
    static boolean[] mapButtonsData;


    static List<CustomButton> previewButtons;
    GridView preview_gridView;
    RelativeLayout previewLayout;
    RelativeLayout.LayoutParams layoutParams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setgamefield2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        final ImageButton btn_setgamefield2_to_main = (ImageButton) findViewById(R.id.setgamefield2_return);
        btn_setgamefield2_to_main.setBackgroundResource(R.drawable.ic_options_help_return);
        btn_setgamefield2_to_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_setgamefield2_to_main.setBackgroundResource(R.drawable.ic_options_help_returnclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_setgamefield2_to_main.setBackgroundResource(R.drawable.ic_options_help_return);
                        finish();
                        break;
                }
                return true;
            }
        });



        final ImageButton setgamefield2_teamscount_left = (ImageButton) findViewById(R.id.setgamefield2_teamscount_left);
        final ImageButton setgamefield2_teamscount_right = (ImageButton) findViewById(R.id.setgamefield2_teamscount_right);
        final ImageView setgamefield2_teamscount = (ImageView) findViewById(R.id.setgamefield2_teamscount);
        final ImageView setgamefield2_mapteamscount = (ImageView) findViewById(R.id.setgamefield2_mapteamscount);

        final ImageButton setgamefield2_map_arrow_left = (ImageButton) findViewById(R.id.setgamefield2_map_prev);
        final ImageButton setgamefield2_map_arrow_right = (ImageButton) findViewById(R.id.setgamefield2_map_next);
        final ImageView setgamefield2_map_number = (ImageView) findViewById(R.id.setgamefield2_mapnumber);

        teamsCount = 2;
        updateTeamsCountTextures(setgamefield2_teamscount_left, setgamefield2_teamscount, setgamefield2_teamscount_right, setgamefield2_mapteamscount);
        resetMap(setgamefield2_map_arrow_left, setgamefield2_map_number, setgamefield2_map_arrow_right);



        previewButtons = new ArrayList<>();
        preview_gridView = (GridView) findViewById(R.id.setgamefield2_preview);
        preview_gridView.setAdapter(new CustomAdapter(this, previewButtons));
        previewLayout = (RelativeLayout) findViewById(R.id.setgamefield2_previewLayout2);
        preview_gridView.setNumColumns(mapWidth);

        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        preview_gridView.setLayoutParams(layoutParams);


        updateMapPreview();



        setgamefield2_teamscount_left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (teamsCount > 2) {
                    teamsCount--;
                    updateTeamsCountTextures(setgamefield2_teamscount_left, setgamefield2_teamscount, setgamefield2_teamscount_right, setgamefield2_mapteamscount);
                    resetMap(setgamefield2_map_arrow_left, setgamefield2_map_number, setgamefield2_map_arrow_right);
                    updateMapPreview();
                }
            }
        });

        setgamefield2_teamscount_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (teamsCount < 4) {
                    teamsCount++;
                    updateTeamsCountTextures(setgamefield2_teamscount_left, setgamefield2_teamscount, setgamefield2_teamscount_right, setgamefield2_mapteamscount);
                    resetMap(setgamefield2_map_arrow_left, setgamefield2_map_number, setgamefield2_map_arrow_right);
                    updateMapPreview();
                }
            }
        });


        setgamefield2_map_arrow_left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentMap > 1) {
                    currentMap--;
                    updateMapDataAndTextures(setgamefield2_map_arrow_left, setgamefield2_map_number, setgamefield2_map_arrow_right);
                    updateMapPreview();
                }
            }
        });

        setgamefield2_map_arrow_right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (currentMap < MAPS_NUMBER[teamsCount - 2]) {
                    currentMap++;
                    updateMapDataAndTextures(setgamefield2_map_arrow_left, setgamefield2_map_number, setgamefield2_map_arrow_right);
                    updateMapPreview();
                }
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
                        sgf2_to_gameplay.putExtra("GAME_FIELD_KEY_TEAMSCOUNT", teamsCount);
                        sgf2_to_gameplay.putExtra("GAME_FIELD_KEY_CASTLESCOORDS", map.getCastleCoords());
                        sgf2_to_gameplay.putExtra("GAME_FIELD_KEY_BUTTONSDATA", map.getButtonsData());

                        startActivity(sgf2_to_gameplay);
                        break;
                }
                return true;
            }
        });



    }

    private static void updateTeamsCountTextures(ImageButton left, ImageView img, ImageButton right, ImageView numOnMap) {
        switch (teamsCount) {
            case 2:
                left.setBackgroundResource(R.drawable.ic_btn_settings_left);
                right.setBackgroundResource(R.drawable.ic_btn_settings_rightactive);
                break;
            case 3:
                left.setBackgroundResource(R.drawable.ic_btn_settings_leftactive);
                right.setBackgroundResource(R.drawable.ic_btn_settings_rightactive);
                break;
            case 4:
                left.setBackgroundResource(R.drawable.ic_btn_settings_leftactive);
                right.setBackgroundResource(R.drawable.ic_btn_settings_right);
                break;
        }
        img.setBackgroundResource(intToImage(teamsCount));
        numOnMap.setBackgroundResource(intToImage(teamsCount));
    }

    private static void updateMapDataAndTextures(ImageButton left, ImageView img, ImageButton right) {
        map = MapData.getMap(teamsCount, currentMap);
        mapWidth = map.getWidth();
        mapHeight = map.getHeight();
        mapCastleCoords = MapData.decryptCastleCoords(map.getCastleCoords());
        mapButtonsData = MapData.decryptButtonsData(map.getButtonsData());

        if (currentMap == 1)
            left.setBackgroundResource(R.drawable.ic_btn_settings_left);
        else
            left.setBackgroundResource(R.drawable.ic_btn_settings_leftactive);

        if (currentMap == MAPS_NUMBER[teamsCount - 2])
            right.setBackgroundResource(R.drawable.ic_btn_settings_right);
        else
            right.setBackgroundResource(R.drawable.ic_btn_settings_rightactive);

        img.setBackgroundResource(intToImage(currentMap));
    }

    private void updateMapPreview() {

        double multLeftRight = (double) getResources().getDisplayMetrics().widthPixels / 390;
        double multUpDown = (double) getResources().getDisplayMetrics().heightPixels / 390;

        previewButtons = new ArrayList<>();

        for (int i = 0; i < mapWidth * mapHeight; i++) {
            CustomButton tmp = new CustomButton(getBaseContext());
            if (mapButtonsData[i]) {
                Gameplay.updateDataAndTexture(tmp, 0, Gameplay.STATE_NEUTRAL);
            } else {
                Gameplay.updateDataAndTexture(tmp, 0, Gameplay.STATE_MISSING);
            }
            previewButtons.add(tmp);
        }

        for (int i = 0; i < teamsCount; i++) {
            Gameplay.updateDataAndTexture(previewButtons.get(mapCastleCoords[i]), i + 1, Gameplay.STATE_CASTLE);
        }

        preview_gridView.setAdapter(new CustomAdapter(this, previewButtons));
        preview_gridView.setNumColumns(mapWidth);

        int[] gridMargins = LayoutSetter.getMargins(225, 225, (double) mapWidth / mapHeight, 0, 0, 0, 0);
        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins((int) (gridMargins[0] * multLeftRight) + 30, (int) (gridMargins[1] * multUpDown) + 30, (int) (gridMargins[2] * multLeftRight) + 25, (int) (gridMargins[3] * multUpDown) + 25);
        previewLayout.setLayoutParams(layoutParams);
    }

    private static void resetMap(ImageButton left, ImageView img, ImageButton right) {
        currentMap = 1;
        updateMapDataAndTextures(left, img, right);
    }


    private static int intToImage(int arg) {
        switch (arg) {
            case 1:
                return R.drawable.ic_setgamefield_num_1;
            case 2:
                return R.drawable.ic_setgamefield_num_2;
            case 3:
                return R.drawable.ic_setgamefield_num_3;
            case 4:
                return R.drawable.ic_setgamefield_num_4;
            case 5:
                return R.drawable.ic_setgamefield_num_5;
            case 6:
                return R.drawable.ic_setgamefield_num_6;
            case 7:
                return R.drawable.ic_setgamefield_num_7;
            case 8:
                return R.drawable.ic_setgamefield_num_8;
            case 9:
                return R.drawable.ic_setgamefield_num_9;
        }
        return 239;
    }

}