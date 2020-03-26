package com.example.emotionalsupportapp;

import android.util.Pair;

import java.util.ArrayList;

public class LocationSelector {

    ArrayList<Pair<String, Pair<Double, Double>>> listOfLocations;

    public LocationSelector(){
        this.listOfLocations = new ArrayList<Pair<String, Pair<Double, Double>>>();

    }

    /**
     * funct:   createListOfLocations
     * details: creates a list of meet up locations for user and volunteer to interact
     */
    public void createListOfLocations(){

        this.listOfLocations.add(new Pair<String, Pair<Double,Double>>("Capen 1 Entrance",new Pair<>(43.00114, -78.78956)));
        this.listOfLocations.add(new Pair<String, Pair<Double,Double>>("Mrs Rich’s Corner Cafe",new Pair<>(43.00068, -78.79168)));
        this.listOfLocations.add(new Pair<String, Pair<Double,Double>>("Knox Hall Staircase",new Pair<>(43.00102, -78.78827)));
        this.listOfLocations.add(new Pair<String, Pair<Double,Double>>("Obrien Hall First Floor",new Pair<>(43.00029, -78.78766)));
        this.listOfLocations.add(new Pair<String, Pair<Double,Double>>("Baldy Walkway Cafè",new Pair<>(43.00012, -78.78722)));
        this.listOfLocations.add(new Pair<String, Pair<Double,Double>>("Lockwood Memorial Library",new Pair<>(43.00025, -78.78637)));
        this.listOfLocations.add(new Pair<String, Pair<Double,Double>>("Student Union Welcome Center",new Pair<>(43.001411, -78.786098)));
        this.listOfLocations.add(new Pair<String, Pair<Double,Double>>("SU Courtyard",new Pair<>(43.001073, -78.787000)));
        this.listOfLocations.add(new Pair<String, Pair<Double,Double>>("Davis Hall Bansal Atrium",new Pair<>(43.002564, -78.787554)));

    }

    public ArrayList<Pair<String, Pair<Double, Double>>> getListOfLocations() {
        return listOfLocations;
    }

    /**
     * funct:           findMidPoint
     * @param user      coordinates of the user who made the request
     * @param volunteer coordinates and name of the volunteer
     * @return returns the midPoint between the user and the volunteer
     */
    public Pair<Double,Double> findMidPoint(Pair<Double, Double> user, Pair<String, Pair<Double,Double>> volunteer){
        double xCoord = ((user.first + volunteer.second.first) / 2);
        double yCoord = ((user.second + volunteer.second.second)/2);
        Pair<Double, Double> midPoint = new Pair<>(xCoord,yCoord);
        return midPoint;

    }

    public Pair<String, Pair<Double,Double>> meetUpLocationFound(Pair<Double, Double> midPoint){
        double minDistance = Double.MAX_VALUE;
        Pair<String, Pair<Double,Double>> result = new Pair<>("Dummy", new Pair<>(0.00,0.00));
        for(int x = 0; x < this.listOfLocations.size(); x++){
            if (Math.sqrt(Math.pow((listOfLocations.get(x).second.first - midPoint.first),2) + Math.pow(listOfLocations.get(x).second.second - midPoint.second,2)) < minDistance){
                minDistance = Math.sqrt(Math.pow((listOfLocations.get(x).second.first - midPoint.first),2) + Math.pow(listOfLocations.get(x).second.second - midPoint.second,2));
                result = listOfLocations.get(x);
            }
        }
        return result;

    }
}
