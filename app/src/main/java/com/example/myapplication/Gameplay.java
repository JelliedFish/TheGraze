package com.example.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.myapplication.CustomObjects.CustomAdapter;
import com.example.myapplication.CustomObjects.CustomButton;
import com.example.myapplication.Abstract.LayoutSetter;
import com.example.myapplication.Data.GameSettings;
import com.example.myapplication.Data.MapData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

public class Gameplay extends AppCompatActivity {

    // КОНСТАНТЫ

        static final byte STATE_MISSING = -1;
        static final byte STATE_NEUTRAL = 0;
        static final byte STATE_ALIVE = 1;
        static final byte STATE_KILL = 2;
        static final byte STATE_CASTLE = 3;
        static final byte STATE_CASTLEKILLED = 4;

    // ДАННЫЕ, ПЕРЕДАВАЕМЫЕ ИЗ ПРЕДЫДУЩИХ ACTIVITY

        int Width;
        int Height;
        int teamsCount;                                                                             // количество команд в игре
        int[] castleCoords;                                                                         // координаты (в массиве fields, а не двумерном массиве buttons!) замков соответствующих 4 команд
        boolean[] buttonsData;                                                                      // здесь в виде массива T/F хранятся данные о наличии/отсутствии каждой из клеток поля.
                                                                                                    // проще говоря, buttonsData задаёт форму для непрямоугольного поля.
    //


    static int step;                                                                                // счётчик шагов (сейчас работает, но нигде не применяется)
    static int currentTeam;                                                                         // команда, которая сейчас ходит
    static int stepsLeft;                                                                           // сколько ходов осталось этой команде

