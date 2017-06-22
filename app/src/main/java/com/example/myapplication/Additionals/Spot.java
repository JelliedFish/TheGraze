package com.example.myapplication.Additionals;

import android.util.Log;

import java.util.ArrayList;

public class Spot {

    public boolean isActive;
    public ArrayList<Point> SpottedList;
    public ArrayList<Point> TargetList;

    public Spot() {
        this.isActive = false;
        this.SpottedList = new ArrayList<>();
        this.TargetList = new ArrayList<>();
    }

    public Spot addPointToSpotted(Point P) {
        this.SpottedList.add(P);
        return this;
    }

    public Spot addPointToTarget(Point P) {
        this.TargetList.add(P);
        return this;
    }

    public Spot safetyAddPointToTarget(Point P, ArrayList<Point> blackList) {
        boolean flg = true;
        for (Point PCheck : blackList) {
            if (Point.areMatched(P, PCheck)) {
                flg = false;
                break;
            }
        }
        if (flg)
            this.TargetList.add(P);
        return this;
    }

    public static Spot combine(ArrayList<Spot> combiningSpots) {
        Spot res = new Spot();
        for (Spot sp : combiningSpots) {
            res.isActive |= sp.isActive;
            for (Point P : sp.SpottedList) {
                res.addPointToSpotted(P);
            }
            for (Point P : sp.TargetList) {
                res.addPointToTarget(P);
            }
        }
        return res;
    }

    public Spot removePoint(Point P) {

        boolean leftUp_status = false;
        boolean up_status = false;
        boolean rightUp_status = false;
        boolean right_status = false;
        boolean rightDown_status = false;
        boolean down_status = false;
        boolean leftDown_status = false;
        boolean left_status = false;

        for (Point PCheck : this.SpottedList) {
            if (PCheck.x - P.x == 0 && PCheck.y - P.y == 1) {
                left_status = true;
                leftUp_status = true;
                up_status = true;
                rightUp_status = true;
                right_status = true;
            }
            if (PCheck.x - P.x == -1 && PCheck.y - P.y == 1) {
                left_status = true;
                leftUp_status = true;
                up_status = true;
            }
            if (PCheck.x - P.x == -1 && PCheck.y - P.y == 0) {
                down_status = true;
                leftDown_status = true;
                left_status = true;
                leftUp_status = true;
                up_status = true;
            }
            if (PCheck.x - P.x == -1 && PCheck.y - P.y == -1) {
                down_status = true;
                leftDown_status = true;
                left_status = true;
            }
            if (PCheck.x - P.x == 0 && PCheck.y - P.y == -1) {
                right_status = true;
                rightDown_status = true;
                down_status = true;
                leftDown_status = true;
                left_status = true;
            }
            if (PCheck.x - P.x == 1 && PCheck.y - P.y == -1) {
                right_status = true;
                rightDown_status = true;
                down_status = true;
            }
            if (PCheck.x - P.x == 1 && PCheck.y - P.y == 0) {
                up_status = true;
                rightUp_status = true;
                right_status = true;
                rightDown_status = true;
                down_status = true;
            }
            if (PCheck.x - P.x == 1 && PCheck.y - P.y == 1) {
                up_status = true;
                rightUp_status = true;
                right_status = true;
            }
        }
        Log.d("abcw", "REMOVING OF " + P.x + " " + P.y);
        if (Point.getPosOfPointByCoords(this.SpottedList, P) != -1) {
            this.SpottedList.remove(Point.getPosOfPointByCoords(this.SpottedList, P));
        }
        Log.d("abcw", "REMOVED " + P.x + " " + P.y);
        if (!leftUp_status && Point.getPosOfPointByCoords(this.TargetList, P.move(-1, 1)) != -1) {
            this.TargetList.remove(Point.getPosOfPointByCoords(this.TargetList, P.move(-1, 1)));
            Log.d("abcw", "removed " + P.move(-1, 1).x + " " + P.move(-1, 1).y);
        }
        if (!up_status && Point.getPosOfPointByCoords(this.TargetList, P.move(0, 1)) != -1) {
            this.TargetList.remove(Point.getPosOfPointByCoords(this.TargetList, P.move(0, 1)));
            Log.d("abcw", "removed " + P.move(0, 1).x + " " + P.move(0, 1).y);
        }
        if (!rightUp_status && Point.getPosOfPointByCoords(this.TargetList, P.move(1, 1)) != -1) {
            this.TargetList.remove(Point.getPosOfPointByCoords(this.TargetList, P.move(1, 1)));
            Log.d("abcw", "removed " + P.move(1, 1).x + " " + P.move(1, 1).y);
        }
        if (!right_status&& Point.getPosOfPointByCoords(this.TargetList, P.move(1, 0)) != -1) {
            this.TargetList.remove(Point.getPosOfPointByCoords(this.TargetList, P.move(1, 0)));
            Log.d("abcw", "removed " + P.move(1, 0).x + " " + P.move(1, 0).y);
        }
        if (!rightDown_status&& Point.getPosOfPointByCoords(this.TargetList, P.move(1, -1)) != -1) {
            this.TargetList.remove(Point.getPosOfPointByCoords(this.TargetList, P.move(1, -1)));
            Log.d("abcw", "removed " + P.move(1, -1).x + " " + P.move(1, -1).y);
        }
        if (!down_status&& Point.getPosOfPointByCoords(this.TargetList, P.move(0, -1)) != -1) {
            this.TargetList.remove(Point.getPosOfPointByCoords(this.TargetList, P.move(0, -1)));
            Log.d("abcw", "removed " + P.move(0, -1).x + " " + P.move(0, -1).y);
        }
        if (!leftDown_status&& Point.getPosOfPointByCoords(this.TargetList, P.move(-1, -1)) != -1) {
            this.TargetList.remove(Point.getPosOfPointByCoords(this.TargetList, P.move(-1, -1)));
            Log.d("abcw", "removed " + P.move(-1, -1).x + " " + P.move(-1, -1).y);
        }
        if (!left_status&& Point.getPosOfPointByCoords(this.TargetList, P.move(-1, 0)) != -1) {
            this.TargetList.remove(Point.getPosOfPointByCoords(this.TargetList, P.move(-1, 0)));
            Log.d("abcw", "removed " + P.move(-1, 0).x + " " + P.move(-1, 0).y);
        }

        return this;
    }
}
