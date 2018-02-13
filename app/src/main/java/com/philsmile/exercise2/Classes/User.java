package com.philsmile.exercise2.Classes;

/**
 * Created by philsmile on 2/13/2018.
 */

public class User {

    String UserID, Name;

    public User(String userID, String name) {
        UserID = userID;
        Name = name;
    }

    public String getUserID() {
        return UserID;
    }

    public String getName() {
        return Name;
    }
}
