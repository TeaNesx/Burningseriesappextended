package com.example.burningseriesapp_extended;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.autofill.FieldClassification;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeriesViewActivity extends AppCompatActivity {
    TextView seriesTitle, seriesDesc;
    ImageView seriesImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_view);

        seriesTitle = findViewById(R.id.tv_seriesTitle);
        seriesDesc = findViewById(R.id.tv_seriesDesc);
        seriesImg = findViewById(R.id.iv_seriesImg);

        Bundle dataFromList = getIntent().getExtras();
        String LinkData = "serie/" + dataFromList.getString("selectedItem");

        (new Thread(new Runnable() {
            @Override
            public void run() {
                Ion.with(getApplicationContext()).load("https://burning-series.io/" + LinkData).asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        //Pattern and matcher to fetch title
                        Pattern titlePattern = Pattern.compile("<h2>\\s*(.+?)\\s*<small>Staffel \\d+<\\/small>\\s*<\\/h2>", Pattern.DOTALL);
                        Matcher titleMatcher = titlePattern.matcher(result);

                        while (titleMatcher.find()) {
                            seriesTitle.setText(titleMatcher.group(1));
                        }
                    }
                });
            }
        })).start();
    }
}