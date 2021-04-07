package com.example.burningseriesapp_extended.Repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SerieRepository {
    private final String TAG = getClass().getSimpleName();

    private static SerieRepository instance;
    private ArrayList<String> serieURL = new ArrayList<>();
    private ArrayList<String> serieName = new ArrayList<>();
    private MutableLiveData <List<String>> serieURLMutableLiveData = new MutableLiveData<>();
    private MutableLiveData <List<String>> serieNameMutableLiveData = new MutableLiveData<>();

    public static SerieRepository getInstance(){
        if(instance == null){
            instance = new SerieRepository();
        }
        return instance;
    }

    public MutableLiveData<List<String>> getSerieName(Context context) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                Ion.with(context).load("https://burning-series.io/andere-serien").asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        //Pattern and matcher to fetch Name
                        Pattern pattern = Pattern.compile("<li><a href=\".+?\" title=\".+?\">(.+?)</a></li>", Pattern.DOTALL);
                        Matcher matcher = pattern.matcher(result);

                        while (matcher.find()) {
                            serieName.add(matcher.group(1));
                        }

                        serieNameMutableLiveData.setValue(serieName);
                    }
                });
            }
        })).start();

        return serieNameMutableLiveData;
    }

    public MutableLiveData<List<String>> getSerieUrl (Context context) {

        (new Thread(new Runnable() {
            @Override
            public void run() {
                Ion.with(context).load("https://burning-series.io/andere-serien").asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        //Pattern and matcher to fetch url
                        Pattern LinkPattern = Pattern.compile("<li><a href=\"(.+?)\" title=\".+?\">.+?</a></li>", Pattern.DOTALL);
                        Matcher LinkMatcher = LinkPattern.matcher(result);

                        while (LinkMatcher.find()) {
                            serieURL.add(LinkMatcher.group(1));
                        }

                        serieURLMutableLiveData.setValue(serieURL);

                    }
                });
            }
        })).start();

        return serieURLMutableLiveData;
    }

    public MutableLiveData<List<String>> getSerieURLMutableLiveData() {
        return serieURLMutableLiveData;
    }

    public MutableLiveData<List<String>> getSerieNameMutableLiveData() {
        return serieNameMutableLiveData;
    }
}
