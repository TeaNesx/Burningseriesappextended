package com.example.burningseriesapp_extended.Repository;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.burningseriesapp_extended.Model.Serie;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EpisodeRepository {
    private final String TAG = getClass().getSimpleName();

    private static EpisodeRepository instance;

    private String serieTitle;
    private MutableLiveData<String> serieTitleMutableLiveData = new MutableLiveData<>();

    private String serieDescription;
    private MutableLiveData<String> serieDescriptionMutableLiveData = new MutableLiveData<>();

    String imgSrc;
    private MutableLiveData<String> serieImageMutableLiveData = new MutableLiveData<>();

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
}
