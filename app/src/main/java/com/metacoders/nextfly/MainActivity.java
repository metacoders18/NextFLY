package com.metacoders.nextfly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar ;

    private ArrayList<Model_for_Article_row> list ;
    private static final String JSON_URL = "https://launchlibrary.net/1.4/launch";
    String title ="Loading" , net="loading" ;
    //listview object
    ListView listView;

    //the hero list where we will store all the hero objects after parsing json
    List<Model_for_Article_row> heroList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing listview and hero list
        listView = (ListView) findViewById(R.id.listView);
        heroList = new ArrayList<>();
    loadHeroList();
    TextView load_mor = (TextView)findViewById(R.id.load_more);
    load_mor.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent o = new Intent(getApplicationContext() , PostsListActivity.class);
            startActivity(o);
        }
    });



}

    private void loadHeroList() {
        //getting the progressbar
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //making the progressbar visible
        progressBar.setVisibility(View.VISIBLE);

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressBar.setVisibility(View.INVISIBLE);


                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray heroArray = obj.getJSONArray("launches");

                            //now looping through all the elements of the json array

                            //getting the json object of the particular index inside the array
                            JSONObject heroObject = heroArray.getJSONObject(0);

                            //creating a hero object and giving them the values from json object
                            Model_for_Article_row hero = new Model_for_Article_row(heroObject.getString("name"), heroObject.getString("net"));

                            //adding the hero to herolist
                            heroList.add(hero);


                            //creating custom adapter object
                            ListViewAdapter adapter = new ListViewAdapter(heroList, getApplicationContext());

                            //adding the adapter to listview
                            listView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }



}
