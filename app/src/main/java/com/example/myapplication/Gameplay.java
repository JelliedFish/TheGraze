package com.example.myapplication;

import android.content.Intent;
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

    final int Height = 15;
    final int Width = 10;

    CustomButton[][] buttons = new CustomButton[Width][Height];
    static int step = 0;//переменная для подсчета шагов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);


        final List<CustomButton> fields = new ArrayList<CustomButton>();//массив всех кнопок

        for (int i = 0; i < Height * Width; i++) {//заполнение всех кнопок картинками, установка прозрачности
            CustomButton tmp = new CustomButton(getBaseContext());
            tmp.setImageResource(R.drawable.grnd_main);
            tmp.setImageAlpha(210);
            tmp.setCheckable(true);
            fields.add(tmp);
        }


        int l = 0;

        for (int i = 0; i < Height; i++) {//связываем массив кнопок с двумерным массивом кнопок
            for (int j = 0; j < Width; j++) {
                buttons[j][i] = fields.get(l);
                l++;
            }
        }


        fields.get(0).setState(1);//установка для углавых клеток
        fields.get(Height * Width - 1).setState(-1);
        fields.get(0).setImageResource(R.drawable.ctl_grace);
        fields.get(Height * Width - 1).setImageResource(R.drawable.ctl_black);


        GridView gridView = (GridView) findViewById(R.id.gridView);//создаем гридвью
        gridView.setAdapter(new CustomAdapter(this, fields));//связываем гридвью и адптер
        gridView.setNumColumns(Width);//установка кол-ва колонок для гридвтю


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();//фича, необходимая для получения высоты и ширины экрана в пикселях
        //displayMetrics.widthPixels;
        //displayMetrics.heightPixels;


        LinearLayout wrapperView = (LinearLayout) findViewById(R.id.wrapper);//устанавливаем разметчик для активити
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


        int GridHeight = (displayMetrics.widthPixels - 60) / Width * Height;//просто переменная - высота гридвью


        layoutParams.setMargins(30, (displayMetrics.heightPixels - GridHeight) / 3, 30, (displayMetrics.heightPixels - GridHeight) / 3 * 2);
        wrapperView.setLayoutParams(layoutParams);//задаем месторасположение гридвью ч\з марджин


        for (int position = 0; position < Height * Width; position++) {//обрабатываем кждую кнопку

            final CustomButton tmp = fields.get(position);
            final int x = position % Width;//находим месторасположение кнопки в двумерном массиве (и на поле соотвественно)
            final int y = position / Width;//задаем эти значения переменным


            tmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {  //добавляем для каждой кнопки слушатель действия

                    Log.d("check_the_trouble", "pressed"); //лог


                    step++;//увеличиваем шаг (ход игрока)

                    if (tmp.getState() != -2 && tmp.getState() != 2) {//если мы не нажали на "мертвую" клетку, то


                        //если ходит первый
                        if ((step % 6 == 1 || step % 6 == 2 || step % 6 == 3)) {


                            if (ReasonsToPut(x, y)) {//есть ли рядом живые клопы

                                if (tmp.getState() == -1) {//нажал на своего
                                    step--;
                                } else {
                                    if (tmp.getState() == 1) {//нажал на чужого - убивает его
                                        tmp.setState(2);//изменение состояния мертвого клопа
                                    } else {
                                        tmp.setState(-1);
                                    }
                                }

                            } else {

                                if (ReasonsToEat(x, y)) {//можно ли сЪесть данную клетку
                                    if (tmp.getState() == 1) {//да
                                        tmp.setState(2);
                                    }
                                } else {//нет
                                    step--;
                                }

                            }


                        } else {


                            //если ходит второй (все аналогично)
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


                    clear();//обнуление флагов для проверки клеток с кучкой мертвых клопов

                    switch (tmp.getState()) {
                        case 2:
                            tmp.setImageResource(R.drawable.die_grace_black);
                            Log.d("asdasd", "2");
                            break;
                        case 1:
                            tmp.setImageResource(R.drawable.grnd_grace);
                            Log.d("asdasd", "1");
                            break;
                        case -1:
                            tmp.setImageResource(R.drawable.grnd_black);
                            Log.d("asdasd", "-1");
                            break;
                        case -2:
                            tmp.setImageResource(R.drawable.die_black_grace);
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


    //
    //
    //ФУНКЦИИ
    //
    //

    private boolean ReasonsToPut(int x, int y) {
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
    }//для првоерки постановки клопа


    private boolean ReasonsToEat(int x, int y) {
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
    }//для проверки съедания клопа


    public boolean checkActivity(int x, int y) {
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
    }//для проверки активности мертвых кучей клопов


    public boolean existEatenNear(int x, int y) {
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
    }//для проверки существования рядом мертвых клопов


    public void clear() {
        for (int k1 = 0; k1 < Width; k1++) {
            for (int k2 = 0; k2 < Height; k2++) {
                buttons[k1][k2].setCheckable(true);
            }
        }
    }//для обнуления флагов клеток
}
