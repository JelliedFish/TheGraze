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

    int Height=12;
    int Width=8;

    CustomButton[][] buttons=new CustomButton[Width][Height];
    static int step=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay);

        final List<CustomButton> fields = new ArrayList<CustomButton>();

        for (int i = 0; i < Height*Width; i++) {
            CustomButton tmp = new CustomButton(getBaseContext());
            tmp.setImageResource(R.drawable.grnd_main);
            fields.add(tmp);
        }

        int l = 0;

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < Width; j++) {
                buttons[j][i] = fields.get(l);
                l++;
            }
        }
        fields.get(0).setState(1);
        fields.get(Height*Width-1).setState(-1);
        fields.get(0).setImageResource(R.drawable.grnd_grace);
        fields.get(Height*Width-1).setImageResource(R.drawable.grnd_black);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new CustomAdapter(this, fields));
        gridView.setNumColumns(Width);


        for (int position=0; position< Height*Width; position++) {

            final CustomButton tmp = fields.get(position);
            final int x = position % Width;
            final int y = position / Width;

            tmp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("asda", "pressed");


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
                            tmp.setImageResource(R.drawable.grnd_grace);
                            Log.d("asdasd","1");
                            break;
                        case -1:
                            tmp.setImageResource(R.drawable.grnd_black);
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
        if (x > 0 && y > 0 && x < Width-1 && y < Height-1) {
            b=(d==buttons[x+1][y+1].getState())||(d==buttons[x+1][y].getState())||
                    (d==buttons[x+1][y-1].getState())||(d==buttons[x][y+1].getState())||
                    (d==buttons[x][y-1].getState())||(d==buttons[x-1][y+1].getState())||
                    (d==buttons[x-1][y].getState())||(d==buttons[x-1][y-1].getState());
        }
        if (x == 0 && y < Height-1 && y > 0) {
            b=(d==buttons[x+1][y+1].getState())||(d==buttons[x+1][y].getState())||
                    (d==buttons[x+1][y-1].getState())||(d==buttons[x][y+1].getState())||
                    (d==buttons[x][y-1].getState());
        }
        if (x==Width-1&&y<Height-1&&y>0){
            b=(d==buttons[x][y+1].getState())||
                    (d==buttons[x][y-1].getState())||(d==buttons[x-1][y+1].getState())||
                    (d==buttons[x-1][y].getState())||(d==buttons[x-1][y-1].getState());
        }
        if (y==0&&x>0&&x<Width-1){
            b=(d==buttons[x+1][y].getState())||(d==buttons[x+1][y+1].getState())||
                    (d==buttons[x][y+1].getState())||(d==buttons[x-1][y+1].getState())||
                    (d==buttons[x-1][y].getState());
        }
        if(y==Height-1&&x>0&&x<Width-1){
            b=(d==buttons[x+1][y].getState())||(d==buttons[x+1][y-1].getState())||
                    (d==buttons[x][y-1].getState())||(d==buttons[x-1][y-1].getState())||
                    (d==buttons[x-1][y].getState());
        }
        if(y==0&x==0){
            b=(d==buttons[0][1].getState())||(d==buttons[1][1].getState())||
                    (d==buttons[1][0].getState());
        }
        if(y==Height-1&x==0){
            b=(d==buttons[0][Height-2].getState())||(d==buttons[1][Height-2].getState())||
                    (d==buttons[1][Height-1].getState());
        }
        if(y==0&&x==Width-1){
            b=(d==buttons[Width-1][1].getState())||(d==buttons[Width-2][1].getState())||
                    (d==buttons[Width-2][0].getState());
        }
        if(y==Height-1&x==Width-1){
            b=(d==buttons[Width-1][Height-2].getState())||(d==buttons[Width-2][Height-2].getState())||
                    (d==buttons[Width-2][Height-1].getState());
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
        if (x > 0 && y > 0 && x < Width-1 && y < Height-1) {
            b=(d==buttons[x+1][y+1].getState())||(d==buttons[x+1][y].getState())||
                    (d==buttons[x+1][y-1].getState())||(d==buttons[x][y+1].getState())||
                    (d==buttons[x][y-1].getState())||(d==buttons[x-1][y+1].getState())||
                    (d==buttons[x-1][y].getState())||(d==buttons[x-1][y-1].getState());
        }
        if (x == 0 && y < Height-1 && y > 0) {
            b=(d==buttons[x+1][y+1].getState())||(d==buttons[x+1][y].getState())||
                    (d==buttons[x+1][y-1].getState())||(d==buttons[x][y+1].getState())||
                    (d==buttons[x][y-1].getState());
        }
        if (x==Width-1&&y<Height-1&&y>0){
            b=(d==buttons[x][y+1].getState())||
                    (d==buttons[x][y-1].getState())||(d==buttons[x-1][y+1].getState())||
                    (d==buttons[x-1][y].getState())||(d==buttons[x-1][y-1].getState());
        }
        if (y==0&&x>0&&x<Width-1){
            b=(d==buttons[x+1][y].getState())||(d==buttons[x+1][y+1].getState())||
                    (d==buttons[x][y+1].getState())||(d==buttons[x-1][y+1].getState())||
                    (d==buttons[x-1][y].getState());
        }
        if(y==Height-1&&x>0&&x<Width-1){
            b=(d==buttons[x+1][y].getState())||(d==buttons[x+1][y-1].getState())||
                    (d==buttons[x][y-1].getState())||(d==buttons[x-1][y-1].getState())||
                    (d==buttons[x-1][y].getState());
        }
        if(y==0&x==0){
            b=(d==buttons[0][1].getState())||(d==buttons[1][1].getState())||
                    (d==buttons[1][0].getState());
        }
        if(y==Height-1&x==0){
            b=(d==buttons[0][Height-2].getState())||(d==buttons[1][Height-2].getState())||
                    (d==buttons[1][Height-1].getState());
        }
        if(y==0&&x==Width-1){
            b=(d==buttons[Width-1][1].getState())||(d==buttons[Width-2][1].getState())||
                    (d==buttons[Width-2][0].getState());
        }
        if(y==Height-1&x==Width-1){
            b=(d==buttons[Width-1][Height-2].getState())||(d==buttons[Width-2][Height-2].getState())||
                    (d==buttons[Width-2][Height-1].getState());
        }
        return b;
    }
}
