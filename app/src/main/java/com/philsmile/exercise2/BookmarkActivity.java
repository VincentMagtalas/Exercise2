package com.philsmile.exercise2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.philsmile.exercise2.Adapters.BookmarkRecyclerViewAdapter;
import com.philsmile.exercise2.Adapters.PostRecyclerViewAdapter;
import com.philsmile.exercise2.Classes.DisplayPost;
import com.philsmile.exercise2.db.Bookmark;
import com.philsmile.exercise2.db.BookmarkDao;
import com.philsmile.exercise2.db.DaoSession;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {

    ArrayList<DisplayPost> arrayListDisplay = new ArrayList<>();
    ArrayList<Bookmark> arrayListBookmark = new ArrayList<>();
    RecyclerView rv;

    DaoSession daoSession;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        getSupportActionBar().setTitle("Bookmarks");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = getApplicationContext();
        daoSession = ((AppController) getApplication()).getDaoSession();
        rv = (RecyclerView) findViewById(R.id.post_rv);


        fetchBookmarkList();

    }

    private void fetchBookmarkList(){
        arrayListDisplay.clear();
        arrayListBookmark.clear();
        //// Get the entity dao we need to work with.
        BookmarkDao bookmarkDao = daoSession.getBookmarkDao();

        //// Load all items
        arrayListBookmark.addAll(bookmarkDao.loadAll());
        for(int i=0; i<arrayListBookmark.size(); i++){
            Bookmark bookmark = arrayListBookmark.get(i);

            String postid = bookmark.getPostid().toString();
            String userid = String.valueOf(bookmark.getId());
            String username = bookmark.getUsername().toString();
            String title = bookmark.getTitle().toString();
            String body = bookmark.getBody().toString();

            arrayListDisplay.add(new DisplayPost(postid,userid,username,title,body));

        }

        /// Notify our adapter of changes
        BookmarkRecyclerViewAdapter bookmarkRecyclerViewAdapter = new BookmarkRecyclerViewAdapter(arrayListDisplay,mContext);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(bookmarkRecyclerViewAdapter);
    }
}
