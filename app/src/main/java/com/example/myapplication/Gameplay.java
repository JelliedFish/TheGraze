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

        private static final byte TEXTURE_MISSING = -1;
        private static final byte TEXTURE_NEUTRAL = 0;
        private static final byte TEXTURE_ALIVE = 1;
        private static final byte TEXTURE_KILL = 2;
        private static final byte TEXTURE_CASTLE = 3;
        private static final byte TEXTURE_CASTLEKILLED = 4;

    //

    int Height;
    int Width;
    static int teamsCount = 2;                                                                      // количество команд в игре, на данный момент константа, равная 2.
    static int[] castleCoords = {0, 0, 0, 0};                                                       // координаты (в массиве fields, а не двумерном массиве buttons!) замков соответствующих 4 команд


    static int currentTeam;                                                                         // команда, которая сейчас ходит
    static int stepsLeft;                                                                           // сколько ходов осталось этой команде
    static int step;                                                                                // счётчик шагов (сейчас работает, но нигде не применяется)

    static int[] players_textures = {1, 2, 3, 4};                                                   // тут хранятся данные о текстурах игроков. Команде N соответствует номер массива N-1

    CustomButton[][] buttons = new CustomButton[Width][Height];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);                         // фиксируем вертикальную ориентацию окна


        Intent sgf_to_gameplay = getIntent();                                                       //
        String gameFieldInfo = sgf_to_gameplay.getStringExtra("GAME_FIELD_KEY");                    //
        int pos = gameFieldInfo.indexOf(';');                                                       // считываем данные о ширине и высоте поля из
        Width = Integer.parseInt(gameFieldInfo.substring(0, pos));                                  // предыдущего activity
        Height = Integer.parseInt(gameFieldInfo.substring(pos + 1));                                //
        buttons = new CustomButton[Width][Height];                                                  //


        final List<CustomButton> fields = new ArrayList<>();                                        // массив всех кнопок

        for (int i = 0; i < Height * Width; i++) {                                                  //
            CustomButton tmp = new CustomButton(getBaseContext());                                  //
            setTexture(tmp, 0, TEXTURE_NEUTRAL);                                                    // заполнение всех кнопок картинками
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
        fields.get(castleCoords[0]).setState(1);
        fields.get(castleCoords[1]).setState(-1);
        setTexture(fields.get(castleCoords[0]), 1, TEXTURE_CASTLE);                                 // ставим текстуры и координаты баз
        setTexture(fields.get(castleCoords[1]), 2, TEXTURE_CASTLE);

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

                    if (tmp.getState() != -2 && tmp.getState() != 2) {                              // если мы не нажали на "мертвую" клетку, то


                        if (currentTeam == 2) {                                                     // если ходит второй

                            if (ReasonsToPut(x, y)) {                                               // есть ли рядом живые клопы
                                if (tmp.getState() != -1) {                                         // если нажал не на своего
                                    minusStep();
                                    if (tmp.getState() == 1) {                                      // нажал на чужого - убивает его
                                        tmp.setState(2);                                            // изменение состояния мертвого клопа
                                    } else {
                                        tmp.setState(-1);
                                    }
                                }
                            } else {
                                if (ReasonsToEat(x, y)) {                                           // можно ли съесть данную клетку
                                    if (tmp.getState() == 1) {
                                        tmp.setState(2);
                                        minusStep();
                                    }
                                }
                            }

                        } else {                                                                    // если ходит первый (тут всё аналогично)

                            if (ReasonsToPut(x, y)) {
                                if (tmp.getState() != 1) {
                                    minusStep();
                                    if (tmp.getState() == -1) {
                                        tmp.setState(-2);
                                    } else {
                                        tmp.setState(1);
                                    }
                                }
                            } else {
                                if (ReasonsToEat(x, y)) {
                                    if (tmp.getState() == -1) {
                                        tmp.setState(-2);
                                        minusStep();
                                    }
                                }
                            }

                        }
                    }


                    clear();                                                                        // обнуление флагов для проверки клеток с кучкой мёртвых клопов

                    switch (tmp.getState()) {                                                       // дальше обновление текстур
                        case 2:

                            if (isCastle(x, y, 1))
                                setTexture(tmp, 1, TEXTURE_CASTLEKILLED);
                            else
                                setTexture(tmp, 2, TEXTURE_KILL);
                            break;

                        case 1:

                            if (!isCastle(x, y, 1))
                                setTexture(tmp, 1, TEXTURE_ALIVE);
                            break;

                        case -1:

                            if (!isCastle(x, y, 2))
                                setTexture(tmp, 2, TEXTURE_ALIVE);
                            break;

                        case -2:

                            if (isCastle(x, y, 2))
                                setTexture(tmp, 2, TEXTURE_CASTLEKILLED);
                            else
                                setTexture(tmp, 1, TEXTURE_KILL);
                            break;

                    }
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

    private boolean buttonMatch(CustomButton CB, int team, boolean testForAlive, boolean testForKill, boolean testForCastle) {
        /* Метод, проверяющий, подходит ли кнопка CB к ХОТЯ БЫ ОДНОМУ указанному флагу.
         * Например, функция buttonMatch(buttons[2][3], 1, true, false, true) вернёт true, если в клетке (2;3)
         * стоит живой клоп команды 1 ИЛИ база команды 1.
         */
        return false;
    }


    private boolean ReasonsToPut(int x, int y) {                                                    // проверка постановки клопа
        boolean b = false;
        int d = (currentTeam == 2)? -1 : 1;

        try {
            b = b || (d == buttons[x - 1][y].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b = b || (d == buttons[x - 1][y - 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b = b || (d == buttons[x - 1][y + 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b = b || (d == buttons[x][y - 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b = b || (d == buttons[x][y + 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b = b || (d == buttons[x + 1][y - 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b = b || (d == buttons[x + 1][y].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b = b || (d == buttons[x + 1][y + 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }

        return b;
    }

    private boolean ReasonsToEat(int x, int y) {                                                    // проверка съедания клопа
        boolean b = false;
        int d = (currentTeam == 2)? 2 : -2;

        try {
            b = b || (d == buttons[x - 1][y].getState() && checkActivity(x - 1, y));
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x - 1][y - 1].getState() && checkActivity(x - 1, y - 1));
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x - 1][y + 1].getState() && checkActivity(x - 1, y + 1));
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x][y - 1].getState() && checkActivity(x, y - 1));
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x][y + 1].getState() && checkActivity(x, y + 1));
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x + 1][y - 1].getState() && checkActivity(x + 1, y - 1));
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x + 1][y].getState() && checkActivity(x + 1, y));
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x + 1][y + 1].getState() && checkActivity(x + 1, y + 1));
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }


        return b;
    }

    public boolean checkActivity(int x, int y) {                                                    // проверка активности мертвой кучи клопов
        boolean result = false;

        boolean main_flg = false;
        CustomButton tmp = buttons[x][y];
        tmp.setCheckable(false);


        main_flg = ReasonsToPut(x, y);
        if (main_flg == true) {
            result = true;
            Log.d("N", "True");
        } else {
            if (existEatenNear(x, y)) {
                Log.d("L", "Yes");

                try {
                    if (buttons[x + 1][y + 1].getState() == tmp.getState() && buttons[x + 1][y + 1].getCheckable()) {
                        result = result || checkActivity(x + 1, y + 1);
                    }
                } catch (IndexOutOfBoundsException e) {
                    Log.d("i gotcha:", "promahnulis' mimo imdexa");
                }
                try {
                    if (buttons[x + 1][y - 1].getState() == tmp.getState() && buttons[x + 1][y - 1].getCheckable()) {
                        result = result || checkActivity(x + 1, y - 1);
                    }
                } catch (IndexOutOfBoundsException e) {
                    Log.d("i gotcha:", "promahnulis' mimo imdexa");
                }
                try {
                    if (buttons[x + 1][y].getState() == tmp.getState() && buttons[x + 1][y].getCheckable()) {
                        result = result || checkActivity(x + 1, y);
                    }
                } catch (IndexOutOfBoundsException e) {
                    Log.d("i gotcha:", "promahnulis' mimo imdexa");
                }
                try {
                    if (buttons[x][y - 1].getState() == tmp.getState() && buttons[x][y - 1].getCheckable()) {
                        result = result || checkActivity(x, y - 1);
                    }
                } catch (IndexOutOfBoundsException e) {
                    Log.d("i gotcha:", "promahnulis' mimo imdexa");
                }
                try {
                    if (buttons[x - 1][y - 1].getState() == tmp.getState() && buttons[x - 1][y - 1].getCheckable()) {
                        result = result || checkActivity(x - 1, y - 1);
                    }

                } catch (IndexOutOfBoundsException e) {
                    Log.d("i gotcha:", "promahnulis' mimo imdexa");
                }
                try {
                    if (buttons[x - 1][y].getState() == tmp.getState() && buttons[x - 1][y].getCheckable()) {
                        result = result || checkActivity(x - 1, y);
                    }
                } catch (IndexOutOfBoundsException e) {
                    Log.d("i gotcha:", "promahnulis' mimo imdexa");
                }
                try {
                    if (buttons[x - 1][y + 1].getState() == tmp.getState() && buttons[x - 1][y + 1].getCheckable()) {
                        result = result || checkActivity(x - 1, y + 1);
                    }
                } catch (IndexOutOfBoundsException e) {
                    Log.d("i gotcha:", "promahnulis' mimo imdexa");
                }
                try {
                    if (buttons[x][y + 1].getState() == tmp.getState() && buttons[x][y + 1].getCheckable()) {
                        result = result || checkActivity(x, y + 1);
                    }
                } catch (IndexOutOfBoundsException e) {
                    Log.d("i gotcha:", "promahnulis' mimo imdexa");
                }
            } else {
                result = false;
            }
        }




        return result;
    }

    public boolean existEatenNear(int x, int y) {                                                   // проверка существования рядом мертвых клопов
        int d = buttons[x][y].getState();

        boolean b = false;
        try {
            b = b || (d == buttons[x - 1][y].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b = b || (d == buttons[x - 1][y - 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b = b || (d == buttons[x - 1][y + 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b = b || (d == buttons[x][y - 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b = b || (d == buttons[x][y + 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b = b || (d == buttons[x + 1][y - 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b = b || (d == buttons[x + 1][y].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }
        try {
            b = b || (d == buttons[x + 1][y + 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo indexa");
        }

        return b;
    }


    public void clear() {                                                                           // обнуление флагов клеток
        for (int k1 = 0; k1 < Width; k1++) {
            for (int k2 = 0; k2 < Height; k2++) {
                buttons[k1][k2].setCheckable(true);
            }
        }
    }

    public void minusStep() {                                                                       // процедура отнятия шага; выполняется при кажом успешном действии
        stepsLeft--;
        if (stepsLeft == 0) {
            stepsLeft = 3;
            currentTeam++;
            if (currentTeam > teamsCount) currentTeam = 1;
        }
        step++;
    }

    public void setTexture(CustomButton CB, int team, byte state) {                                 // ставит текстуру искомой команды team с искомым состоянием state
        if (state == 0) CB.setImageResource(R.drawable.ic_grnd_main);
        else {
            int textureID = state * 4 + players_textures[team - 1] - 5;
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

    public boolean isCastle(int x, int y, int seekingTeam) {                                        // проверяет, есть ли в данной точке база команды seekingTeam
        return (castleCoords[seekingTeam - 1] == y * Width + x);
    }
}
