package com.example.emotionalsupportapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Volunteer implements Parcelable, Comparable<Volunteer> {
    private String userId;
    private String userName;
    private String volunteerId;
    private String volunteerName;
    private String overallRating;

    public Volunteer(String userId, String userName, String volunteerId, String volunteerName, String overallRating) {
        this.userId = userId;
        this.userName = userName;
        this.volunteerId = volunteerId;
        this.volunteerName = volunteerName;
        this.overallRating = overallRating;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getVolunteerId() {
        return volunteerId;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public String getOverallRating() {
        return overallRating;
    }

    public Volunteer(Parcel in){
        String[] data = new String[5];
        in.readStringArray(data);
        this.userId = data[0];
        this.userName = data[1];
        this.volunteerId = data[2];
        this.volunteerName = data[3];
        this.overallRating = data[4];
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.userId, this.userName, this.volunteerId, this.volunteerName, this.overallRating});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Volunteer createFromParcel(Parcel in) {
            return new Volunteer(in);
        }

        public Volunteer[] newArray(int size) {
            return new Volunteer[size];
        }
    };

    @Override
    public int compareTo(Volunteer v) {
        return Integer.parseInt(v.overallRating) - Integer.parseInt(this.overallRating);
    }
}
