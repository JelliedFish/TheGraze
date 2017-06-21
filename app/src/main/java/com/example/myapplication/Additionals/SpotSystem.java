package com.example.myapplication.Additionals;

import java.util.ArrayList;

public class SpotSystem {

    public int team;
    public ArrayList<Spot> spots;
    /*
     * team - игровая команда. 1 - первая команда, -1 - вторая
     */

    public SpotSystem(int team) {
        this.team = team;
        this.spots = new ArrayList<>();
    }


    public void addNewPoint(Point newP) {

        boolean isRelatedToOthers = false;
        ArrayList<Integer> otherRelatedList = new ArrayList<>();

        for (int i = 0; i < spots.size(); i++) {
            for (Point PCheck : spots.get(i).SpottedList) {
                if (Point.areMatched(newP, PCheck)) {
                    isRelatedToOthers = true;
                    otherRelatedList.add(i);
                }
            }
        }

        if (isRelatedToOthers) {
            ArrayList<Spot> relatedSpots = new ArrayList<>();
            for (int i : otherRelatedList) {
                relatedSpots.add(spots.get(i));
                spots.remove(i);
            }
            Spot newSpot = Spot.combine(relatedSpots);

            newSpot.addPointToSpotted(newP);
            newSpot.addPointToTarget(newP.move(0,1));
            newSpot.addPointToTarget(newP.move(1,1));
            newSpot.addPointToTarget(newP.move(1,0));
            newSpot.addPointToTarget(newP.move(1,-1));
            newSpot.addPointToTarget(newP.move(0,-1));
            newSpot.addPointToTarget(newP.move(-1,-1));
            newSpot.addPointToTarget(newP.move(-1,0));
            newSpot.addPointToTarget(newP.move(-1,1));

            spots.add(newSpot);
        } else {
            Spot newSpot = new Spot();
            newSpot.addPointToSpotted(newP);
            newSpot.addPointToTarget(newP.move(0,1));
            newSpot.addPointToTarget(newP.move(1,1));
            newSpot.addPointToTarget(newP.move(1,0));
            newSpot.addPointToTarget(newP.move(1,-1));
            newSpot.addPointToTarget(newP.move(0,-1));
            newSpot.addPointToTarget(newP.move(-1,-1));
            newSpot.addPointToTarget(newP.move(-1,0));
            newSpot.addPointToTarget(newP.move(-1,1));

            spots.add(newSpot);
        }
    }
}
