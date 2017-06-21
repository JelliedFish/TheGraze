package com.example.myapplication.Additionals;

public class Point {

    int x;
    int y;

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
}