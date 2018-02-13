package com.philsmile.exercise2;

/**
 * Created by philsmile on 2/13/2018.
 */
import android.app.Application;

import com.philsmile.exercise2.db.DaoMaster;
import com.philsmile.exercise2.db.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Akinsete on 6/30/17.
 */

public class AppController extends Application {

    public static final boolean ENCRYPTED = true;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"bookmark-db"); //The users-db here is the name of our database.
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
