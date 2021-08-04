package com.example.api_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    RequestQueue requestQueue;
    String URL ="https://www.google.com";
    String apiURL = "https://jsonplaceholder.typicode.com/todos";
    String getApiURL = "https://jsonplaceholder.typicode.com/todos/1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);

        textView = findViewById(R.id.text_view);

        //Parsing an JSONArray using getJsonArrayRequest
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                getApiURL, null, response -> {
            try {
                textView.setText(response.getString("completed"));
                Log.d("JSONO", "onCreate: " + response.getString("title"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
                Log.d("JSONO", "failed to get data!");
        });
        requestQueue.add(jsonObjectRequest);

        getJsonArrayRequest();

        getString(requestQueue);

    }

    //Parsing an JSONArray using getJsonArrayRequest
    private void getJsonArrayRequest() {
        // JsonArrayRequest : A request for retrieving a JSONArray response body at a given URL.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                apiURL, null,
                response -> {

                //iterations through array
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Log.d("JSON", "onCreate: " + jsonObject
                                    .getString("userId") );

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    Log.d("JSON", "onCreate: " + response);
                }, error -> {
                    Log.d("JSON", "Failed to get!");
                });
        requestQueue.add(jsonArrayRequest);
    }

    //parsing a String format
    private void getString(RequestQueue requestQueue) {
        //String request : A canned request for retrieving the response body at a given URL as a String.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, response -> {
            //display the content of our url
            Log.d("Volley", "onCreate: " + response.substring(0, 500));

        }, error -> {
            Log.d("Volley", "failed to get info!");

        });

        // add the request to request queue
        requestQueue.add(stringRequest);
    }
}