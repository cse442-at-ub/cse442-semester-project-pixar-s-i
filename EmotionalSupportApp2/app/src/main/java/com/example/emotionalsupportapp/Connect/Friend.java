package com.example.emotionalsupportapp.Connect;

import android.os.Parcel;
import android.os.Parcelable;

public class Friend implements Parcelable {
    private String userId;
    private String userName;
    private String friendId;
    private String friendName;

    public Friend(String userId, String userName, String friendId, String friendName)  {
        this.userId = userId;
        this.userName = userName;
        this.friendId = friendId;
        this.friendName = friendName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getFriendId() {
        return friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public Friend(Parcel in){
        String[] data = new String[4];
        in.readStringArray(data);
        this.userId = data[0];
        this.userName = data[1];
        this.friendId = data[2];
        this.friendName = data[3];
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.userId, this.userName, this.friendId, this.friendName});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Friend createFromParcel(Parcel in) {
            return new Friend(in);
        }

        public Friend[] newArray(int size) {
            return new Friend[size];
        }
    };
}
