package com.example.myapplication.Additionals;

public class PointAndCustomButtonConverter {

    /*
     * Класс преобразует номер клетки из CustomButton в точку и обратно
     * width - длина игрового поля (не высота!)
     */

    public static Point CBtoPoint(int CBnumber, int width) {
        return new Point(CBnumber % width, CBnumber / width);
    }

    public static int PointToCB(Point P, int width) {
        return P.x + P.y * width;
    }

}
