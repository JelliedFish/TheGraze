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
import com.example.myapplication.Abstract.ViewPatterns;
import com.example.myapplication.CustomObjects.CustomAdapter;
import com.example.myapplication.CustomObjects.CustomButton;
import com.example.myapplication.Abstract.LayoutSetter;
import com.example.myapplication.Data.Const;
import com.example.myapplication.Data.GameSettings;
import com.example.myapplication.Data.MapData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

public class Gameplay extends AppCompatActivity {

    // ДАННЫЕ, ПЕРЕДАВАЕМЫЕ ИЗ ПРЕДЫДУЩИХ ACTIVITY

        int fieldWidth;                                                                             // ширина поля (имеются ввиду количество игровых клеток в ширину)
        int fieldHeight;                                                                            // высота поля
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
    static int[] aliveUnitsCount;                                                                   // количество ЖИВЫХ клопов в каждой команде


    ImageView title_teamNum;
    ImageView title_teamIcon;
    ImageView title_stepsLeft;


    CustomButton[][] buttons = new CustomButton[fieldWidth][fieldHeight];


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);                         // фиксируем вертикальную ориентацию окна

        final List<ImageButton> gameplayButtons = new ArrayList<>();

        final GridView gridView = (GridView) findViewById(R.id.gridView);                                 // создаем гридвью



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
                        PopupWindow.hide(popup_end, gameplayButtons, gridView);
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
                        PopupWindow.hide(popup_end, gameplayButtons, gridView);
                        break;
                }
                return true;
            }
        });



        //—————ВСПЛЫВАЮЩЕЕ ОКНО ПРИ ВЫЛЕТЕ ИГРОКА (>=2)—————//



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
                        PopupWindow.hide(popup_playersleft_2ormore, gameplayButtons, gridView);
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
                        PopupWindow.hide(popup_playersleft_1, gameplayButtons, gridView);
                        finish();
                        break;
                }
                return true;
            }
        });



        //—————ГЛАВНОЕ ОКНО—————//



        Intent sgf_to_gameplay = getIntent();                                                       //
        fieldWidth = sgf_to_gameplay.getIntExtra("GAME_FIELD_KEY_WIDTH", 8);                        // передача данных о карте из предыдущих activity
        fieldHeight = sgf_to_gameplay.getIntExtra("GAME_FIELD_KEY_HEIGHT", 12);                     //
        teamsCount = sgf_to_gameplay.getIntExtra("GAME_FIELD_KEY_TEAMSCOUNT", 2);                   //
        castleCoords = MapData.decryptCastleCoords(sgf_to_gameplay.getStringExtra("GAME_FIELD_KEY_CASTLESCOORDS"));
        buttonsData = MapData.decryptButtonsData(sgf_to_gameplay.getStringExtra("GAME_FIELD_KEY_BUTTONSDATA"));


        buttons = new CustomButton[fieldWidth][fieldHeight];


        final List<CustomButton> fields = new ArrayList<>();                                        // массив всех кнопок

        for (int i = 0; i < fieldHeight * fieldWidth; i++) {                                        //
            CustomButton tmp = new CustomButton(getBaseContext());                                  //
            if (buttonsData[i])                                                                     // заполнение поля клетками в соответствии с данными об их
                updateDataAndTexture(tmp, 0, Const.STATE_NEUTRAL);                                  // существовании (buttonData[])
            else                                                                                    //
                updateDataAndTexture(tmp, 0, Const.STATE_MISSING);                                  //
            tmp.setCheckable(true);                                                                 //
            fields.add(tmp);                                                                        //
        }

        for (int i = 0; i < teamsCount; i++) {                                                      // ставим текстуры и координаты баз
            updateDataAndTexture(fields.get(castleCoords[i]), i + 1, Const.STATE_CASTLE);
        }


        int l = 0;
        for (int i = 0; i < fieldHeight; i++) {                                                     //
            for (int j = 0; j < fieldWidth; j++) {                                                  //
                buttons[j][i] = fields.get(l);                                                      // связываем наш массив кнопок с двумерным массивом кнопок
                l++;                                                                                //
            }                                                                                       //
        }                                                                                           //



        gridView.setAdapter(new CustomAdapter(this, fields));                                       // связываем гридвью и адаптер
        gridView.setNumColumns(fieldWidth);                                                         // установка кол-ва колонок для гридвью


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();                         // фича, необходимая для получения высоты и ширины экрана в пикселях:
                                                                                                    // displayMetrics.widthPixels;
                                                                                                    // displayMetrics.heightPixels;

        LinearLayout wrapperView = (LinearLayout) findViewById(R.id.wrapper);                       // устанавливаем разметчик для activity
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        int[] gridMargins = LayoutSetter.getRelativeMargins(displayMetrics.widthPixels, displayMetrics.heightPixels, (double) fieldWidth / fieldHeight, 0.05, 0.12, 0.09, 0.03);
        // рассчитывает отступы для игровой сетки (см. класс LayoutSetter)

        layoutParams.setMargins(gridMargins[0], gridMargins[1], gridMargins[2], gridMargins[3]);
        wrapperView.setLayoutParams(layoutParams);                                                  // устанавливаем полученные отступы в layout



        step = 0;
        currentTeam = 1;
        stepsLeft = 3;
        activeTeams = getPrimalStateForActiveTeams();
        activeTeamsCount = teamsCount;
        aliveUnitsCount = new int[]{1, 1, 1, 1};


        title_teamNum = (ImageView) findViewById(R.id.gameplay_team);
        title_teamIcon = (ImageView) findViewById(R.id.gameplay_playingicon);
        title_stepsLeft = (ImageView) findViewById(R.id.gameplay_steps);

        updateTitle();


        for (int position = 0; position < fieldHeight * fieldWidth; position++) {                   // обрабатываем каждую кнопку

            final CustomButton tmp = fields.get(position);
            final int x = position % fieldWidth;                                                    // находим месторасположение кнопки в двумерном массиве (и на поле
            final int y = position / fieldWidth;                                                    // соотвественно), задаем эти значения переменным

            tmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {                                                         // добавляем для каждой кнопки слушатель действия

                    if ((tmp.getState() != Const.STATE_MISSING) && (tmp.getState() != Const.STATE_KILL) && (tmp.getState() != Const.STATE_CASTLEKILLED) && (tmp.getTeam() != currentTeam)) {
                                                                                                    // если мы не нажали на "мертвую" или отсутствующую клетку, либо на клетку своей команды
                        if (ReasonsToPut(x, y, currentTeam)) {
                            switch (tmp.getState()) {
                                case Const.STATE_ALIVE: {
                                    aliveUnitsCount[tmp.getTeam() - 1]--;
                                    updateDataAndTexture(tmp, currentTeam, Const.STATE_KILL);
                                }
                                break;
                                case Const.STATE_CASTLE: {
                                    aliveUnitsCount[tmp.getTeam() - 1]--;
                                    updateDataAndTexture(tmp, null, Const.STATE_CASTLEKILLED);
                                }
                                break;
                                case Const.STATE_NEUTRAL: {
                                    aliveUnitsCount[currentTeam - 1]++;
                                    updateDataAndTexture(tmp, currentTeam, Const.STATE_ALIVE);
                                }
                                break;
                            }
                            minusStep();
                        }
                        else if (ReasonsToEat(x, y, currentTeam)) {
                            switch (tmp.getState()) {
                                case Const.STATE_ALIVE: {
                                    aliveUnitsCount[tmp.getTeam() - 1]--;
                                    updateDataAndTexture(tmp, currentTeam, Const.STATE_KILL);
                                    minusStep();
                                }
                                break;
                                case Const.STATE_CASTLE: {
                                    aliveUnitsCount[tmp.getTeam() - 1]--;
                                    updateDataAndTexture(tmp, null, Const.STATE_CASTLEKILLED);
                                    minusStep();
                                }
                                break;
                            }
                        }

                        clear();                                                                    // обнуление флагов для проверки клеток с кучкой мёртвых клопов

                        for (int i = 1; i <= teamsCount; i++) {                                     // для каждого игрока проверяет, надо ли его кикнуть из-за отсутствия его живых клеток
                            if (activeTeams[i - 1] && aliveUnitsCount[i - 1] == 0) {

                                activeTeamsCount--;
                                activeTeams[i - 1] = false;
                                int[] activeTeamsList = getActiveTeamsList();

                                if (activeTeamsCount == 1) {
                                    ViewPatterns.setNumImg(popup_playersleft_1_kickedplayer, i, Const.COLOR_YELLOW);
                                    ViewPatterns.setNumImg(popup_playersleft_1_winnernumber, activeTeamsList[0], Const.COLOR_YELLOW);
                                    popup_playersleft_1_winnericon.setBackgroundResource(GameSettings.getPlayerTextureID(activeTeamsList[0]));

                                    PopupWindow.display(popup_playersleft_1, gameplayButtons, gridView);
                                } else {
                                    ViewPatterns.setNumImg(popup_playersleft_2ormore_kickedplayer, i, Const.COLOR_YELLOW);
                                    ViewPatterns.setNumImg(popup_playersleft_2ormore_playersleft, activeTeamsCount, Const.COLOR_YELLOW);

                                    ViewPatterns.setNumImg(popup_playersleft_2ormore_playernumber1, activeTeamsList[0], Const.COLOR_YELLOW);
                                    popup_playersleft_2ormore_playericon1.setBackgroundResource(GameSettings.getPlayerTextureID(activeTeamsList[0]));

                                    ViewPatterns.setNumImg(popup_playersleft_2ormore_playernumber2, activeTeamsList[1], Const.COLOR_YELLOW);
                                    popup_playersleft_2ormore_playericon2.setBackgroundResource(GameSettings.getPlayerTextureID(activeTeamsList[1]));

                                    if (activeTeamsCount == 3) {
                                        ViewPatterns.setNumImg(popup_playersleft_2ormore_playernumber3, activeTeamsList[2], Const.COLOR_YELLOW);
                                        popup_playersleft_2ormore_playericon3.setBackgroundResource(GameSettings.getPlayerTextureID(activeTeamsList[2]));
                                    } else {
                                        popup_playersleft_2ormore_playernumber3.setImageResource(R.drawable.ic_alpha);
                                        popup_playersleft_2ormore_playericon3.setBackgroundResource(R.drawable.ic_alpha);
                                    }

                                    PopupWindow.display(popup_playersleft_2ormore, gameplayButtons, gridView);
                                }

                                if (currentTeam == i)
                                    minusStep();
                            }
                        }
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
                            ViewPatterns.setNumImg(popup_playersleft_1_kickedplayer, currentTeam, Const.COLOR_YELLOW);
                            ViewPatterns.setNumImg(popup_playersleft_1_winnernumber, activeTeamsList[0], Const.COLOR_YELLOW);
                            popup_playersleft_1_winnericon.setBackgroundResource(GameSettings.getPlayerTextureID(activeTeamsList[0]));

                            PopupWindow.display(popup_playersleft_1, gameplayButtons, gridView);
                        } else {
                            ViewPatterns.setNumImg(popup_playersleft_2ormore_kickedplayer, currentTeam, Const.COLOR_YELLOW);
                            ViewPatterns.setNumImg(popup_playersleft_2ormore_playersleft, activeTeamsCount, Const.COLOR_YELLOW);

                            ViewPatterns.setNumImg(popup_playersleft_2ormore_playernumber1, activeTeamsList[0], Const.COLOR_YELLOW);
                            popup_playersleft_2ormore_playericon1.setBackgroundResource(GameSettings.getPlayerTextureID(activeTeamsList[0]));

                            ViewPatterns.setNumImg(popup_playersleft_2ormore_playernumber2, activeTeamsList[1], Const.COLOR_YELLOW);
                            popup_playersleft_2ormore_playericon2.setBackgroundResource(GameSettings.getPlayerTextureID(activeTeamsList[1]));

                            if (activeTeamsCount == 3) {
                                ViewPatterns.setNumImg(popup_playersleft_2ormore_playernumber3, activeTeamsList[2], Const.COLOR_YELLOW);
                                popup_playersleft_2ormore_playericon3.setBackgroundResource(GameSettings.getPlayerTextureID(activeTeamsList[2]));
                            } else {
                                popup_playersleft_2ormore_playernumber3.setImageResource(R.drawable.ic_alpha);
                                popup_playersleft_2ormore_playericon3.setBackgroundResource(R.drawable.ic_alpha);
                            }

                            PopupWindow.display(popup_playersleft_2ormore, gameplayButtons, gridView);
                        }

                        minusStep();
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
                            PopupWindow.display(popup_end, gameplayButtons, gridView);
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
        return ((buttons[x][y].getState() == Const.STATE_ALIVE) || (buttons[x][y].getState() == Const.STATE_CASTLE)) && buttons[x][y].getTeam() == team;
    }

    private boolean isKilledUnitThere(int x, int y, int team) {                                     // вспомогательный метод для ReasonsToEat и existEatenNear
        return (buttons[x][y].getState() == Const.STATE_KILL) && (buttons[x][y].getTeam() == team);
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
        for (int k1 = 0; k1 < fieldWidth; k1++) {
            for (int k2 = 0; k2 < fieldHeight; k2++) {
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

    private void minusStep() {                                                                      // процедура отнятия шага; выполняется при каждом успешном действии
        do {
            stepsLeft--;
            if (stepsLeft == 0) {
                stepsLeft = 3;
                currentTeam++;
                if (currentTeam > teamsCount) currentTeam = 1;
            }
        } while (!activeTeams[currentTeam - 1]);

        step++;
        updateTitle();
    }


    public static void updateTexture(CustomButton CB) {                                                   // обновляет текстуру клетки в соответствии с её командой и состоянием
        CB.setBackgroundResource(GameSettings.getPlayerTextureID(CB.getTeam(), CB.getState()));
    }

    public static void updateDataAndTexture(CustomButton CB, Integer newTeam, Byte newState) {
        // ставит кнопке новую игровую команду, состояние и обновляет её текстуру
        // если в newTeam или newState передать null, то команда или состояние обновляться не будет
        if (newTeam != null) CB.setTeam(newTeam);
        if (newState != null) CB.setState(newState);
        updateTexture(CB);
    }


    private void updateTitle() {
        ViewPatterns.setNumImg(title_teamNum, currentTeam, Const.COLOR_YELLOW);
        ViewPatterns.setNumImg(title_stepsLeft, stepsLeft, Const.COLOR_YELLOW);
        title_teamIcon.setBackgroundResource(GameSettings.getPlayerTextureID(currentTeam));
    }


}