    CustomButton[][] buttons = new CustomButton[Width][Height];


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);                         // фиксируем вертикальную ориентацию окна


        Intent sgf_to_gameplay = getIntent();                                                       //
        Width = sgf_to_gameplay.getIntExtra("GAME_FIELD_KEY_WIDTH", 8);                             // передача данных о карте из предыдущих activity
        Height = sgf_to_gameplay.getIntExtra("GAME_FIELD_KEY_HEIGHT", 12);                          //
        teamsCount = sgf_to_gameplay.getIntExtra("GAME_FIELD_KEY_TEAMSCOUNT", 2);                   //
        castleCoords = MapData.decryptCastleCoords(sgf_to_gameplay.getStringExtra("GAME_FIELD_KEY_CASTLESCOORDS"));
        buttonsData = MapData.decryptButtonsData(sgf_to_gameplay.getStringExtra("GAME_FIELD_KEY_BUTTONSDATA"));


        buttons = new CustomButton[Width][Height];


        final List<CustomButton> fields = new ArrayList<>();                                        // массив всех кнопок

        for (int i = 0; i < Height * Width; i++) {                                                  //
            CustomButton tmp = new CustomButton(getBaseContext());                                  //
            if (buttonsData[i])                                                                     // заполнение поля клетками в соответствии с данными об их
                updateDataAndTexture(tmp, 0, STATE_NEUTRAL);                                        // существовании (buttonData[])
            else                                                                                    //
                updateDataAndTexture(tmp, 0, STATE_MISSING);                                        //
            tmp.setCheckable(true);                                                                 //
            fields.add(tmp);                                                                        //
        }

        for (int i = 0; i < teamsCount; i++) {                                                      // ставим текстуры и координаты баз
            updateDataAndTexture(fields.get(castleCoords[i]), i + 1, STATE_CASTLE);
        }


        int l = 0;
        for (int i = 0; i < Height; i++) {                                                          //
            for (int j = 0; j < Width; j++) {                                                       //
                buttons[j][i] = fields.get(l);                                                      // связываем наш массив кнопок с двумерным массивом кнопок
                l++;                                                                                //
            }                                                                                       //
        }                                                                                           //



        GridView gridView = (GridView) findViewById(R.id.gridView);                                 // создаем гридвью
        gridView.setAdapter(new CustomAdapter(this, fields));                                       // связываем гридвью и адаптер
        gridView.setNumColumns(Width);                                                              // установка кол-ва колонок для гридвью


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();                         // фича, необходимая для получения высоты и ширины экрана в пикселях:
                                                                                                    // displayMetrics.widthPixels;
                                                                                                    // displayMetrics.heightPixels;

        LinearLayout wrapperView = (LinearLayout) findViewById(R.id.wrapper);                       // устанавливаем разметчик для activity
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        int[] gridMargins = LayoutSetter.getRelativeMargins(displayMetrics.widthPixels, displayMetrics.heightPixels, (double) Width / Height, 0.05, 0.12, 0.05, 0.03);
        // рассчитывает отступы для игровой сетки (см. класс LayoutSetter)

        layoutParams.setMargins(gridMargins[0], gridMargins[1], gridMargins[2], gridMargins[3]);
        wrapperView.setLayoutParams(layoutParams);                                                  // устанавливаем полученные отступы в layout



        step = 0;
        currentTeam = 1;
        stepsLeft = 3;


        final ImageView title_teamNum = (ImageView) findViewById(R.id.gameplay_team);
        final ImageView title_teamIcon = (ImageView) findViewById(R.id.gameplay_playingicon);
        final ImageView title_stepsLeft = (ImageView) findViewById(R.id.gameplay_steps);

        updateTeamInTitle(title_teamNum, title_teamIcon);
        updateStepsInTitle(title_stepsLeft);


        for (int position = 0; position < Height * Width; position++) {                             // обрабатываем каждую кнопку

            final CustomButton tmp = fields.get(position);
            final int x = position % Width;                                                         // находим месторасположение кнопки в двумерном массиве (и на поле
            final int y = position / Width;                                                         // соотвественно), задаем эти значения переменным


            tmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {                                                         // добавляем для каждой кнопки слушатель действия

                    if (tmp.getState() != STATE_MISSING && tmp.getState() != STATE_KILL && tmp.getState() != STATE_CASTLEKILLED) {
                                                                                                    // если мы не нажали на "мертвую" или отсутствующую клетку, то
                        if (ReasonsToPut(x, y, currentTeam)) {
                            if (tmp.getTeam() != currentTeam) {
                                switch (tmp.getState()) {
                                    case STATE_ALIVE: {
                                        updateDataAndTexture(tmp, currentTeam, STATE_KILL);
                                        minusStep(title_teamNum, title_teamIcon, title_stepsLeft);
                                    }
                                    break;
                                    case STATE_CASTLE: {
                                        updateDataAndTexture(tmp, null, STATE_CASTLEKILLED);
                                        minusStep(title_teamNum, title_teamIcon, title_stepsLeft);
                                    }
                                    break;
                                    case STATE_NEUTRAL: {
                                        updateDataAndTexture(tmp, currentTeam, STATE_ALIVE);
                                        minusStep(title_teamNum, title_teamIcon, title_stepsLeft);
                                    }
                                    break;
                                }
                            }
                        }
                        else if (ReasonsToEat(x, y, currentTeam) && tmp.getTeam() != currentTeam) {
                            switch (tmp.getState()) {
                                case STATE_ALIVE: {
                                    updateDataAndTexture(tmp, currentTeam, STATE_KILL);
                                    minusStep(title_teamNum, title_teamIcon, title_stepsLeft);
                                }
                                break;
                                case STATE_CASTLE: {
                                    updateDataAndTexture(tmp, null, STATE_CASTLEKILLED);
                                    minusStep(title_teamNum, title_teamIcon, title_stepsLeft);
                                }
                                break;
                            }
                        }
                    }

                    clear();                                                                        // обнуление флагов для проверки клеток с кучкой мёртвых клопов
                }
            });
        }


        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.sidemenu);
        menu.setBehindWidthRes(R.dimen.slidingmenu_behind_width);

        final ImageButton btn_sliding_menu_return_to_main_menu = (ImageButton) findViewById(R.id.sliding_menu_btn_main_menu);
        btn_sliding_menu_return_to_main_menu.setBackgroundResource(R.drawable.ic_btn_sliding_menu_return_to_main_menu);
        btn_sliding_menu_return_to_main_menu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_sliding_menu_return_to_main_menu.setBackgroundResource(R.drawable.ic_btn_sliding_menu_return_to_main_menu_onclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_sliding_menu_return_to_main_menu.setBackgroundResource(R.drawable.ic_btn_sliding_menu_return_to_main_menu);
                        Intent sliding_menu_to_main_menu = new Intent(getBaseContext(), MainMenu.class);
                        startActivity(sliding_menu_to_main_menu);
                        break;
                }
                return true;
            }
        });

        final ImageButton btn_sliding_menu_return_to_options = (ImageButton) findViewById(R.id.sliding_menu_btn_options);
        btn_sliding_menu_return_to_options.setBackgroundResource(R.drawable.ic_btn_sliding_menu_return_to_options);
        btn_sliding_menu_return_to_options.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_sliding_menu_return_to_options.setBackgroundResource(R.drawable.ic_btn_sliding_menu_return_to_options_onclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_sliding_menu_return_to_options.setBackgroundResource(R.drawable.ic_btn_sliding_menu_return_to_options);
                        Intent sliding_menu_to_options = new Intent(getBaseContext(), Options.class);
                        startActivity(sliding_menu_to_options);
                        break;
                }
                return true;
            }
        });

        final ImageButton btn_sliding_menu_return_to_help = (ImageButton) findViewById(R.id.sliding_menu_btn_help);
        btn_sliding_menu_return_to_help.setBackgroundResource(R.drawable.ic_btn_sliding_menu_return_to_help);
        btn_sliding_menu_return_to_help.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        btn_sliding_menu_return_to_help.setBackgroundResource(R.drawable.ic_btn_sliding_menu_return_to_helponclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        btn_sliding_menu_return_to_help.setBackgroundResource(R.drawable.ic_btn_sliding_menu_return_to_help);
                        Intent sliding_menu_to_help = new Intent(getBaseContext(), Help.class);
                        startActivity(sliding_menu_to_help);
                        break;
                }
                return true;
            }
        });
    }


    /*
     * ФУНКЦИИ
     */


    private boolean isAliveUnitThere(int x, int y, int team) {                                      // вспомогательный метод для ReasonsToPut
        return ((buttons[x][y].getState() == STATE_ALIVE) || (buttons[x][y].getState() == STATE_CASTLE)) && buttons[x][y].getTeam() == team;
    }

    private boolean isKilledUnitThere(int x, int y, int team) {                                     // вспомогательный метод для ReasonsToEat и existEatenNear
        return (buttons[x][y].getState() == STATE_KILL) && (buttons[x][y].getTeam() == team);
    }


    private boolean ReasonsToPut(int x, int y, int team) {                                          // проверка постановки клопа
        boolean b = false;

        try {
            b = (isAliveUnitThere(x - 1, y - 1, team));
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b |= (isAliveUnitThere(x - 1, y, team));
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b |= (isAliveUnitThere(x - 1, y + 1, team));
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b |= (isAliveUnitThere(x, y - 1, team));
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b |= (isAliveUnitThere(x, y + 1, team));
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b |= (isAliveUnitThere(x + 1, y - 1, team));
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b |= (isAliveUnitThere(x + 1, y, team));
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b |= (isAliveUnitThere(x + 1, y + 1, team));
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }

        return b;
    }

    private boolean ReasonsToEat(int x, int y, int team) {                                          // проверка съедания клопа
        boolean b = false;

        try {
            b = (isKilledUnitThere(x - 1, y - 1, team) && checkActivity(x - 1, y - 1, team));
        } catch (IndexOutOfBoundsException ignored) {}
        try {
            b |= (isKilledUnitThere(x - 1, y, team) && checkActivity(x - 1, y, team));
        } catch (IndexOutOfBoundsException ignored) {}
        try {
            b |= (isKilledUnitThere(x - 1, y + 1, team) && checkActivity(x - 1, y + 1, team));
        } catch (IndexOutOfBoundsException ignored) {}
        try {
            b |= (isKilledUnitThere(x, y - 1, team) && checkActivity(x, y - 1, team));
        } catch (IndexOutOfBoundsException ignored) {}
        try {
            b |= (isKilledUnitThere(x, y + 1, team) && checkActivity(x, y + 1, team));
        } catch (IndexOutOfBoundsException ignored) {}
        try {
            b |= (isKilledUnitThere(x + 1, y - 1, team) && checkActivity(x + 1, y - 1, team));
        } catch (IndexOutOfBoundsException ignored) {}
        try {
            b |= (isKilledUnitThere(x + 1, y, team) && checkActivity(x + 1, y, team));
        } catch (IndexOutOfBoundsException ignored) {}
        try {
            b |= (isKilledUnitThere(x + 1, y + 1, team) && checkActivity(x + 1, y + 1, team));
        } catch (IndexOutOfBoundsException ignored) {}

        return b;
    }

    private boolean checkActivity(int x, int y, int team) {                                         // проверка активности мертвой кучи клопов
        boolean result = false;

        CustomButton tmp = buttons[x][y];
        tmp.setCheckable(false);

        if (ReasonsToPut(x, y, team))
            result = true;
        else {
            try {
                if (isKilledUnitThere(x + 1, y - 1, team) && (buttons[x + 1][y - 1].getCheckable())) {
                    result = checkActivity(x + 1, y - 1, team);
                }
            } catch (IndexOutOfBoundsException ignored) {}
            try {
                if (isKilledUnitThere(x + 1, y, team) && (buttons[x + 1][y].getCheckable())) {
                    result |= checkActivity(x + 1, y, team);
                }
            } catch (IndexOutOfBoundsException ignored) {}
            try {
                if (isKilledUnitThere(x + 1, y + 1, team) && (buttons[x + 1][y + 1].getCheckable())) {
                    result |= checkActivity(x + 1, y + 1, team);
                }
            } catch (IndexOutOfBoundsException ignored) {}
            try {
                if (isKilledUnitThere(x, y - 1, team) && (buttons[x][y - 1].getCheckable())) {
                    result |= checkActivity(x, y - 1, team);
                }
            } catch (IndexOutOfBoundsException ignored) {}
            try {
                if (isKilledUnitThere(x, y + 1, team) && (buttons[x][y + 1].getCheckable())) {
                    result |= checkActivity(x, y + 1, team);
                }
            } catch (IndexOutOfBoundsException ignored) {}
            try {
                if (isKilledUnitThere(x - 1, y - 1, team) && (buttons[x - 1][y - 1].getCheckable())) {
                    result |= checkActivity(x - 1, y - 1, team);
                }
            } catch (IndexOutOfBoundsException ignored) {}
            try {
                if (isKilledUnitThere(x - 1, y, team) && (buttons[x - 1][y].getCheckable())) {
                    result |= checkActivity(x - 1, y, team);
                }
            } catch (IndexOutOfBoundsException ignored) {}
            try {
                if (isKilledUnitThere(x - 1, y + 1, team) && (buttons[x - 1][y + 1].getCheckable())) {
                    result |= checkActivity(x - 1, y + 1, team);
                }
            } catch (IndexOutOfBoundsException ignored) {}
        }

        return result;
    }


    private void clear() {                                                                          // обнуление флагов клеток
        for (int k1 = 0; k1 < Width; k1++) {
            for (int k2 = 0; k2 < Height; k2++) {
                buttons[k1][k2].setCheckable(true);
            }
        }
    }

    private void minusStep(ImageView teamNumImg, ImageView teamIcon, ImageView stepsImg) {                                                                      // процедура отнятия шага; выполняется при каждом успешном действии
        stepsLeft--;
        if (stepsLeft == 0) {
            stepsLeft = 3;
            currentTeam++;
            if (currentTeam > teamsCount) currentTeam = 1;
            updateTeamInTitle(teamNumImg, teamIcon);
        }
        step++;
        updateStepsInTitle(stepsImg);
    }


    public static void updateTexture(CustomButton CB) {                                                   // обновляет текстуру клетки в соответствии с её командой и состоянием
        if (CB.getState() == STATE_MISSING) {
            CB.setImageResource(R.drawable.ic_grnd_main);
            CB.setImageAlpha(0);
        }
        else if (CB.getState() == STATE_NEUTRAL) {
            CB.setImageResource(R.drawable.ic_grnd_main);
            CB.setImageAlpha(210);
        }
        else {
            int textureID = CB.getState() * 4 + GameSettings.getPlayers_textureState(CB.getTeam() - 1) - 5;
            switch (textureID) {
                case 0: CB.setImageResource(R.drawable.grnd_black); break;
                case 1: CB.setImageResource(R.drawable.grnd_grace); break;
                case 2: CB.setImageResource(R.drawable.grnd_lava); break;
                case 3: CB.setImageResource(R.drawable.grnd_sand); break;
                case 4: CB.setImageResource(R.drawable.black_kill); break;
                case 5: CB.setImageResource(R.drawable.grace_kill); break;
                case 6: CB.setImageResource(R.drawable.lava_kill); break;
                case 7: CB.setImageResource(R.drawable.sand_kill); break;
                case 8: CB.setImageResource(R.drawable.ctl_black); break;
                case 9: CB.setImageResource(R.drawable.ctl_grace); break;
                case 10: CB.setImageResource(R.drawable.ctl_lava); break;
                case 11: CB.setImageResource(R.drawable.ctl_sand); break;
                case 12: CB.setImageResource(R.drawable.ctl_black_die); break;
                case 13: CB.setImageResource(R.drawable.ctl_grace_die); break;
                case 14: CB.setImageResource(R.drawable.ctl_lava_die); break;
                case 15: CB.setImageResource(R.drawable.ctl_sand_die); break;
            }
        }
    }

    public static void updateDataAndTexture(CustomButton CB, Integer newTeam, Byte newState) {
        // ставит кнопке новую игровую команду, состояние и обновляет её текстуру
        // если в newTeam или newState передать null, то команда или состояние обновляться не будет
        if (newTeam != null) CB.setTeam(newTeam);
        if (newState != null) CB.setState(newState);
        updateTexture(CB);
    }


    private static void updateTeamInTitle(ImageView teamNumImg, ImageView teamIcon) {
        switch (currentTeam) {
            case 1:
                teamNumImg.setBackgroundResource(R.drawable.ic_gameplay_num_1);
                break;
            case 2:
                teamNumImg.setBackgroundResource(R.drawable.ic_gameplay_num_2);
                break;
            case 3:
                teamNumImg.setBackgroundResource(R.drawable.ic_gameplay_num_3);
                break;
            case 4:
                teamNumImg.setBackgroundResource(R.drawable.ic_gameplay_num_4);
                break;
        }
        switch (GameSettings.getPlayers_textureState(currentTeam - 1)) {
            case 1:
                teamIcon.setBackgroundResource(R.drawable.grnd_black);
                break;
            case 2:
                teamIcon.setBackgroundResource(R.drawable.grnd_grace);
                break;
            case 3:
                teamIcon.setBackgroundResource(R.drawable.grnd_lava);
                break;
            case 4:
                teamIcon.setBackgroundResource(R.drawable.grnd_sand);
                break;
        }
    }

    private static void updateStepsInTitle(ImageView stepsImg) {
        switch (stepsLeft) {
            case 1:
                stepsImg.setBackgroundResource(R.drawable.ic_gameplay_num_1);
                break;
            case 2:
                stepsImg.setBackgroundResource(R.drawable.ic_gameplay_num_2);
                break;
            case 3:
                stepsImg.setBackgroundResource(R.drawable.ic_gameplay_num_3);
                break;
        }
    }

}
