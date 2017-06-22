package com.example.myapplication.Additionals;

import java.util.ArrayList;

public class Point {

    public int x;
    public int y;

    public Point(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    public static boolean areMatched(Point A, Point B) {
        return (A.x == B.x && A.y == B.y);
    }

    public Point move(int dx, int dy) {
        return new Point(this.x + dx, this.y + dy);
    }

    public static int getPosOfPointByCoords(ArrayList<Point> arg, Point P) {
        for (int i = 0; i < arg.size(); i++) {
            if (Point.areMatched(P, arg.get(i))) {
                return i;
            }
        }
        return -1;
    }
}