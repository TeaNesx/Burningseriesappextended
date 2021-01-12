package com.example.burningseriesapp_extended;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SeriesViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_view);

        Bundle dataFromList = getIntent().getExtras();
        String LinkData = "serie/" + dataFromList.getString("selectedItem");
        System.out.println("LinkData: "+ LinkData);
    }
}