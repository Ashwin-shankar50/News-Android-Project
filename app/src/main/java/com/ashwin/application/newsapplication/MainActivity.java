package com.ashwin.application.newsapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    ListView listNews;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<HashMap<String, String>>();
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_URL = "url";
    static final String KEY_URLTOIMAGE = "urlToImage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listNews = (ListView) findViewById(R.id.listNews);



        DownloadNews newsTask = new DownloadNews();
        newsTask.execute();




    }


    class DownloadNews extends AsyncTask<String, Void, String> {
        @Override

        protected String doInBackground(String... args) {
            String line = "";


            try {
                URL myapi = new URL("https://newsapi.org/v2/top-headlines?sources=cnn&apiKey=0baa683f251d461b9f4e8f44ab6e72bb");
                HttpURLConnection httpURLConnection = (HttpURLConnection) myapi.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String data="";
                while (data != null) {
                    data = bufferedReader.readLine();
                    line = line + data;
                    //   Log.d("TEST STRING",data);
                }
            } catch (MalformedURLException e) {

            } catch (IOException e) {

            }

            return line;
        }
        @Override
        protected void onPostExecute(String line) {

            try {
                JSONObject jsonResponse = new JSONObject(line);
                JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(KEY_TITLE, jsonObject.optString(KEY_TITLE).toString());
                    map.put(KEY_DESCRIPTION, jsonObject.optString(KEY_DESCRIPTION).toString());
                    map.put(KEY_URL, jsonObject.optString(KEY_URL).toString());
                    map.put(KEY_URLTOIMAGE, jsonObject.optString(KEY_URLTOIMAGE).toString());

                    dataList.add(map);
                }
            } catch (JSONException e) {
            }

            ListNewsAdapter adapter = new ListNewsAdapter(MainActivity.this, dataList);
            listNews.setAdapter(adapter);
            listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    String ne = dataList.get(+position).get(KEY_URL);
                    Intent browserIntent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(ne));
                    startActivity(browserIntent);

                }
            });


        }



    }



}
