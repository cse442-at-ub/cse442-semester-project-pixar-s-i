package com.example.emotionalsupportapp.Member.Profile;

public class User {

    private String username;
    private String first_name;
    private String last_name;
    private String lat;
    private String lon;

    public User(String _username,String _lat,String _lon){
        username = _username;
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
}
