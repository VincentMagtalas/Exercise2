package com.philsmile.exercise2.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BOOKMARK".
*/
public class BookmarkDao extends AbstractDao<Bookmark, Long> {

    public static final String TABLENAME = "BOOKMARK";



    /**
     * Properties of entity Bookmark.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Postid = new Property(1, String.class, "postid", false, "POSTID");
        public final static Property Userid = new Property(2, int.class, "userid", false, "USERID");
        public final static Property Username = new Property(3, String.class, "username", false, "USERNAME");
        public final static Property Title = new Property(4, String.class, "title", false, "TITLE");
        public final static Property Body = new Property(5, String.class, "body", false, "BODY");
    }


    public BookmarkDao(DaoConfig config) {
        super(config);
    }
    
    public BookmarkDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BOOKMARK\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"POSTID\" TEXT NOT NULL ," + // 1: postid
                "\"USERID\" INTEGER NOT NULL ," + // 2: userid
                "\"USERNAME\" TEXT," + // 3: username
                "\"TITLE\" TEXT," + // 4: title
                "\"BODY\" TEXT);"); // 5: body
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BOOKMARK\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Bookmark entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getPostid());
        stmt.bindLong(3, entity.getUserid());
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(4, username);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(5, title);
        }
 
        String body = entity.getBody();
        if (body != null) {
            stmt.bindString(6, body);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Bookmark entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getPostid());
        stmt.bindLong(3, entity.getUserid());
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(4, username);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(5, title);
        }
 
        String body = entity.getBody();
        if (body != null) {
            stmt.bindString(6, body);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Bookmark readEntity(Cursor cursor, int offset) {
        Bookmark entity = new Bookmark( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // postid
            cursor.getInt(offset + 2), // userid
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // username
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // title
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // body
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Bookmark entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPostid(cursor.getString(offset + 1));
        entity.setUserid(cursor.getInt(offset + 2));
        entity.setUsername(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTitle(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setBody(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Bookmark entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Bookmark entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Bookmark entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}