package com.lincoln.jake.filevault;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class History2 extends AppCompatActivity {

    public String jsonOut = null, data = null, url = null; //declare globals
    // array list to store tweet items from web service
    //ArrayList<String> items = new ArrayList<String>();
    // json test string
    String jsonTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history2);
        Bundle extras = getIntent().getExtras(); //pull data from previous activity
        if (extras != null) {
            data = extras.getString("itemdata");
            url = constructURL(data);
            new AsyncTaskParseJson().execute();

        }
    }
    public void getCOORDfromData(String url)
    {

    }
    public String constructURL(String data) {
        String[] split = data.split(Pattern.quote("^"));
        String output = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + split[1] + "," + split[3] + "&key=AIzaSyBbh93ZVOU_yjPBxX-i6Lpv2SfZs4l5ikQ"; //simple string construction
        return output;
    }
    // added asynctask class methods below -  you can make this class as a separate class file


    public class AsyncTaskParseJson extends AsyncTask<String, String, String> {

        @Override
        // this method is used for......................
        protected void onPreExecute() {}

        @Override
        // this method is used for...................
        protected String doInBackground(String... arg0)  {

            try {
                // create new instance of the httpConnect class
                httpConnect jParser = new httpConnect();

                // get json string from service url
                String json = jParser.getJSONFromUrl(url);

                // save returned json to your test string
                jsonOut = json;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        // below method will run when service HTTP request is complete, will then bind tweet text in arrayList to ListView
        protected void onPostExecute(String strFromDoInBg) {
            try {
                TextView display = (TextView) findViewById(R.id.txtDisplayMore);
                JSONArray json1 = (new JSONObject(jsonOut)).getJSONArray("results");
                int a = 0; //code did not like getJSONObject(0)
               String yes = json1.getJSONObject(a).getString("formatted_address"); //get single item out from JSON data.
                display.setText(yes);
            }catch(Exception e)
            {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}
