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

    int Height = 15;
    int Width = 10;
    static int player1_pers =1;
    static int player2_pers =2;
    //static int player1_vs =2;
    //static int player2_vs =1;

    CustomButton[][] buttons = new CustomButton[Width][Height];
    static int step = 0;                                                                            // переменная для подсчета шагов

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
            tmp.setImageResource(R.drawable.ic_grnd_main);                                          // заполнение всех кнопок картинками
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


        fields.get(0).setState(1);                                                                  // ставим текстуру базы для 1-й команды
        fields.get(Height * Width - 1).setState(-1);
        switch (player1_pers) {
            case 2:   fields.get(0).setImageResource(R.drawable.ctl_grace);
                break;
            case 3:   fields.get(0).setImageResource(R.drawable.ctl_lava);
                break;
            case 4:   fields.get(0).setImageResource(R.drawable.ctl_sand);
                break;
            case 1:   fields.get(0).setImageResource(R.drawable.ctl_black);
                break;
        }

        switch (player2_pers) {                                                                     // для 2-й команды
            case 2:  fields.get(Height * Width - 1).setImageResource(R.drawable.ctl_grace);
                break;
            case 3:  fields.get(Height * Width - 1).setImageResource(R.drawable.ctl_lava);
                break;
            case 4:  fields.get(Height * Width - 1).setImageResource(R.drawable.ctl_sand);
                break;
            case 1:  fields.get(Height * Width - 1).setImageResource(R.drawable.ctl_black);
                break;
        }

        GridView gridView = (GridView) findViewById(R.id.gridView);                                 // создаем гридвью
        gridView.setAdapter(new CustomAdapter(this, fields));                                       // связываем гридвью и адптер
        gridView.setNumColumns(Width);                                                              // установка кол-ва колонок для гридвтю


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

        Log.d("MARGINS", leftAndRightMargins + " " + upMargin + " " + leftAndRightMargins + " " + downMargin + "; ADD=" + additionalMargin + "; MAX= " + maxGridWidth + " " + maxGridHeight);
        layoutParams.setMargins(leftAndRightMargins, upMargin, leftAndRightMargins, downMargin);
        wrapperView.setLayoutParams(layoutParams);                                                  // устанавливаем полученные отступы в layout


        step = 0;


        for (int position = 0; position < Height * Width; position++) {                             // обрабатываем каждую кнопку

            final CustomButton tmp = fields.get(position);
            final int x = position % Width;                                                         // находим месторасположение кнопки в двумерном массиве (и на поле
            final int y = position / Width;                                                         // соотвественно), задаем эти значения переменным


            tmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {                                                         // добавляем для каждой кнопки слушатель действия

                    step++;                                                                         // увеличиваем шаг (ход игрока)

                    if (tmp.getState() != -2 && tmp.getState() != 2) {                              // если мы не нажали на "мертвую" клетку, то


                        if ((step % 6 == 1 || step % 6 == 2 || step % 6 == 3)) {                    // если ходит первый


                            if (ReasonsToPut(x, y)) {                                               // есть ли рядом живые клопы

                                if (tmp.getState() == -1) {                                         // нажал на своего
                                    step--;
                                } else {
                                    if (tmp.getState() == 1) {                                      // нажал на чужого - убивает его
                                        tmp.setState(2);                                            // изменение состояния мертвого клопа
                                    } else {
                                        tmp.setState(-1);
                                    }
                                }

                            } else {

                                if (ReasonsToEat(x, y)) {                                           // можно ли съесть данную клетку
                                    if (tmp.getState() == 1) {                                      // да
                                        tmp.setState(2);
                                    }
                                } else {                                                            // нет
                                    step--;
                                }

                            }


                        } else {
                                                                                                    // если ходит второй (тут всё аналогично)
                            if (ReasonsToPut(x, y) && tmp.getState() != -2 && tmp.getState() != 2) {


                                if (tmp.getState() == 1) {
                                    step--;
                                } else {
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
                                    }
                                } else {
                                    step--;
                                }
                            }
                        }


                    } else {
                        step--;
                    }


                    clear();                                                                        // обнуление флагов для проверки клеток с кучкой мертвых клопов

                    switch (tmp.getState()) {                                                       // дальше обновление текстур
                        case 2:
                            if (x== 0 && y==0) {
                                switch (player1_pers) {
                                    case 1:     tmp.setImageResource(R.drawable.ctl_black_die);
                                        break;
                                    case 2:     tmp.setImageResource(R.drawable.ctl_grace_die);
                                        break;
                                    case 3:     tmp.setImageResource(R.drawable.ctl_lava_die);
                                        break;
                                    case 4:     tmp.setImageResource(R.drawable.ctl_sand_die);
                                        break;
                                }
                            }
                            else {
                                switch (player2_pers) {                                             ////////////////////////////////////////////
                                    case 1:
                                        tmp.setImageResource(R.drawable.black_kill);
                                        break;
                                    case 2:
                                        tmp.setImageResource(R.drawable.grace_kill);
                                        break;
                                    case 3:
                                        tmp.setImageResource(R.drawable.lava_kill);
                                        break;
                                    case 4:
                                        tmp.setImageResource(R.drawable.sand_kill);
                                        break;
                                }
                            }
                            Log.d("asdasd", "2");
                            break;
                        case 1:
                            if(x!=0 || y!=0) {
                            switch (player1_pers) {
                                case 1:
                                    tmp.setImageResource(R.drawable.grnd_black);
                                    break;
                                case 2:
                                    tmp.setImageResource(R.drawable.grnd_grace);
                                    break;
                                case 3:
                                    tmp.setImageResource(R.drawable.grnd_lava);
                                    break;
                                case 4:
                                    tmp.setImageResource(R.drawable.grnd_sand);
                                    break;
                            }
                            }
                            Log.d("asdasd", "1");
                            break;
                        case -1:
                            if(x!=Width-1 || y!=Height-1) {
                                switch (player2_pers) {
                                    case 1:
                                        tmp.setImageResource(R.drawable.grnd_black);
                                        break;
                                    case 2:
                                        tmp.setImageResource(R.drawable.grnd_grace);
                                        break;
                                    case 3:
                                        tmp.setImageResource(R.drawable.grnd_lava);
                                        break;
                                    case 4:
                                        tmp.setImageResource(R.drawable.grnd_sand);
                                        break;
                                }
                            }
                            Log.d("asdasd", "-1");
                            break;
                        case -2:
                            if (x==Width-1 && y==Height-1) {
                                switch (player2_pers) {
                                    case 1:     tmp.setImageResource(R.drawable.ctl_black_die);
                                        break;
                                    case 2:     tmp.setImageResource(R.drawable.ctl_grace_die);
                                        break;
                                    case 3:     tmp.setImageResource(R.drawable.ctl_lava_die);
                                        break;
                                    case 4:     tmp.setImageResource(R.drawable.ctl_sand_die);
                                        break;
                                }
                            }
                            else {
                                switch (player1_pers) {                                             ////////////////////////////////////////////
                                    case 1:
                                        tmp.setImageResource(R.drawable.black_kill);
                                        break;
                                    case 2:
                                        tmp.setImageResource(R.drawable.grace_kill);
                                        break;
                                    case 3:
                                        tmp.setImageResource(R.drawable.lava_kill);
                                        break;
                                    case 4:
                                        tmp.setImageResource(R.drawable.sand_kill);
                                        break;
                                }
                            }
                                        Log.d("asdasd", "-2");
                            break;
                    }
                    Log.d("asdasd", "unpressed");
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


    private boolean ReasonsToPut(int x, int y) {                                                    // проверка постановки клопа
        boolean b = false;
        int d;
        if (step % 6 == 1 || step % 6 == 2 || step % 6 == 3) {
            d = -1;
        } else {
            d = 1;
        }
        try {
            b = b || (d == buttons[x - 1][y].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x - 1][y - 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x - 1][y + 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x][y - 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x][y + 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x + 1][y - 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x + 1][y].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x + 1][y + 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }

        return b;
    }


    private boolean ReasonsToEat(int x, int y) {                                                    // проверка съедания клопа
        boolean b = false;
        int d;
        if (step % 6 == 1 || step % 6 == 2 || step % 6 == 3) {
            d = 2;
        } else {
            d = -2;
        }

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
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x - 1][y - 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x - 1][y + 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x][y - 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x][y + 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x + 1][y - 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x + 1][y].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
        }
        try {
            b = b || (d == buttons[x + 1][y + 1].getState());
        } catch (IndexOutOfBoundsException e) {
            Log.d("i gotcha:", "promahnulis' mimo imdexa");
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
}
