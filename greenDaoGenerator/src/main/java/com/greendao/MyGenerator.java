package com.greendao;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

public class MyGenerator {
    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.philsmile.exercise2.db"); // Your app package name and the (.db) is the folder where the DAO files will be generated into.
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema,"../app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        addBookmarkEntity(schema);
    }

    // This is use to describe the columns of your table
    private static Entity addBookmarkEntity(final Schema schema) {
        Entity bookmark = schema.addEntity("Bookmark");
        bookmark.addIdProperty().primaryKey().autoincrement();
        bookmark.addStringProperty("postid").notNull();
        bookmark.addIntProperty("userid").notNull();
        bookmark.addStringProperty("username");
        bookmark.addStringProperty("title");
        bookmark.addStringProperty("body");
        return bookmark;
    }

}