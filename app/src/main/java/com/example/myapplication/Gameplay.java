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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.myapplication.Additionals.CustomAdapter;
import com.example.myapplication.Additionals.CustomButton;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

public class Gameplay extends AppCompatActivity {

    // КОНСТАНТЫ

        private static final byte STATE_MISSING = -1;
        private static final byte STATE_NEUTRAL = 0;
        private static final byte STATE_ALIVE = 1;
        private static final byte STATE_KILL = 2;
        private static final byte STATE_CASTLE = 3;
        private static final byte STATE_CASTLEKILLED = 4;

    // ДАННЫЕ, ПЕРЕДАВАЕМЫЕ ИЗ ПРЕДЫДУЩИХ ACTIVITY

        int Width;
        int Height;
        int teamsCount;                                                                             // количество команд в игре, на данный момент константа, равная 2.
        static int[] castleCoords = {0, 0, 0, 0};                                                   // координаты (в массиве fields, а не двумерном массиве buttons!) замков соответствующих 4 команд
        static int[] players_textures = {1, 2, 3, 4};                                               // тут хранятся данные о текстурах игроков. Команде N соответствует номер массива N-1

    //


    static int currentTeam;                                                                         // команда, которая сейчас ходит
    static int stepsLeft;                                                                           // сколько ходов осталось этой команде
    static int step;                                                                                // счётчик шагов (сейчас работает, но нигде не применяется)

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

        String[] castlesCoordsInString = sgf_to_gameplay.getStringExtra("GAME_FIELD_KEY_CASTLESCOORDS").split(";");
        castleCoords = new int[castlesCoordsInString.length];
        for (int i = 0; i < castlesCoordsInString.length; i++) {
            castleCoords[i] = Integer.parseInt(castlesCoordsInString[i]);
        }

        buttons = new CustomButton[Width][Height];


        final List<CustomButton> fields = new ArrayList<>();                                        // массив всех кнопок

        for (int i = 0; i < Height * Width; i++) {                                                  //
            CustomButton tmp = new CustomButton(getBaseContext());                                  //
            tmp.setTeam(0);                                                                         //
            tmp.setState(STATE_NEUTRAL);                                                            //
            updateTexture(tmp);                                                                     // заполнение всех кнопок картинками
            tmp.setImageAlpha(210);                                                                 // установка прозрачности
            tmp.setCheckable(true);                                                                 //
            fields.add(tmp);                                                                        //
        }


        int l = 0;
        for (int i = 0; i < Height; i++) {                                                          //
            for (int j = 0; j < Width; j++) {                                                       //
                buttons[j][i] = fields.get(l);                                                      // связываем наш массив кнопок с двумерным массивом кнопок
                l++;                                                                                //
            }                                                                                       //
        }                                                                                           //


        castleCoords[0] = 0;
        castleCoords[1] = Width * Height - 1;
        for (int i = 0; i < teamsCount; i++) {                                                      // ставим текстуры и координаты баз
            fields.get(castleCoords[i]).setTeam(i + 1);
            fields.get(castleCoords[i]).setState(STATE_CASTLE);
            updateTexture(fields.get(castleCoords[i]));
        }


        GridView gridView = (GridView) findViewById(R.id.gridView);                                 // создаем гридвью
        gridView.setAdapter(new CustomAdapter(this, fields));                                       // связываем гридвью и адаптер
        gridView.setNumColumns(Width);                                                              // установка кол-ва колонок для гридвью


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();                         // фича, необходимая для получения высоты и ширины экрана в пикселях:
                                                                                                    // displayMetrics.widthPixels;
                                                                                                    // displayMetrics.heightPixels;

        LinearLayout wrapperView = (LinearLayout) findViewById(R.id.wrapper);                       // устанавливаем разметчик для activity
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        int leftAndRightMargins = displayMetrics.widthPixels / 20;                                  //
        int upMargin = displayMetrics.heightPixels / 10;                                            //
        int downMargin = displayMetrics.heightPixels / 20;                                          // задаём гарантированно минимальные значения отступов
        int maxGridWidth = displayMetrics.widthPixels - (leftAndRightMargins * 2);                  //
        int maxGridHeight = displayMetrics.heightPixels - upMargin - downMargin;                    //
        int additionalMargin;                                                                       //

        if ((double) Width / Height < (double) maxGridWidth / maxGridHeight) {                      //
            additionalMargin = (maxGridWidth - (maxGridHeight * Width / Height)) / 2;               // если гридвью не вписывается по горизонтали - делаем дополнительные вертикальные отступы
            leftAndRightMargins += additionalMargin;                                                //
        } else {
            additionalMargin = (maxGridHeight - (maxGridWidth * Height / Width)) / 2;               //
            upMargin += additionalMargin;                                                           // если по вертикали - доп. горизонтальные
            downMargin += additionalMargin;                                                         //
        }

        layoutParams.setMargins(leftAndRightMargins, upMargin, leftAndRightMargins, downMargin);
        wrapperView.setLayoutParams(layoutParams);                                                  // устанавливаем полученные отступы в layout


        step = 0;
        currentTeam = 1;
        stepsLeft = 3;


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
                                        tmp.setTeam(currentTeam);
                                        tmp.setState(STATE_KILL);
                                        updateTexture(tmp);
                                    }
                                    break;
                                    case STATE_CASTLE: {
                                        tmp.setState(STATE_CASTLEKILLED);
                                        updateTexture(tmp);
                                    }
                                    break;
                                    case STATE_NEUTRAL: {
                                        tmp.setTeam(currentTeam);
                                        tmp.setState(STATE_ALIVE);
                                        updateTexture(tmp);
                                    }
                                    break;
                                }
                                minusStep();
                            }
                        }
                        else if (ReasonsToEat(x, y, currentTeam) && tmp.getTeam() != currentTeam) {
                            switch (tmp.getState()) {
                                case STATE_ALIVE: {
                                    tmp.setTeam(currentTeam);
                                    tmp.setState(STATE_KILL);
                                    updateTexture(tmp);
                                } break;
                                case STATE_CASTLE: {
                                    tmp.setState(STATE_CASTLEKILLED);
                                    updateTexture(tmp);
                                } break;
                            }
                            minusStep();
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


    private void clear() {                                                                           // обнуление флагов клеток
        for (int k1 = 0; k1 < Width; k1++) {
            for (int k2 = 0; k2 < Height; k2++) {
                buttons[k1][k2].setCheckable(true);
            }
        }
    }

    private void minusStep() {                                                                       // процедура отнятия шага; выполняется при каждом успешном действии
        stepsLeft--;
        if (stepsLeft == 0) {
            stepsLeft = 3;
            currentTeam++;
            if (currentTeam > teamsCount) currentTeam = 1;
        }
        step++;
    }

    private void updateTexture(CustomButton CB) {                                 // ставит текстуру искомой команды team с искомым состоянием state
        if (CB.getState() == -1) {
            CB.setImageResource(R.drawable.ic_grnd_main);
            CB.setImageAlpha(0);
        }
        else if (CB.getState() == 0) CB.setImageResource(R.drawable.ic_grnd_main);
        else {
            int textureID = CB.getState() * 4 + players_textures[CB.getTeam() - 1] - 5;
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
}
