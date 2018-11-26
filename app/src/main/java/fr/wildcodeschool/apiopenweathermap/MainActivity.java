package fr.wildcodeschool.apiopenweathermap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final static String API_KEY = "26f4c5596bf13f93e3ece34223d9e5a5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView forecast = findViewById(R.id.text_forecast);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://api.openweathermap.org/data/2.5/forecast?q=Bordeaux&APPID=" + API_KEY;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray list = response.getJSONArray("list");
                            int j = 1;
                            for(int i = 0; i < list.length(); i+=8){
                                JSONObject listObject = (JSONObject) list.get(i);
                                JSONArray weather = listObject.getJSONArray("weather");
                                JSONObject weatherInfo = weather.getJSONObject(0);
                                String description = weatherInfo.getString("description");
                                forecast.append("Day " + j + ": " + description + " \n ");
                                j++;
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY_ERROR", "onErrorResponse: " + error.getMessage());
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}