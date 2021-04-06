package com.example.burningseriesapp_extended.Repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.burningseriesapp_extended.Model.Serie;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.IonContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SerieRepository {
    private final String TAG = getClass().getSimpleName();
    private static SerieRepository instance;
    private ArrayList<String> serieURL = new ArrayList<>();

    private MutableLiveData <List<String>> serieURLMutableLiveData = new MutableLiveData<>();

    public static SerieRepository getInstance(){
        if(instance == null){
            instance = new SerieRepository();
        }
        return instance;
    }

    public MutableLiveData<List<String>> getSerie(Context context) {
        (new Thread(new Runnable() {
            @Override
            public void run() {
                Ion.with(context).load("https://burning-series.io/andere-serien").asString().setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {

                        //Pattern and matcher to fetch url
                        Pattern pattern = Pattern.compile("<li><a href=\".+?\" title=\".+?\">(.+?)</a></li>", Pattern.DOTALL);
                        Matcher matcher = pattern.matcher(result);
                        System.out.println("result: "+ result);

                        while (matcher.find()) {
                            serieURL.add(matcher.group(1));
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
}
