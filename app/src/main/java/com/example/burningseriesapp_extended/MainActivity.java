package com.example.burningseriesapp_extended;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> SerieList = new ArrayList<>();
    private ListView lv_SerieLit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv_SerieLit = findViewById(R.id.lv_Serie);

        (new Thread(new Runnable() {
            @Override
            public void run() {
                // Background executed code (Lambda-Expression)
                Ion.with(getApplicationContext()).load("https://burning-series.io/andere-serien").asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Pattern pattern = Pattern.compile("<li><a href=\".+?\" title=\".+?\">(.+?)</a></li>", Pattern.DOTALL);
                        Matcher matcher = pattern.matcher(result);
                        while (matcher.find()) {
                            System.out.println("matcher: "+matcher.group(1));
                            SerieList.add(matcher.group(1));
                        }

                        System.out.println("SerieList: "+ SerieList);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, SerieList);
                        lv_SerieLit.setAdapter(adapter);
                    }
                });
            }
        })).start();

    }
}
