package com.philsmile.exercise2.Classes;

/**
 * Created by philsmile on 2/13/2018.
 */

public class DisplayPost {

    String PostID, UserID, UserName, Title, Body;

    public DisplayPost(String postID, String userID, String userName, String title, String body) {
        PostID = postID;
        UserID = userID;
        UserName = userName;
        Title = title;
        Body = body;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPostID() {
        return PostID;
    }

    public String getUserID() {
        return UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public String getTitle() {
        return Title;
    }

    public String getBody() {
        return Body;
    }
}

