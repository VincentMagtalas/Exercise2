package com.philsmile.exercise2.db;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END

/**
 * Entity mapped to table "BOOKMARK".
 */
@Entity
public class Bookmark {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String postid;
    private int userid;
    private String username;
    private String title;
    private String body;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    @Generated
    public Bookmark() {
    }

    public Bookmark(Long id) {
        this.id = id;
    }

    @Generated
    public Bookmark(Long id, String postid, int userid, String username, String title, String body) {
        this.id = id;
        this.postid = postid;
        this.userid = userid;
        this.username = username;
        this.title = title;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    public String getPostid() {
        return postid;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setPostid(@NotNull String postid) {
        this.postid = postid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
