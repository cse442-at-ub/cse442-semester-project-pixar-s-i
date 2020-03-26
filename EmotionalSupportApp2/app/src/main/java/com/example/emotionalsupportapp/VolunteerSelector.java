package com.example.emotionalsupportapp;


import android.util.Pair;

import java.util.ArrayList;

public class VolunteerSelector {


    ArrayList<Pair<String,Pair<Double,Double>>> listOfVolunteers;
    public VolunteerSelector(){
        listOfVolunteers = new ArrayList<Pair<String, Pair<Double, Double>>>();
    }

    /**
     * funct:   createListOfVolunteers
     * details: creates a list of volunteers paired with their contents
     */
    public void createListOfVolunteers(){
        this.listOfVolunteers.add(new Pair<String, Pair<Double, Double>>("Jasmine Yen", new Pair<Double, Double>(43.001338, -78.786164)));
        this.listOfVolunteers.add(new Pair<String, Pair<Double, Double>>("Jackson Bob", new Pair<>(43.001121, -78.787468)));
        this.listOfVolunteers.add(new Pair<String, Pair<Double,Double>>("Heaven Thompson", new Pair<>(43.001617, -78.787653)));
    }

    /**
     * funct:   getListOfVolunteers
     * @return  returns a list of Volunteers paired with their coordinates
     */
    public ArrayList<Pair<String, Pair<Double,Double>>> getListOfVolunteers(){
        return  this.listOfVolunteers;
    }

    /**
     * funct:   findDistance
     * @param   user - pair of coordinates of the user who made a requestr
     * @param   voluteer - pair of Volunteer paired with their coordinates
     * @return  a double representing the distance between the user and the volunteer
     */
    public double findDistance(Pair<Double, Double> user ,  Pair<String, Pair<Double, Double>> voluteer){

        double distance;

        distance = Math.sqrt(Math.pow((voluteer.second.first - user.first), 2) + Math.pow((voluteer.second.second - user.second ),2));

        return distance;
    }







}
