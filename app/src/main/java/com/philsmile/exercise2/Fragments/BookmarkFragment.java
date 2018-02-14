package com.philsmile.exercise2.Fragments;

/**
 * Created by philsmile on 2/14/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.philsmile.exercise2.Adapters.BookmarkRecyclerViewAdapter;
import com.philsmile.exercise2.Adapters.PostRecyclerViewAdapter;
import com.philsmile.exercise2.AppController;
import com.philsmile.exercise2.BookmarkActivity;
import com.philsmile.exercise2.Classes.DisplayPost;
import com.philsmile.exercise2.R;
import com.philsmile.exercise2.db.Bookmark;
import com.philsmile.exercise2.db.BookmarkDao;
import com.philsmile.exercise2.db.DaoSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class BookmarkFragment extends Fragment{

    ArrayList<DisplayPost> arrayListDisplay = new ArrayList<>();
    ArrayList<Bookmark> arrayListBookmark = new ArrayList<>();
    ArrayList<DisplayPost> arrayListDisplaySearch = new ArrayList<>();
    RecyclerView rv;

    DaoSession daoSession;

    Context mContext;

    private Toolbar mToolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;

    @SuppressLint("ValidFragment")
    public BookmarkFragment(Context ctx, Toolbar mToolbar) {
        mContext = ctx;
        mToolbar = mToolbar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_main, parent, false);
        setHasOptionsMenu(true);

        daoSession = ((AppController) getActivity().getApplication()).getDaoSession();
        rv = (RecyclerView) v.findViewById(R.id.post_rv);

        fetchBookmarkList();

        return v;
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                //Toast.makeText(mContext,"Search Clicked in bookmarks",Toast.LENGTH_LONG).show();
                handleMenuSearch();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void handleMenuSearch(){
        ActionBar action = ((AppCompatActivity)getActivity()).getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            //mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_search));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch(edtSeach.getText().toString());
                        return true;
                    }
                    return false;
                }
            });


            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            //mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_close));

            isSearchOpened = true;
        }
    }

    private void doSearch(String inputSearch) {
        arrayListDisplaySearch.clear();

        if(inputSearch.toString().equals("")){
            BookmarkRecyclerViewAdapter bookmarkRecyclerViewAdapter = new BookmarkRecyclerViewAdapter(arrayListDisplay,mContext);
            rv.setLayoutManager(new LinearLayoutManager(mContext));
            rv.setAdapter(bookmarkRecyclerViewAdapter);
        }else{
            for(int x=0; x<arrayListDisplay.size(); x++){
                DisplayPost dp = arrayListDisplay.get(x);

                String postID = dp.getPostID().toString();
                String userID = dp.getUserID().toString();
                String username = dp.getUserName().toString();
                String title = dp.getTitle().toString();
                String body = dp.getBody().toString();

                if(inputSearch.toString().equals(postID.toString())){
                    arrayListDisplaySearch.add(new DisplayPost(postID,userID,username,title,body));
                    BookmarkRecyclerViewAdapter bookmarkRecyclerViewAdapter = new BookmarkRecyclerViewAdapter(arrayListDisplaySearch,mContext);
                    rv.setLayoutManager(new LinearLayoutManager(mContext));
                    rv.setAdapter(bookmarkRecyclerViewAdapter);
                }else{
                    BookmarkRecyclerViewAdapter bookmarkRecyclerViewAdapter = new BookmarkRecyclerViewAdapter(arrayListDisplaySearch,mContext);
                    rv.setLayoutManager(new LinearLayoutManager(mContext));
                    rv.setAdapter(bookmarkRecyclerViewAdapter);
                }

            }
        }
    }







}