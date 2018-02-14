package com.philsmile.exercise2.Fragments;

/**
 * Created by philsmile on 2/14/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.philsmile.exercise2.Adapters.PostRecyclerViewAdapter;
import com.philsmile.exercise2.Classes.DisplayPost;
import com.philsmile.exercise2.MainActivity;
import com.philsmile.exercise2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class MainFragment extends Fragment{


    String postsURL = "https://jsonplaceholder.typicode.com/posts";
    String usersURL = "https://jsonplaceholder.typicode.com/users";

    ArrayList<DisplayPost> arrayListDisplay = new ArrayList<>();
    ArrayList<DisplayPost> arrayListDisplaySearch = new ArrayList<>();
    RecyclerView rv;

    private Toolbar mToolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;

    Context mContext;

    @SuppressLint("ValidFragment")
    public MainFragment(Context ctx, Toolbar mToolbar) {
        mContext = ctx;
        mToolbar = mToolbar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_main, parent, false);
        setHasOptionsMenu(true);

        rv = (RecyclerView) v.findViewById(R.id.post_rv);

        // Initialize a new RequestQueue instance
        final RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        // Initialize a new JsonObjectRequest instance
        JsonArrayRequest jsonArrayRequestPosts = new JsonArrayRequest(
                Request.Method.GET,
                postsURL,
                (JSONArray) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject user = response.getJSONObject(i);

                                // Get the current student (json object) data
                                String userID = user.getString("userId");
                                String id = user.getString("id");
                                String title = user.getString("title");
                                String body = user.getString("body");

                                arrayListDisplay.add(new DisplayPost(id,userID,"",title,body));
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Toast.makeText(mContext,"Error",Toast.LENGTH_LONG).show();
                    }
                }
        );

        JsonArrayRequest jsonArrayRequestUsers = new JsonArrayRequest(
                Request.Method.GET,
                usersURL,
                (JSONArray) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON
                        try{
                            // Loop through the array elements
                            for(int i=0;i<response.length();i++){
                                // Get current json object
                                JSONObject user = response.getJSONObject(i);

                                // Get the current student (json object) data
                                String id = user.getString("id");
                                String name = user.getString("name");

                                for(int j=0; j<arrayListDisplay.size(); j++){
                                    DisplayPost post = arrayListDisplay.get(j);
                                    String userID = post.getUserID().toString();

                                    if(id.toString().equals(userID.toString())){
                                        post.setUserName(name.toString());
                                        Log.i("XOXO",post.getPostID().toString()
                                                +"--"+ id.toString()
                                                +"--"+ post.getUserName().toString());
                                    }
                                }

                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                        Toast.makeText(mContext,"Error",Toast.LENGTH_LONG).show();
                    }
                }
        );

        PostRecyclerViewAdapter postRecyclerViewAdapter;
        postRecyclerViewAdapter = new PostRecyclerViewAdapter(arrayListDisplay,mContext);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(postRecyclerViewAdapter);

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonArrayRequestPosts);
        requestQueue.add(jsonArrayRequestUsers);

        return v;
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
                //Toast.makeText(mContext,"Search CLicked",Toast.LENGTH_LONG).show();
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

        if (inputSearch.equals("")) {
            PostRecyclerViewAdapter postRecyclerViewAdapter = new PostRecyclerViewAdapter(arrayListDisplay, mContext);
            rv.setLayoutManager(new LinearLayoutManager(mContext));
            rv.setAdapter(postRecyclerViewAdapter);
        } else {
            for (int i = 0; i < arrayListDisplay.size(); i++) {
                DisplayPost dp = arrayListDisplay.get(i);

                String postID = dp.getPostID().toString();
                String userID = dp.getUserID().toString();
                String username = dp.getUserName().toString();
                String title = dp.getTitle().toString();
                String body = dp.getBody().toString();

                if (inputSearch.toString().equals(postID.toString())) {
                    arrayListDisplaySearch.add(new DisplayPost(postID, userID, username, title, body));
                    PostRecyclerViewAdapter postRecyclerViewAdapter = new PostRecyclerViewAdapter(arrayListDisplaySearch, mContext);
                    rv.setLayoutManager(new LinearLayoutManager(mContext));
                    rv.setAdapter(postRecyclerViewAdapter);
                } else {
                    PostRecyclerViewAdapter postRecyclerViewAdapter = new PostRecyclerViewAdapter(arrayListDisplaySearch, mContext);
                    rv.setLayoutManager(new LinearLayoutManager(mContext));
                    rv.setAdapter(postRecyclerViewAdapter);
                }
            }
        }


    }




}