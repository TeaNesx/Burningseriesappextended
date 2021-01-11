package com.example.burningseriesapp_extended;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Ion.with(getApplicationContext()).load("https://burning-series.io/andere-serien").asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {

                Pattern pattern = Pattern.compile("<li><a href='serie/(.*?)'<a><li>", Pattern.DOTALL);
                Matcher matcher = pattern.matcher(result);
                
                while (matcher.find()) {
                    System.out.println("matcher: "+matcher.group(1));
                }
            }
        });
    }
}
