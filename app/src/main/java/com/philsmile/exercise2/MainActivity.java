package com.philsmile.exercise2;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.philsmile.exercise2.db.DaoSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String postsURL = "https://jsonplaceholder.typicode.com/posts";
    String usersURL = "https://jsonplaceholder.typicode.com/users";

    ArrayList<DisplayPost> arrayListDisplay = new ArrayList<>();
    ArrayList<DisplayPost> arrayListDisplaySearch = new ArrayList<>();
    RecyclerView rv;

    private Toolbar mToolbar;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        rv = (RecyclerView) findViewById(R.id.post_rv);

        final Context mContext = getApplicationContext();

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

        PostRecyclerViewAdapter postRecyclerViewAdapter = new PostRecyclerViewAdapter(arrayListDisplay,mContext);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.setAdapter(postRecyclerViewAdapter);

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonArrayRequestPosts);
        requestQueue.add(jsonArrayRequestUsers);

        //GreenDAO


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_bookmark:
                Intent intent = new Intent(getApplicationContext(),BookmarkActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_search:
                handleMenuSearch();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }
    protected void handleMenuSearch(){
        android.support.v7.app.ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_search));

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
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);


            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_close));

            isSearchOpened = true;
        }
    }

    private void doSearch(String inputSearch) {
        arrayListDisplaySearch.clear();

        if(inputSearch.equals("")){
            PostRecyclerViewAdapter postRecyclerViewAdapter = new PostRecyclerViewAdapter(arrayListDisplay,getApplicationContext());
            rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            rv.setAdapter(postRecyclerViewAdapter);
        }else{
            for(int i=0; i<arrayListDisplay.size(); i++){
                DisplayPost dp = arrayListDisplay.get(i);

                String postID = dp.getPostID().toString();
                String userID = dp.getUserID().toString();
                String username = dp.getUserName().toString();
                String title = dp.getTitle().toString();
                String body = dp.getBody().toString();

                if(inputSearch.toString().equals(postID.toString())){
                    arrayListDisplaySearch.add(new DisplayPost(postID,userID,username,title,body));
                    PostRecyclerViewAdapter postRecyclerViewAdapter = new PostRecyclerViewAdapter(arrayListDisplaySearch,getApplicationContext());
                    rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rv.setAdapter(postRecyclerViewAdapter);
                }else{
                    PostRecyclerViewAdapter postRecyclerViewAdapter = new PostRecyclerViewAdapter(arrayListDisplaySearch,getApplicationContext());
                    rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rv.setAdapter(postRecyclerViewAdapter);
                }
            }
        }
    }




}
