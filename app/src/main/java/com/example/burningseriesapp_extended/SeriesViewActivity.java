package com.example.burningseriesapp_extended;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.service.autofill.FieldClassification;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeriesViewActivity extends AppCompatActivity {
    TextView seriesTitle, seriesDesc;
    ImageView seriesImg;
    Spinner seasonTab;
    ListView episodeListview;
    ArrayList<String> seasonList = new ArrayList<>();
    ArrayList<String> episodeList = new ArrayList<>();
    ArrayAdapter<String> adapter, episodeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_view);

        seriesTitle = findViewById(R.id.tv_seriesTitle);
        seriesDesc = findViewById(R.id.tv_seriesDesc);
        seriesImg = findViewById(R.id.iv_seriesImg);
        seasonTab = findViewById(R.id.sp_season);
        episodeListview = findViewById(R.id.lv_Episode);

        episodeListview.setNestedScrollingEnabled(true);

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

                        //Pattern and matcher to fetch Desc
                        Pattern descPattern = Pattern.compile("<p>(.+?)</p>");
                        Matcher descMatcher = descPattern.matcher(result);

                        while (descMatcher.find()) {
                            seriesDesc.setText(descMatcher.group(1));
                        }

                        //Pattern and matcher to fetch image
                        Pattern imgPattern = Pattern.compile("<img src=\"(.+?)\" alt=\"Cover\" />");
                        Matcher imgMatcher = imgPattern.matcher(result);

                        while (imgMatcher.find()) {
                            String imgsrc = "https://burning-series.io"+imgMatcher.group(1);
                            Picasso.get().load(imgsrc).into(seriesImg);
                        }

                        //Pattern and matcher to fetch Season
                        Pattern seasonPattern = Pattern.compile("<li class=\"s.+?\"><a href=\".+?\">(.+?)</a></li>");
                        Matcher seasonMatcher = seasonPattern.matcher(result);

                        while (seasonMatcher.find()) {
                            seasonList.add(seasonMatcher.group(1));
                            adapter = new ArrayAdapter<>(SeriesViewActivity.this, android.R.layout.simple_spinner_dropdown_item, seasonList);
                        }
                        seasonTab.setAdapter(adapter);

                        //Pattern and matcher to fetch Episodes
                        Pattern episodePattern = Pattern.compile("<td><a href=\".+?\" title=\"(.+?)\">.+?</a></td>");
                        Matcher episodeMatcher = episodePattern.matcher(result);

                        while (episodeMatcher.find()) {
                            episodeList.add(episodeMatcher.group(1));
                        }

                        episodeAdapter = new ArrayAdapter<>(SeriesViewActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, episodeList);
                        episodeListview.setAdapter(episodeAdapter);
                    }
                });
            }
        })).start();
    }
}