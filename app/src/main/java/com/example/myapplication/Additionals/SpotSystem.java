package com.example.myapplication.Additionals;

import android.util.Log;

import java.util.ArrayList;

public class SpotSystem {

    public int team;        // team - игровая команда. 1 - первая команда, 2 - вторая
    public ArrayList<Spot> spots;


    public SpotSystem(int team) {
        this.team = team;
        this.spots = new ArrayList<>();
    }


    public void addNewPoint(Point newP) {

        Log.d("abcw_adding", "STARTING");
        boolean isRelatedToOthers = false;
        boolean isAlreadyAdded;
        ArrayList<Integer> otherRelatedList = new ArrayList<>();
        ArrayList<Point> pointsRelatedList = new ArrayList<>();
        Log.d("abcw_adding", "1");

        for (int i = 0; i < spots.size(); i++) {
            isAlreadyAdded = false;
            for (Point PCheck : spots.get(i).TargetList) {
                if (Point.areMatched(newP, PCheck)) {
                    isRelatedToOthers = true;
                    pointsRelatedList.add(PCheck);
                    if (!isAlreadyAdded) {
                        otherRelatedList.add(i);
                        isAlreadyAdded = true;
                    }
                }
            }
        }
        Log.d("abcw_adding", "2");

        if (isRelatedToOthers) {
            Log.d("abcw_adding", "3");
            ArrayList<Spot> relatedSpots = new ArrayList<>();
            Log.d("abcw_adding", "3'1");
            for (int i : otherRelatedList) {
                Log.d("abcw_adding", "3'2");
                relatedSpots.add(spots.get(i));
                Log.d("abcw_adding", "3'3");
                spots.remove(i);
                Log.d("abcw_adding", "3'4");
            }
            Spot newSpot = Spot.combine(relatedSpots);

            Log.d("abcw_adding", "4");
            int newSize = newSpot.TargetList.size();
            for (int i = 0; i < newSize; i++) {
                if ((newP.x - newSpot.TargetList.get(i).x >= -1 && newP.x - newSpot.TargetList.get(i).x <= 1 && newP.y - newSpot.TargetList.get(i).y >= -1 && newP.y - newSpot.TargetList.get(i).y <= 1) || newSpot.TargetList.get(i).x < 0 || newSpot.TargetList.get(i).y < 0) {
                    newSpot.TargetList.remove(i);
                    i--;
                    newSize--;
                }
            }
            Log.d("abcw_adding", "5");

            newSpot.addPointToSpotted(newP);
            newSpot.safetyAddPointToTarget(newP.move(0, 1), pointsRelatedList);
            newSpot.safetyAddPointToTarget(newP.move(1, 1), pointsRelatedList);
            newSpot.safetyAddPointToTarget(newP.move(1, 0), pointsRelatedList);
            newSpot.safetyAddPointToTarget(newP.move(1, -1), pointsRelatedList);
            newSpot.safetyAddPointToTarget(newP.move(0, -1), pointsRelatedList);
            newSpot.safetyAddPointToTarget(newP.move(-1, -1), pointsRelatedList);
            newSpot.safetyAddPointToTarget(newP.move(-1, 0), pointsRelatedList);
            newSpot.safetyAddPointToTarget(newP.move(-1, 1), pointsRelatedList);
            /*newSpot.addPointToTarget(newP.move(0,1));
            newSpot.addPointToTarget(newP.move(1,1));
            newSpot.addPointToTarget(newP.move(1,0));
            newSpot.addPointToTarget(newP.move(1,-1));
            newSpot.addPointToTarget(newP.move(0,-1));
            newSpot.addPointToTarget(newP.move(-1,-1));
            newSpot.addPointToTarget(newP.move(-1,0));
            newSpot.addPointToTarget(newP.move(-1,1));*/
            Log.d("abcw_adding", "6");

            spots.add(newSpot);
            Log.d("abcw_adding", "7");
        } else {
            Log.d("abcw_adding", "8");
            Spot newSpot = new Spot();
            newSpot.addPointToSpotted(newP);
            newSpot.addPointToTarget(newP.move(0, 1));
            newSpot.addPointToTarget(newP.move(1, 1));
            newSpot.addPointToTarget(newP.move(1, 0));
            newSpot.addPointToTarget(newP.move(1, -1));
            newSpot.addPointToTarget(newP.move(0, -1));
            newSpot.addPointToTarget(newP.move(-1, -1));
            newSpot.addPointToTarget(newP.move(-1, 0));
            newSpot.addPointToTarget(newP.move(-1, 1));

            Log.d("abcw_adding", "9");
            newSpot.isActive = true;
            spots.add(newSpot);
            Log.d("abcw_adding", "10");
        }
    }

    public SpotSystem removePoint(Point P) {
        SpotSystem res = new SpotSystem(this.team);
        for (Spot spot : spots) {
            spot.removePoint(P);
            res.spots.add(spot);
        }
        return res;
    }
}
