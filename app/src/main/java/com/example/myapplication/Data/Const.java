package com.example.myapplication.Data;

public class Const {


    /* Тут хранятся константы (в основном константы, содержащие состояние какого-нибудь объекта)
     */


    //—————Состояния игровых клеток—————//


    public static final byte STATE_MISSING = -1;
    public static final byte STATE_NEUTRAL = 0;
    public static final byte STATE_ALIVE = 1;
    public static final byte STATE_KILL = 2;
    public static final byte STATE_CASTLE = 3;
    public static final byte STATE_CASTLEKILLED = 4;


    //—————Цвета (пока что используются только в ViewPatterns.setNumImg())—————//


    public static final byte COLOR_GRAY = 0;
    public static final byte COLOR_YELLOW = 1;


}
