package com.example.myapplication.Additionals;

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

}
