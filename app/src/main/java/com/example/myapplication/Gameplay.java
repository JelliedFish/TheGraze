package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class Gameplay extends AppCompatActivity {

    static CustomButton[][] buttons=new CustomButton[7][12];
    static int step=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        final List<CustomButton> fields = new ArrayList<CustomButton>();

        for (int i = 0; i < 84; i++) {
            CustomButton tmp = new CustomButton(getBaseContext());
            tmp.setImageResource(R.drawable.grnd_main);
            fields.add(tmp);
        }

        int l = 0;

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 7; j++) {
                buttons[j][i] = fields.get(l);
                l++;
            }
        }
        fields.get(0).setState(1);
        fields.get(83).setState(-1);
        fields.get(0).setImageResource(R.drawable.first);
        fields.get(83).setImageResource(R.drawable.minusfirst);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new CustomAdapter(this, fields));


        for (int position=0; position< 84; position++) {

            final CustomButton tmp = fields.get(position);
            final int x = position % 7;
            final int y = position / 7;

            tmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("asda", "pressed");


                    step = step + 1;

                    if (tmp.getState() != -2 && tmp.getState() != 2) {
                        //
                        //если ходит 1ый
                        //
                        if ((step % 6 == 1 || step % 6 == 2 || step % 6 == 3)) {
                            if (ReasonsToPut(x, y)) {
                                if (tmp.getState() == -1) {
                                    step = step - 1;
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
                                    step = step - 1;
                                }
                            }
                        }else

                        //
                        //если ходит 2ой
                        //

                        {
                            if (ReasonsToPut(x, y) && tmp.getState() != -2 && tmp.getState() != 2) {
                                if (tmp.getState() == 1) {
                                    step = step - 1;
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
                                    step = step - 1;
                                }
                            }
                        }
                    }else{
                        step=step-1;
                    }

                    switch (tmp.getState()) {
                        case 2:
                            tmp.setImageResource(R.drawable.two);
                            Log.d("asdasd","2");
                            break;
                        case 1:
                            tmp.setImageResource(R.drawable.first);
                            Log.d("asdasd","1");
                            break;
                        case -1:
                            tmp.setImageResource(R.drawable.minusfirst);
                            Log.d("asdasd","-1");
                            break;
                        case -2:
                            tmp.setImageResource(R.drawable.minustwo);
                            Log.d("asdasd","-2");
                            break;
                    }
                    Log.d("asdasd","unpressed");
                }
            });
        }


    }

    //
    //
    //
    //ФУНКЦИИ
    //
    //

    private boolean ReasonsToPut(int x,int y){
        boolean b=false;
        int d;
        if(step%6==1||step%6==2||step%6==3){
            d=-1;
        }else{
            d=1;
        }
        if (x > 0 && y > 0 && x < 6 && y < 11) {
            b=(d==buttons[x+1][y+1].getState())||(d==buttons[x+1][y].getState())||
                    (d==buttons[x+1][y-1].getState())||(d==buttons[x][y+1].getState())||
                    (d==buttons[x][y-1].getState())||(d==buttons[x-1][y+1].getState())||
                    (d==buttons[x-1][y].getState())||(d==buttons[x-1][y-1].getState());
        }
        if (x == 0 && y < 11 && y > 0) {
            b=(d==buttons[x+1][y+1].getState())||(d==buttons[x+1][y].getState())||
                    (d==buttons[x+1][y-1].getState())||(d==buttons[x][y+1].getState())||
                    (d==buttons[x][y-1].getState());
        }
        if (x==6&&y<11&&y>0){
            b=(d==buttons[x][y+1].getState())||
                    (d==buttons[x][y-1].getState())||(d==buttons[x-1][y+1].getState())||
                    (d==buttons[x-1][y].getState())||(d==buttons[x-1][y-1].getState());
        }
        if (y==0&&x>0&&x<6){
            b=(d==buttons[x+1][y].getState())||(d==buttons[x+1][y+1].getState())||
                    (d==buttons[x][y+1].getState())||(d==buttons[x-1][y+1].getState())||
                    (d==buttons[x-1][y].getState());
        }
        if(y==11&&x>0&&x<6){
            b=(d==buttons[x+1][y].getState())||(d==buttons[x+1][y-1].getState())||
                    (d==buttons[x][y-1].getState())||(d==buttons[x-1][y-1].getState())||
                    (d==buttons[x-1][y].getState());
        }
        if(y==0&x==0){
            b=(d==buttons[0][1].getState())||(d==buttons[1][1].getState())||
                    (d==buttons[1][0].getState());
        }
        if(y==11&x==0){
            b=(d==buttons[0][10].getState())||(d==buttons[1][10].getState())||
                    (d==buttons[1][11].getState());
        }
        if(y==0&&x==6){
            b=(d==buttons[6][1].getState())||(d==buttons[5][1].getState())||
                    (d==buttons[5][0].getState());
        }
        if(y==11&x==6){
            b=(d==buttons[6][10].getState())||(d==buttons[5][10].getState())||
                    (d==buttons[5][11].getState());
        }
        return b;
    }


    private boolean ReasonsToEat(int x,int y){
        boolean b=false;
        int d;
        if(step%6==1||step%6==2||step%6==3){
            d=2;
        }else{
            d=-2;
        }
        if (x > 0 && y > 0 && x < 6 && y < 11) {
            b=(d==buttons[x+1][y+1].getState())||(d==buttons[x+1][y].getState())||
                    (d==buttons[x+1][y-1].getState())||(d==buttons[x][y+1].getState())||
                    (d==buttons[x][y-1].getState())||(d==buttons[x-1][y+1].getState())||
                    (d==buttons[x-1][y].getState())||(d==buttons[x-1][y-1].getState());
        }
        if (x == 0 && y < 11 && y > 0) {
            b=(d==buttons[x+1][y+1].getState())||(d==buttons[x+1][y].getState())||
                    (d==buttons[x+1][y-1].getState())||(d==buttons[x][y+1].getState())||
                    (d==buttons[x][y-1].getState());
        }
        if (x==6&&y<11&&y>0){
            b=(d==buttons[x][y+1].getState())||
                    (d==buttons[x][y-1].getState())||(d==buttons[x-1][y+1].getState())||
                    (d==buttons[x-1][y].getState())||(d==buttons[x-1][y-1].getState());
        }
        if (y==0&&x>0&&x<6){
            b=(d==buttons[x+1][y].getState())||(d==buttons[x+1][y+1].getState())||
                    (d==buttons[x][y+1].getState())||(d==buttons[x-1][y+1].getState())||
                    (d==buttons[x-1][y].getState());
        }
        if(y==11&&x>0&&x<6){
            b=(d==buttons[x+1][y].getState())||(d==buttons[x+1][y-1].getState())||
                    (d==buttons[x][y-1].getState())||(d==buttons[x-1][y-1].getState())||
                    (d==buttons[x-1][y].getState());
        }
        if(y==0&x==0){
            b=(d==buttons[0][1].getState())||(d==buttons[1][1].getState())||
                    (d==buttons[1][0].getState());
        }
        if(y==11&x==0){
            b=(d==buttons[0][10].getState())||(d==buttons[1][10].getState())||
                    (d==buttons[1][11].getState());
        }
        if(y==0&&x==6){
            b=(d==buttons[6][1].getState())||(d==buttons[5][1].getState())||
                    (d==buttons[5][0].getState());
        }
        if(y==11&x==6){
            b=(d==buttons[6][10].getState())||(d==buttons[5][10].getState())||
                    (d==buttons[5][11].getState());
        }
        return b;
    }
}
