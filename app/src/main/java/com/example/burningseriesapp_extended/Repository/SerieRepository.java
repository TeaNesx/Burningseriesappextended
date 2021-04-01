package com.example.burningseriesapp_extended.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.burningseriesapp_extended.Model.Serie;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.IonContext;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SerieRepository {
    private final String TAG = getClass().getSimpleName();
    private static SerieRepository instance;


    private MutableLiveData <List<String>> serieURLMutableLiveData;
    private ArrayList<String> serieURL;


    public static SerieRepository getInstance(){
        if(instance == null){
            instance = new SerieRepository();
        }
        return instance;

    }

    /*
    public MutableLiveData<List<Serie>> getSerie () {
        MutableLiveData<List<Serie>> serieData = new MutableLiveData<>();

        Ion.with((IonContext) SerieRepository.this).load("https://burning-series.io/andere-serien").asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {

                //Pattern and matcher to fetch url
                Pattern pattern = Pattern.compile("<li><a href=\".+?\" title=\".+?\">(.+?)</a></li>", Pattern.DOTALL);
                Matcher matcher = pattern.matcher(result);

                while (matcher.find()) {
                    serieURL.add(matcher.group(1));
                }

                serieData.postValue(serieURL);
            }
        });

        return serieData;
    }
     */

    public MutableLiveData<List<String>> getSerie() {
        serieURLMutableLiveData = new MutableLiveData<>();

        Ion.with((IonContext) SerieRepository.this).load("https://burning-series.io/andere-serien").asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {

                //Pattern and matcher to fetch url
                Pattern pattern = Pattern.compile("<li><a href=\".+?\" title=\".+?\">(.+?)</a></li>", Pattern.DOTALL);
                Matcher matcher = pattern.matcher(result);

                while (matcher.find()) {
                    serieURL.add(matcher.group(1));
                }

                System.out.println("serieURL: " + serieURL);

                serieURLMutableLiveData.setValue(serieURL);

            }
        });

        return serieURLMutableLiveData;
    }

    public MutableLiveData<List<String>> getSerieURLMutableLiveData() {
        return serieURLMutableLiveData;
    }
}
