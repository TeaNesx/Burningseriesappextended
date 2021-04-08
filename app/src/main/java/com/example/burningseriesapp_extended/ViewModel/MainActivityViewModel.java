package com.example.burningseriesapp_extended.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.burningseriesapp_extended.Repository.SerieRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    MutableLiveData<List<String>> getSerieURLMutableLiveData;
    MutableLiveData<List<String>> getSerieNameMutableLiveData;
    SerieRepository mRepoSerie;

    public void initSerieName(Context context) {
        mRepoSerie = SerieRepository.getInstance();
        getSerieNameMutableLiveData = mRepoSerie.getSerieName(context);
    }

    public void initSerieUrl(Context context) {
        mRepoSerie = SerieRepository.getInstance();
        getSerieURLMutableLiveData = mRepoSerie.getSerieUrl(context);
    }

    public LiveData<List<String>> getSerieName() {
        return getSerieNameMutableLiveData;
    }

    public LiveData<List<String>> getSerieURL() {
        return getSerieURLMutableLiveData;
    }
}
