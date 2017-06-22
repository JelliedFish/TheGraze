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
    static int step = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        final List<CustomButton> fields = new ArrayList<CustomButton>();

        for (int i = 0; i < Height * Width; i++) {
            CustomButton tmp = new CustomButton(getBaseContext());
            tmp.setImageResource(R.drawable.grnd_main);
            tmp.setImageAlpha(210);
            tmp.setCheckable(true);
            fields.add(tmp);
        }

        int l = 0;

        for (int i = 0; i < Height; i++) {
            for (int j = 0; j < Width; j++) {
                buttons[j][i] = fields.get(l);
                l++;
            }
        }
        fields.get(0).setState(1);
        fields.get(Height * Width - 1).setState(-1);
        fields.get(0).setImageResource(R.drawable.ctl_grace);
        fields.get(Height * Width - 1).setImageResource(R.drawable.ctl_black);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new CustomAdapter(this, fields));
        gridView.setNumColumns(Width);


        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        //displayMetrics.widthPixels;
        //displayMetrics.heightPixels;
        LinearLayout wrapperView = (LinearLayout) findViewById(R.id.wrapper);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


        int GridHeight = (displayMetrics.widthPixels - 60) / Width * Height;


        layoutParams.setMargins(30, (displayMetrics.heightPixels - GridHeight) / 3, 30, (displayMetrics.heightPixels - GridHeight) / 3 * 2);
        wrapperView.setLayoutParams(layoutParams);




        for (int position = 0; position < Height * Width; position++) {

            final CustomButton tmp = fields.get(position);
            final int x = position % Width;
            final int y = position / Width;

            tmp.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {

                                           Log.d("check_the_trouble", "pressed");


                                           step++;

                                           if (tmp.getState() != -2 && tmp.getState() != 2) {
                                               //
                                               //если ходит 1ый
                                               //
                                               if ((step % 6 == 1 || step % 6 == 2 || step % 6 == 3)) {

                                                   if (ReasonsToPut(x, y)) {
                                                       if (tmp.getState() == -1) {
                                                           step--;
                                                       } else {
                                                           if (tmp.getState() == 1) {
                                                               tmp.setState(2);
                                                           } else {
                                                               tmp.setState(-1);
                                                           }
                                                       }
                                                   } else {
                                                       if (ReasonsToEat(x, y)) {
                                                           if (tmp.getState() == 1) {
                                                               tmp.setState(2);
                                                           }
                                                       } else {
                                                           step--;
                                                       }
                                                   }
                                               } else

                                               //
                                               //если ходит 2ой
                                               //

                        {
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


                    clear();

                    switch (tmp.getState()) {
                        case 2:
                            if (x!=0 && y!=0) {
                                tmp.setImageResource(R.drawable.black_kill);
                            }
                            else {
                                tmp.setImageResource(R.drawable.ctl_grace_die);
                            }

                            Log.d("asdasd", "2");
                            break;
                        case 1:
                            if (x!=0 && y!=0) {
                            tmp.setImageResource(R.drawable.grnd_grace);
                        }
                            Log.d("asdasd", "1");
                            break;
                        case -1:
                            if (x!= Width-1 && y!=Height-1){
                            tmp.setImageResource(R.drawable.grnd_sand);
                                }
                            Log.d("asdasd", "-1");
                            break;
                        case -2:
                          if  (x!=Width-1 && y!=Height-1) {
                            tmp.setImageResource(R.drawable.grace_kill);
                        }
                        else {
                              tmp.setImageResource(R.drawable.ctl_black_die);
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

    ////
    //
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
        if (x > 0 && y > 0 && x < Width - 1 && y < Height - 1) {
            b = (d == buttons[x + 1][y + 1].getState()) || (d == buttons[x + 1][y].getState()) ||
                    (d == buttons[x + 1][y - 1].getState()) || (d == buttons[x][y + 1].getState()) ||
                    (d == buttons[x][y - 1].getState()) || (d == buttons[x - 1][y + 1].getState()) ||
                    (d == buttons[x - 1][y].getState()) || (d == buttons[x - 1][y - 1].getState());
        }
        if (x == 0 && y < Height - 1 && y > 0) {
            b = (d == buttons[x + 1][y + 1].getState()) || (d == buttons[x + 1][y].getState()) ||
                    (d == buttons[x + 1][y - 1].getState()) || (d == buttons[x][y + 1].getState()) ||
                    (d == buttons[x][y - 1].getState());
        }
        if (x == Width - 1 && y < Height - 1 && y > 0) {
            b = (d == buttons[x][y + 1].getState()) ||
                    (d == buttons[x][y - 1].getState()) || (d == buttons[x - 1][y + 1].getState()) ||
                    (d == buttons[x - 1][y].getState()) || (d == buttons[x - 1][y - 1].getState());
        }
        if (y == 0 && x > 0 && x < Width - 1) {
            b = (d == buttons[x + 1][y].getState()) || (d == buttons[x + 1][y + 1].getState()) ||
                    (d == buttons[x][y + 1].getState()) || (d == buttons[x - 1][y + 1].getState()) ||
                    (d == buttons[x - 1][y].getState());
        }
        if (y == Height - 1 && x > 0 && x < Width - 1) {
            b = (d == buttons[x + 1][y].getState()) || (d == buttons[x + 1][y - 1].getState()) ||
                    (d == buttons[x][y - 1].getState()) || (d == buttons[x - 1][y - 1].getState()) ||
                    (d == buttons[x - 1][y].getState());
        }
        if (y == 0 & x == 0) {
            b = (d == buttons[0][1].getState()) || (d == buttons[1][1].getState()) ||
                    (d == buttons[1][0].getState());
        }
        if (y == Height - 1 & x == 0) {
            b = (d == buttons[0][Height - 2].getState()) || (d == buttons[1][Height - 2].getState()) ||
                    (d == buttons[1][Height - 1].getState());
        }
        if (y == 0 && x == Width - 1) {
            b = (d == buttons[Width - 1][1].getState()) || (d == buttons[Width - 2][1].getState()) ||
                    (d == buttons[Width - 2][0].getState());
        }
        if (y == Height - 1 & x == Width - 1) {
            b = (d == buttons[Width - 1][Height - 2].getState()) || (d == buttons[Width - 2][Height - 2].getState()) ||
                    (d == buttons[Width - 2][Height - 1].getState());
        }
        return b;
    }


    private boolean ReasonsToEat(int x, int y) {
        boolean b = false;
        int d;
        if (step % 6 == 1 || step % 6 == 2 || step % 6 == 3) {
            d = 2;
        } else {
            d = -2;
        }
        if (x > 0 && y > 0 && x < Width - 1 && y < Height - 1) {
            b = (d == buttons[x + 1][y + 1].getState()&&checkActivity(x+1,y+1)) || (d == buttons[x + 1][y].getState()&&checkActivity(x+1,y)) ||
                    (d == buttons[x + 1][y - 1].getState()&&checkActivity(x+1,y-1)) || (d == buttons[x][y + 1].getState()&&checkActivity(x,y+1)) ||
                    (d == buttons[x][y - 1].getState()&&checkActivity(x,y-1)) || (d == buttons[x - 1][y + 1].getState()&&checkActivity(x-1,y+1)) ||
                    (d == buttons[x - 1][y].getState()&&checkActivity(x-1,y)) || (d == buttons[x - 1][y - 1].getState()&&checkActivity(x-1,y-1));
        }


        if (x == 0 && y < Height - 1 && y > 0) {
            b = (d == buttons[x + 1][y + 1].getState()) || (d == buttons[x + 1][y].getState()) ||
                    (d == buttons[x + 1][y - 1].getState()) || (d == buttons[x][y + 1].getState()) ||
                    (d == buttons[x][y - 1].getState());
        }
        if (x == Width - 1 && y < Height - 1 && y > 0) {
            b = (d == buttons[x][y + 1].getState()) ||
                    (d == buttons[x][y - 1].getState()) || (d == buttons[x - 1][y + 1].getState()) ||
                    (d == buttons[x - 1][y].getState()) || (d == buttons[x - 1][y - 1].getState());
        }
        if (y == 0 && x > 0 && x < Width - 1) {
            b = (d == buttons[x + 1][y].getState()) || (d == buttons[x + 1][y + 1].getState()) ||
                    (d == buttons[x][y + 1].getState()) || (d == buttons[x - 1][y + 1].getState()) ||
                    (d == buttons[x - 1][y].getState());
        }
        if (y == Height - 1 && x > 0 && x < Width - 1) {
            b = (d == buttons[x + 1][y].getState()) || (d == buttons[x + 1][y - 1].getState()) ||
                    (d == buttons[x][y - 1].getState()) || (d == buttons[x - 1][y - 1].getState()) ||
                    (d == buttons[x - 1][y].getState());
        }
        if (y == 0 & x == 0) {
            b = (d == buttons[0][1].getState()) || (d == buttons[1][1].getState()) ||
                    (d == buttons[1][0].getState());
        }
        if (y == Height - 1 & x == 0) {
            b = (d == buttons[0][Height - 2].getState()) || (d == buttons[1][Height - 2].getState()) ||
                    (d == buttons[1][Height - 1].getState());
        }
        if (y == 0 && x == Width - 1) {
            b = (d == buttons[Width - 1][1].getState()) || (d == buttons[Width - 2][1].getState()) ||
                    (d == buttons[Width - 2][0].getState());
        }
        if (y == Height - 1 & x == Width - 1) {
            b = (d == buttons[Width - 1][Height - 2].getState()) || (d == buttons[Width - 2][Height - 2].getState()) ||
                    (d == buttons[Width - 2][Height - 1].getState());
        }
        return b;
    }

    public boolean checkActivity(int x,int y){
        boolean result=false;
        boolean result1,result2,result3,result4,result5,result6,result7,result8;
        result1=false;
        result2=false;
        result3=false;
        result4=false;
        result5=false;
        result6=false;
        result7=false;
        result8=false;

        boolean main_flg=false;
        CustomButton tmp=buttons[x][y];
        tmp.setCheckable(false);
        Log.d("L","B");
        main_flg=ReasonsToPut(x,y);
        if (main_flg==true){
            result=true;
            Log.d("N","True");
        }else{
            if(existEatenNear(x,y)){
                Log.d("L","Yes");





                if (x > 0 && y > 0 && x < Width - 1 && y < Height - 1) {
                    if(buttons[x + 1][y + 1].getState()==tmp.getState()&&buttons[x+1][y+1].getCheckable()) {
                        result1=checkActivity(x+1,y+1);
                    }


                    if(buttons[x][y + 1].getState()==tmp.getState()&&buttons[x][y+1].getCheckable()) {
                        result2=checkActivity(x,y+1);
                    }


                    if(buttons[x -1][y+1].getState()==tmp.getState()&&buttons[x-1][y+1].getCheckable()) {
                        result3=checkActivity(x-1,y+1);
                    }


                    if(buttons[x-1][y].getState()==tmp.getState()&&buttons[x-1][y].getCheckable()) {
                        result4=checkActivity(x-1,y);
                    }


                    if(buttons[x+1][y].getState()==tmp.getState()&&buttons[x+1][y].getCheckable()) {
                        result5=checkActivity(x+1,y);
                    }


                    if(buttons[x -1][y-1].getState()==tmp.getState()&&buttons[x-1][y-1].getCheckable()) {
                        result6=checkActivity(x-1,y-1);
                    }


                    if(buttons[x + 1][y - 1].getState()==tmp.getState()&&buttons[x+1][y-1].getCheckable()) {
                        result7=checkActivity(x+1,y-1);
                    }


                    if(buttons[x ][y - 1].getState()==tmp.getState()&&buttons[x][y-1].getCheckable()) {
                        result8=checkActivity(x,y-1);
                    }

                 return (result1|| result2 || result3 || result4 || result5 || result6 || result7 || result8);
                }







            }else{
                result=false;
                Log.d("aksdahfskjashf", "false");
            }
        }




        return result;
    }

    public boolean existEatenNear(int x,int y){
        int xy=buttons[x][y].getState();

        boolean b=false;
        if (x > 0 && y > 0 && x < Width - 1 && y < Height - 1) {
            b = (xy == buttons[x + 1][y + 1].getState()) || (xy == buttons[x + 1][y].getState()) ||
                    (xy == buttons[x + 1][y - 1].getState()) || (xy == buttons[x][y + 1].getState()) ||
                    (xy == buttons[x][y - 1].getState()) || (xy == buttons[x - 1][y + 1].getState()) ||
                    (xy == buttons[x - 1][y].getState()) || (xy == buttons[x - 1][y - 1].getState());
        }
        if (x == 0 && y < Height - 1 && y > 0) {
            b = (xy == buttons[x + 1][y + 1].getState()) || (xy == buttons[x + 1][y].getState()) ||
                    (xy == buttons[x + 1][y - 1].getState()) || (xy == buttons[x][y + 1].getState()) ||
                    (xy == buttons[x][y - 1].getState());
        }
        if (x == Width - 1 && y < Height - 1 && y > 0) {
            b = (xy == buttons[x][y + 1].getState()) ||
                    (xy == buttons[x][y - 1].getState()) || (xy == buttons[x - 1][y + 1].getState()) ||
                    (xy == buttons[x - 1][y].getState()) || (xy == buttons[x - 1][y - 1].getState());
        }
        if (y == 0 && x > 0 && x < Width - 1) {
            b = (xy == buttons[x + 1][y].getState()) || (xy == buttons[x + 1][y + 1].getState()) ||
                    (xy == buttons[x][y + 1].getState()) || (xy == buttons[x - 1][y + 1].getState()) ||
                    (xy == buttons[x - 1][y].getState());
        }
        if (y == Height - 1 && x > 0 && x < Width - 1) {
            b = (xy == buttons[x + 1][y].getState()) || (xy == buttons[x + 1][y - 1].getState()) ||
                    (xy == buttons[x][y - 1].getState()) || (xy == buttons[x - 1][y - 1].getState()) ||
                    (xy == buttons[x - 1][y].getState());
        }
        if (y == 0 & x == 0) {
            b = (xy == buttons[0][1].getState()) || (xy == buttons[1][1].getState()) ||
                    (xy == buttons[1][0].getState());
        }
        if (y == Height - 1 & x == 0) {
            b = (xy == buttons[0][Height - 2].getState()) || (xy == buttons[1][Height - 2].getState()) ||
                    (xy == buttons[1][Height - 1].getState());
        }
        if (y == 0 && x == Width - 1) {
            b = (xy == buttons[Width - 1][1].getState()) || (xy == buttons[Width - 2][1].getState()) ||
                    (xy == buttons[Width - 2][0].getState());
        }
        if (y == Height - 1 & x == Width - 1) {
            b = (xy == buttons[Width - 1][Height - 2].getState()) || (xy == buttons[Width - 2][Height - 2].getState()) ||
                    (xy == buttons[Width - 2][Height - 1].getState());
        }
        return b;

    }

    public void clear(){
        for(int k1=0;k1<Width;k1++){
            for(int k2=0;k2<Height;k2++){
                buttons[k1][k2].setCheckable(true);
            }
        }
    }
}
