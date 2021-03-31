package com.example.burningseriesapp_extended.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.burningseriesapp_extended.Model.Serie;
import com.example.burningseriesapp_extended.Repository.SerieRepository;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<List<Serie>> mSerie;
    private SerieRepository mRepoSerie;

    public void init() {
        if (mSerie == null) {
            return;
        }

        mRepoSerie = SerieRepository.getInstance();
        mSerie = mRepoSerie.getSerie();
    }

    public LiveData<List<Serie>> getSerie() {
        return mSerie;
    }
}
