package com.philsmile.exercise2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
