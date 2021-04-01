package com.example.burningseriesapp_extended.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.burningseriesapp_extended.Model.Serie;
import com.example.burningseriesapp_extended.Repository.SerieRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<String>> getSerieURLMutableLiveData;
    private SerieRepository mRepoSerie;

    public void init() {
        mRepoSerie = SerieRepository.getInstance();
        getSerieURLMutableLiveData = mRepoSerie.getSerieURLMutableLiveData();
    }

    public MutableLiveData<List<String>> getSerie() {
        return mRepoSerie.getSerie();
    }
}
