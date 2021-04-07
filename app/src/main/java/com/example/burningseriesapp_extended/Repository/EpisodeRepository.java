package com.example.burningseriesapp_extended.Repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EpisodeRepository {

    private static EpisodeRepository instance;

    private String serieTitle;
    private MutableLiveData<String> serieTitleMutableLiveData = new MutableLiveData<>();

    private String serieDescription;
    private MutableLiveData<String> serieDescriptionMutableLiveData = new MutableLiveData<>();

    private String imgSrc;
    private MutableLiveData<String> serieImageMutableLiveData = new MutableLiveData<>();

    private List<String> serieSeason = new ArrayList<>();
    private MutableLiveData<List<String>> serieSeasonMutableLiveData = new MutableLiveData<>();

    private List<String> serieEpisode = new ArrayList<>();
    private MutableLiveData<List<String>> serieEpisodeMutableLiveData = new MutableLiveData<>();

    public static EpisodeRepository getInstace() {
        if (instance == null) {
            instance = new EpisodeRepository();
        }
        return instance;
    }

    public MutableLiveData<String> getSerieTitle(Context context, String serieLink) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                Ion.with(context).load("https://burning-series.io/"+serieLink).asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        //Pattern and matcher to fetch title
                        Pattern titlePattern = Pattern.compile("<h2>\\s*(.+?)\\s*<small>Staffel \\d+<\\/small>\\s*<\\/h2>", Pattern.DOTALL);
                        Matcher titleMatcher = titlePattern.matcher(result);

                        while (titleMatcher.find()) {
                            serieTitle = titleMatcher.group(1);
                        }

                        serieTitleMutableLiveData.setValue(serieTitle);
                    }
                });
            }
        })).start();

        return serieTitleMutableLiveData;
    }

    public MutableLiveData<String> getSerieDescription (Context context, String serieLink) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                Ion.with(context).load("https://burning-series.io/"+serieLink).asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        //Pattern and matcher to fetch Desc
                        Pattern descPattern = Pattern.compile("<p>(.+?)</p>");
                        Matcher descMatcher = descPattern.matcher(result);

                        while (descMatcher.find()) {
                            serieDescription = descMatcher.group(1);
                        }

                        serieDescriptionMutableLiveData.setValue(serieDescription);
                    }
                });
            }
        })).start();

        return serieDescriptionMutableLiveData;
    }

    public MutableLiveData<String> getSerieImage (Context context, String serieLink) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                Ion.with(context).load("https://burning-series.io/"+serieLink).asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        //Pattern and matcher to fetch image
                        Pattern imgPattern = Pattern.compile("<img src=\"(.+?)\" alt=\"Cover\" />");
                        Matcher imgMatcher = imgPattern.matcher(result);

                        while (imgMatcher.find()) {
                            imgSrc = "https://burning-series.io" + imgMatcher.group(1);
                        }

                        serieImageMutableLiveData.setValue(imgSrc);
                    }
                });
            }
        })).start();

        return serieImageMutableLiveData;
    }

    public MutableLiveData<List<String>> getSerieSeason (Context context, String serieLink) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                Ion.with(context).load("https://burning-series.io/"+serieLink).asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        serieSeason.clear();

                        //Pattern and matcher to fetch Season
                        Pattern seasonPattern = Pattern.compile("<li class=\"s.+?\"><a href=\".+?\">(.+?)</a></li>");
                        Matcher seasonMatcher = seasonPattern.matcher(result);

                        while (seasonMatcher.find()) {
                            serieSeason.add(seasonMatcher.group(1));
                        }

                        serieSeasonMutableLiveData.setValue(serieSeason);
                    }
                });
            }
        })).start();

        return serieSeasonMutableLiveData;
    }

    public MutableLiveData<List<String>> getSerieEpisode (Context context, String serieLink, String season) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                Ion.with(context).load("https://burning-series.io/"+serieLink+"/"+season).asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        serieEpisode.clear();

                        //Pattern and matcher to fetch Episodes
                        Pattern episodePattern = Pattern.compile("<td><a href=\".+?\" title=\"(.+?)\">.+?</a></td>");
                        Matcher episodeMatcher = episodePattern.matcher(result);

                        while (episodeMatcher.find()) {
                            serieEpisode.add(episodeMatcher.group(1));
                        }

                        serieEpisodeMutableLiveData.setValue(serieEpisode);
                    }
                });
            }
        })).start();

        return serieEpisodeMutableLiveData;
    }

    public MutableLiveData<List<String>> getSerieEpisode (Context context, String serieLink) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                Ion.with(context).load("https://burning-series.io/"+serieLink).asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        //Pattern and matcher to fetch Episodes
                        Pattern episodePattern = Pattern.compile("<td><a href=\".+?\" title=\"(.+?)\">.+?</a></td>");
                        Matcher episodeMatcher = episodePattern.matcher(result);

                        while (episodeMatcher.find()) {
                            serieEpisode.add(episodeMatcher.group(1));
                        }

                        serieEpisodeMutableLiveData.setValue(serieEpisode);
                    }
                });
            }
        })).start();

        return serieEpisodeMutableLiveData;
    }
}
