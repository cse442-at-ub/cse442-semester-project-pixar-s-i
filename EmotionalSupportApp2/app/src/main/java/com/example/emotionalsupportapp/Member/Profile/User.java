package com.example.emotionalsupportapp.Member.Profile;

public class User {

    private String username;
    private String first_name;
    private String last_name;
    private String lat;
    private String lon;

    public User(String _username,String _lat,String _lon,String first_name,String last_name){
        username = _username;
        this.first_name = first_name;
        this.last_name = last_name;
        lat = _lat;
        lon = _lon;
    }

    public String getLat() {
        return lat;
    }

    public String getUsername() {
        return username;
    }

    public String getLon() {
        return lon;
    }

    public String getFirst_name(){
        return first_name;
    }

    public String getLast_name(){ return last_name; }
}
