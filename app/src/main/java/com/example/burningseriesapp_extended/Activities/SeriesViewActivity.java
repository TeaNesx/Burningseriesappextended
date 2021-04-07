package com.example.burningseriesapp_extended.Activities;

/**
 * Created by TeaNesx 13/01/2021
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.burningseriesapp_extended.R;
import com.example.burningseriesapp_extended.ViewModel.MainActivityViewModel;
import com.example.burningseriesapp_extended.ViewModel.SerieViewActivityViewModel;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeriesViewActivity extends AppCompatActivity {
    public static String EXTRA_LINK = "com.example.burningseriesapp_extended.EXTRA_LINK";
    TextView serieTitle, seriesDesc;
    ImageView seriesImg;
    Spinner seasonTab;
    ListView episodeListview;
    ArrayAdapter<String> adapter, episodeAdapter;

    SerieViewActivityViewModel mSerieViewModelActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_view);

        serieTitle = findViewById(R.id.tv_seriesTitle);
        seriesDesc = findViewById(R.id.tv_seriesDesc);
        seriesImg = findViewById(R.id.iv_seriesImg);
        seasonTab = findViewById(R.id.sp_season);
        episodeListview = findViewById(R.id.lv_Episode);
        episodeListview.setNestedScrollingEnabled(true);

        Bundle dataFromList = getIntent().getExtras();
        String LinkData = dataFromList.getString("MainActivitySerieUrl");
        mSerieViewModelActivity = new ViewModelProvider(this).get(SerieViewActivityViewModel.class);

        mSerieViewModelActivity.initSerieTitle(this, LinkData);
        mSerieViewModelActivity.getSerieTitle().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String viewModelSerieTitle) {
                serieTitle.setText(viewModelSerieTitle);
            }
        });

        mSerieViewModelActivity.initSerieDescription(this, LinkData);
        mSerieViewModelActivity.getSerieDescription().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String viewModelSerieDescription) {
                seriesDesc.setText(viewModelSerieDescription);
            }
        });

        mSerieViewModelActivity.initSerieImage(this, LinkData);
        mSerieViewModelActivity.getSerieImage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String viewModelSerieImage) {
                Picasso.get().load(viewModelSerieImage).into(seriesImg);
            }
        });

        mSerieViewModelActivity.initSerieSeason(this, LinkData);
        mSerieViewModelActivity.getSerieSeason().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> viewModelSerieSeason) {
                adapter = new ArrayAdapter<>(SeriesViewActivity.this, android.R.layout.simple_spinner_dropdown_item, viewModelSerieSeason);
                seasonTab.setAdapter(adapter);

                if (viewModelSerieSeason.contains("Specials")) {
                    seasonTab.setSelection(0);
                }
            }
        });

        mSerieViewModelActivity.initSerieEpisode(SeriesViewActivity.this, LinkData);
        mSerieViewModelActivity.getSerieEpisode().observe(SeriesViewActivity.this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> serieEpisode) {
                episodeAdapter = new ArrayAdapter<>(SeriesViewActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, serieEpisode);
                episodeListview.setAdapter(episodeAdapter);

                seasonTab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String season = String.valueOf(i);

                        mSerieViewModelActivity.initSerieEpisode(SeriesViewActivity.this, LinkData, season);
                        mSerieViewModelActivity.getSerieEpisode().observe(SeriesViewActivity.this, new Observer<List<String>>() {
                            @Override
                            public void onChanged(List<String> serieEpisode) {
                                episodeAdapter = new ArrayAdapter<>(SeriesViewActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, serieEpisode);
                                episodeListview.setAdapter(episodeAdapter);
                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });



//        seasonTab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String season = String.valueOf(i);
//
//                mSerieViewModelActivity.initSerieEpisode(SeriesViewActivity.this, LinkData, season);
//                mSerieViewModelActivity.getSerieEpisode().observe(SeriesViewActivity.this, new Observer<List<String>>() {
//                    @Override
//                    public void onChanged(List<String> serieEpisode) {
//                        episodeAdapter = new ArrayAdapter<>(SeriesViewActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, serieEpisode);
//                        episodeListview.setAdapter(episodeAdapter);
//                    }
//                });
//            }
//        });




//                        //Pattern and matcher to fetch Season
//                        Pattern seasonPattern = Pattern.compile("<li class=\"s.+?\"><a href=\".+?\">(.+?)</a></li>");
//                        Matcher seasonMatcher = seasonPattern.matcher(result);
//
//                        while (seasonMatcher.find()) {
//                            seasonList.add(seasonMatcher.group(1));
//                            adapter = new ArrayAdapter<>(SeriesViewActivity.this, android.R.layout.simple_spinner_dropdown_item, seasonList);
//                        }
//                        seasonTab.setAdapter(adapter);
//
//                        if (seasonList.contains("Specials")) {
//                            seasonTab.setSelection(0);
//                        }
//
//                        seasonTab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                            @Override
//                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                                int season = position;
//
//                                Ion.with(getApplicationContext()).load("https://burning-series.io/" + LinkData + "/" + season).asString().setCallback(new FutureCallback<String>() {
//                                    @Override
//                                    public void onCompleted(Exception e, String result) {
//
//                                        episodeList.clear();
//                                        //Pattern and matcher to fetch Episodes
//                                        Pattern episodePattern = Pattern.compile("<td><a href=\".+?\" title=\"(.+?)\">.+?</a></td>");
//                                        Matcher episodeMatcher = episodePattern.matcher(result);
//
//                                        while (episodeMatcher.find()) {
//                                            episodeList.add(episodeMatcher.group(1));
//                                        }
//
//                                        episodeAdapter = new ArrayAdapter<>(SeriesViewActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, episodeList);
//                                        episodeListview.setAdapter(episodeAdapter);
//
//
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onNothingSelected(AdapterView<?> parent) {
//
//                            }
//                        });
//                    }
//                });
//
//            }
//        })).start();


    }
}