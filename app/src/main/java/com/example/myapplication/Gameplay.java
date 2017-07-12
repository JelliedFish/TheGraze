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

import com.example.myapplication.Abstract.PopupWindow;
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
    static boolean[] activeTeams;                                                                   // играющие команды
    static int activeTeamsCount;                                                                    // количество живых команд

    CustomButton[][] buttons = new CustomButton[Width][Height];


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);                         // фиксируем вертикальную ориентацию окна

        final List<ImageButton> gameplayButtons = new ArrayList<>();



        //—————ВСПЛЫВАЮЩЕЕ ОКНО О ПРЕРЫВАНИИ ИГРЫ—————//



        final RelativeLayout popup_end = (RelativeLayout) findViewById(R.id.popup_gameplay_end);



        final ImageButton popup_exit_yes = (ImageButton) findViewById(R.id.popup_gameplay_end_yes);
        final ImageButton popup_exit_no = (ImageButton) findViewById(R.id.popup_gameplay_end_no);

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
                        PopupWindow.hide(popup_end, gameplayButtons);
                        finish();
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
                        PopupWindow.hide(popup_end, gameplayButtons);
                        break;
                }
                return true;
            }
        });



        //—————ВСПЛЫВАЮЩЕЕ ОКНО ПРИ ВЫЛЕТЕ ИГРОКА (>2)—————//



        final RelativeLayout popup_playersleft_2ormore = (RelativeLayout) findViewById(R.id.popup_gameplay_last2more);



        final ImageButton popup_playersleft_2ormore_ok = (ImageButton) findViewById(R.id.popup_gameplay_last2more_ok);

        final ImageView popup_playersleft_2ormore_kickedplayer = (ImageView) findViewById(R.id.popup_gameplay_last2more_kickedplayer);
        final ImageView popup_playersleft_2ormore_playersleft = (ImageView) findViewById(R.id.popup_gameplay_last2more_playersleft);

        final ImageView popup_playersleft_2ormore_playericon1 = (ImageView) findViewById(R.id.popup_gameplay_last2more_playericon1);
        final ImageView popup_playersleft_2ormore_playericon2 = (ImageView) findViewById(R.id.popup_gameplay_last2more_playericon2);
        final ImageView popup_playersleft_2ormore_playericon3 = (ImageView) findViewById(R.id.popup_gameplay_last2more_playericon3);

        final ImageView popup_playersleft_2ormore_playernumber1 = (ImageView) findViewById(R.id.popup_gameplay_last2more_playernumber1);
        final ImageView popup_playersleft_2ormore_playernumber2 = (ImageView) findViewById(R.id.popup_gameplay_last2more_playernumber2);
        final ImageView popup_playersleft_2ormore_playernumber3 = (ImageView) findViewById(R.id.popup_gameplay_last2more_playernumber3);

        popup_playersleft_2ormore_ok.setBackgroundResource(R.drawable.ic_popup_gameplay_ok);



        popup_playersleft_2ormore_ok.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        popup_playersleft_2ormore_ok.setBackgroundResource(R.drawable.ic_popup_gameplay_okclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        popup_playersleft_2ormore_ok.setBackgroundResource(R.drawable.ic_popup_gameplay_ok);
                        PopupWindow.hide(popup_playersleft_2ormore, gameplayButtons);
                        break;
                }
                return true;
            }
        });



        //—————ВСПЛЫВАЮЩЕЕ ОКНО ПРИ ВЫЛЕТЕ ИГРОКА (=1)—————//



        final RelativeLayout popup_playersleft_1 = (RelativeLayout) findViewById(R.id.popup_gameplay_last1);



        final ImageButton popup_playersleft_1_ok = (ImageButton) findViewById(R.id.popup_gameplay_last1_ok);

        final ImageView popup_playersleft_1_kickedplayer = (ImageView) findViewById(R.id.popup_gameplay_last1_kickedplayer);
        final ImageView popup_playersleft_1_winnernumber = (ImageView) findViewById(R.id.popup_gameplay_last1_winnernumber);
        final ImageView popup_playersleft_1_winnericon = (ImageView) findViewById(R.id.popup_gameplay_last1_winnericon);

        popup_playersleft_1_ok.setBackgroundResource(R.drawable.ic_popup_gameplay_ok);



        popup_playersleft_1_ok.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        popup_playersleft_1_ok.setBackgroundResource(R.drawable.ic_popup_gameplay_okclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        popup_playersleft_1_ok.setBackgroundResource(R.drawable.ic_popup_gameplay_ok);
                        PopupWindow.hide(popup_playersleft_1, gameplayButtons);
                        finish();
                        break;
                }
                return true;
            }
        });



        //—————ГЛАВНОЕ ОКНО—————//



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
        activeTeams = getPrimalStateForActiveTeams();
        activeTeamsCount = teamsCount;


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

                        clear();                                                                    // обнуление флагов для проверки клеток с кучкой мёртвых клопов
                    }
                }
            });
        }



        //—————БОКОВОЕ СЛАЙД-МЕНЮ—————//



        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.sidemenu);
        menu.setBehindWidthRes(R.dimen.slidingmenu_behind_width);



        final ImageButton slmenu_surrender = (ImageButton) findViewById(R.id.gameplay_surrender);
        final ImageButton slmenu_help = (ImageButton) findViewById(R.id.gameplay_help);
        final ImageButton slmenu_terminate = (ImageButton) findViewById(R.id.gameplay_terminate);
        final ImageButton slmenu_pause = (ImageButton) findViewById(R.id.gameplay_pause);

        gameplayButtons.add(slmenu_surrender);
        gameplayButtons.add(slmenu_help);
        gameplayButtons.add(slmenu_terminate);
        gameplayButtons.add(slmenu_pause);

        slmenu_surrender.setBackgroundResource(R.drawable.ic_gameplay_surrender);
        slmenu_help.setBackgroundResource(R.drawable.ic_btn_mm_help);
        slmenu_terminate.setBackgroundResource(R.drawable.ic_gameplay_terminate);
        slmenu_pause.setBackgroundResource(R.drawable.ic_gameplay_pause);



        slmenu_surrender.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        slmenu_surrender.setBackgroundResource(R.drawable.ic_gameplay_surrenderclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        slmenu_surrender.setBackgroundResource(R.drawable.ic_gameplay_surrender);

                        activeTeamsCount--;
                        activeTeams[currentTeam - 1] = false;
                        int[] activeTeamsList = getActiveTeamsList();

                        if (activeTeamsCount == 1) {
                            popup_playersleft_1_kickedplayer.setBackgroundResource(intToImg(currentTeam));
                            popup_playersleft_1_winnernumber.setBackgroundResource(intToImg(activeTeamsList[0]));
                            popup_playersleft_1_winnericon.setBackgroundResource(teamNumToIcon(activeTeamsList[0]));

                            PopupWindow.display(popup_playersleft_1, gameplayButtons);
                        } else {
                            popup_playersleft_2ormore_kickedplayer.setBackgroundResource(intToImg(currentTeam));
                            popup_playersleft_2ormore_playersleft.setBackgroundResource(intToImg(activeTeamsCount));

                            popup_playersleft_2ormore_playernumber1.setBackgroundResource(intToImg(activeTeamsList[0]));
                            popup_playersleft_2ormore_playernumber2.setBackgroundResource(intToImg(activeTeamsList[1]));
                            popup_playersleft_2ormore_playernumber3.setBackgroundResource((activeTeamsCount == 3) ? intToImg(activeTeamsList[2]) : R.drawable.ic_aplha);

                            popup_playersleft_2ormore_playericon1.setBackgroundResource(teamNumToIcon(activeTeamsList[0]));
                            popup_playersleft_2ormore_playericon2.setBackgroundResource(teamNumToIcon(activeTeamsList[1]));
                            popup_playersleft_2ormore_playericon3.setBackgroundResource((activeTeamsCount == 3) ? teamNumToIcon(activeTeamsList[2]) : R.drawable.ic_aplha);

                            PopupWindow.display(popup_playersleft_2ormore, gameplayButtons);
                        }

                        minusStep(title_teamNum, title_teamIcon, title_stepsLeft);
                        break;
                }
                return true;
            }
        });

        slmenu_help.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        slmenu_help.setBackgroundResource(R.drawable.ic_btn_mm_helpclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        slmenu_help.setBackgroundResource(R.drawable.ic_btn_mm_help);
                        Intent gameplay_to_help = new Intent(getBaseContext(), Help.class);
                        startActivity(gameplay_to_help);
                        break;
                }
                return true;
            }
        });

        slmenu_terminate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        slmenu_terminate.setBackgroundResource(R.drawable.ic_gameplay_terminateclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        slmenu_terminate.setBackgroundResource(R.drawable.ic_gameplay_terminate);
                        if (activeTeamsCount == 1)
                            finish();
                        else
                            PopupWindow.display(popup_end, gameplayButtons);
                        break;
                }
                return true;
            }
        });

        slmenu_pause.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        slmenu_pause.setBackgroundResource(R.drawable.ic_gameplay_pauseclicked);
                        break;
                    case MotionEvent.ACTION_UP:
                        slmenu_pause.setBackgroundResource(R.drawable.ic_gameplay_pause);
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

    private boolean[] getPrimalStateForActiveTeams() {
        boolean[] res = new boolean[teamsCount];
        for (int i = 0; i < teamsCount; i++) {
            res[i] = true;
        }
        return res;
    }

    private int[] getActiveTeamsList() {
        int[] res = new int[activeTeamsCount];
        int k = 0;
        for (int i = 0; i < activeTeams.length; i++) {
            if (activeTeams[i]) {
                res[k] = i + 1;
                k++;
            }
        }
        return res;
    }

    private void minusStep(ImageView teamNumImg, ImageView teamIcon, ImageView stepsImg) {          // процедура отнятия шага; выполняется при каждом успешном действии
        do {
            stepsLeft--;
            if (stepsLeft == 0) {
                stepsLeft = 3;
                currentTeam++;
                if (currentTeam > teamsCount) currentTeam = 1;
            }
        } while (!activeTeams[currentTeam - 1]);

        step++;
        updateTeamInTitle(teamNumImg, teamIcon);
        updateStepsInTitle(stepsImg);
    }


    public static void updateTexture(CustomButton CB) {                                                   // обновляет текстуру клетки в соответствии с её командой и состоянием
        if (CB.getState() == STATE_MISSING)
            CB.setImageResource(R.drawable.ic_aplha);
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
        teamNumImg.setBackgroundResource(intToImg(currentTeam));
        teamIcon.setBackgroundResource(teamNumToIcon(currentTeam));
    }

    private static void updateStepsInTitle(ImageView stepsImg) {
        stepsImg.setBackgroundResource(intToImg(stepsLeft));
    }


    private static int intToImg(int i) {
        switch (i) {
            case 1:
                return R.drawable.ic_gameplay_num_1;
            case 2:
                return R.drawable.ic_gameplay_num_2;
            case 3:
                return R.drawable.ic_gameplay_num_3;
            case 4:
                return R.drawable.ic_gameplay_num_4;
        }
        return 0;
    }

    private static int teamNumToIcon(int teamNum) {
        switch (GameSettings.getPlayers_textureState(teamNum - 1)) {
            case 1:
                return R.drawable.grnd_black;
            case 2:
                return R.drawable.grnd_grace;
            case 3:
                return R.drawable.grnd_lava;
            case 4:
                return R.drawable.grnd_sand;
        }
        return 0;
    }

}
