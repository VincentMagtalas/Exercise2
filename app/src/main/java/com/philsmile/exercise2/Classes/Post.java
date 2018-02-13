package com.philsmile.exercise2.Classes;

/**
 * Created by philsmile on 2/13/2018.
 */

public class Post {

    String ID,UserID,Title,Body;

    public Post(String ID, String userID, String title, String body) {
        this.ID = ID;
        UserID = userID;
        Title = title;
        Body = body;
    }

    public String getID() {
        return ID;
    }

    public String getUserID() {
        return UserID;
    }

    public String getTitle() {
        return Title;
    }

    public String getBody() {
        return Body;
    }
}
