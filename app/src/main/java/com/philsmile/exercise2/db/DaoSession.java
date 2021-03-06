package com.philsmile.exercise2.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.philsmile.exercise2.db.Bookmark;

import com.philsmile.exercise2.db.BookmarkDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig bookmarkDaoConfig;

    private final BookmarkDao bookmarkDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        bookmarkDaoConfig = daoConfigMap.get(BookmarkDao.class).clone();
        bookmarkDaoConfig.initIdentityScope(type);

        bookmarkDao = new BookmarkDao(bookmarkDaoConfig, this);

        registerDao(Bookmark.class, bookmarkDao);
    }
    
    public void clear() {
        bookmarkDaoConfig.clearIdentityScope();
    }

    public BookmarkDao getBookmarkDao() {
        return bookmarkDao;
    }

}
