package com.example.burningseriesapp_extended.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.burningseriesapp_extended.Model.Serie;
import com.example.burningseriesapp_extended.Repository.SerieRepository;

import java.io.IOException;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<String>> getSerieNameMutableLiveData;
    private MutableLiveData<List<String>> getSerieUrlMutableLiveData;
    private SerieRepository mRepoSerie;

    public void init() {
        mRepoSerie = SerieRepository.getInstance();
        getSerieNameMutableLiveData = mRepoSerie.getSerieNameMutableLiveData();
        getSerieUrlMutableLiveData = mRepoSerie.getSerieUrlMutableLiveData();
    }

    public MutableLiveData<List<String>> getSerie(Context context) {
        return mRepoSerie.getSerie(context);
    }

    public MutableLiveData<List<String>> getSerieUrl (Context context) {
        return mRepoSerie.getSerieUrl(context);
    }
}
