package com.example.myapplication2_0;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication2_0.databinding.ActivityBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Activity extends AppCompatActivity {

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_);
        Intent intent = getIntent();
        String name = intent.getStringExtra("user_field");
        textView=findViewById(R.id.textView);

        String city = name;
        String key = "e823ccbee071befd6937eba93563054d";
        String url = "http://api.weatherapi.com/v1/forecast.json?key=db90acd3651a4b82b96112114221112&q="+city+"&days=3&lang=ru";

        new GetURLDate().execute(url);



    }
    public class GetURLDate extends AsyncTask<String,String,String> {
        protected void onPreExecute(){
            super.onPreExecute();
            textView.setText("Ожидайте...");

        }
        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String Line = "";

                while((Line=reader.readLine()) != null )
                    buffer.append(Line).append("\n");
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection!=null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            return null;

        }
        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            try {
                JSONObject obj = new JSONObject(result);
                textView.setText(obj.getJSONObject("current").getJSONObject("condition").getString("text")+"\n"
                        +obj.getJSONObject("current").getDouble("temp_c")+"\n"+"\n"

                        +obj.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0).getString("date")+"\n"
                        +obj.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0).getJSONArray("hour").getJSONObject(8).getString("temp_c")+"/"
                        +obj.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0).getJSONArray("hour").getJSONObject(17).getString("temp_c")+"\n"+"\n"

                        +obj.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(1).getString("date")+"\n"
                        +obj.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(1).getJSONArray("hour").getJSONObject(8).getString("temp_c")+"/"
                        +obj.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(1).getJSONArray("hour").getJSONObject(17).getString("temp_c")+"\n"+"\n"

                        +obj.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(2).getString("date")+"\n"
                        +obj.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(2).getJSONArray("hour").getJSONObject(8).getString("temp_c")+"/"
                        +obj.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(2).getJSONArray("hour").getJSONObject(17).getString("temp_c")+"\n"+"\n"


                );

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}