package com.example.burningseriesapp_extended.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.burningseriesapp_extended.Repository.SerieRepository;

import java.io.IOException;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<String>> getSerieURLMutableLiveData;
    private MutableLiveData<List<String>> getSerieNameMutableLiveData;
    private SerieRepository mRepoSerie;

    public void init(Context context) {
        mRepoSerie = SerieRepository.getInstance();
        getSerieNameMutableLiveData = mRepoSerie.getSerieName(context);
        getSerieURLMutableLiveData = mRepoSerie.getSerieUrl(context);
    }

    public LiveData<List<String>> getSerieName() {
        return getSerieNameMutableLiveData;
    }

    public LiveData<List<String>> getSerieURL() {
        return getSerieURLMutableLiveData;
    }
}
