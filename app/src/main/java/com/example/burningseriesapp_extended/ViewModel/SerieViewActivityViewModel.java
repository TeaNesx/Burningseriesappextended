package com.example.burningseriesapp_extended.ViewModel;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.burningseriesapp_extended.Model.Serie;
import com.example.burningseriesapp_extended.Repository.EpisodeRepository;

import java.util.List;

public class SerieViewActivityViewModel extends ViewModel {

    private MutableLiveData<String> getSerieTitleMutableLiveData;
    private MutableLiveData<String> getSerieDescriptionMutableLiveData;
    private MutableLiveData<String> getSerieImageMutableLiveData;
    private MutableLiveData<List<String>> getSerieSeasonMutableLiveData;

    private EpisodeRepository mRepoEpisode;

    public void initSerieTitle(Context context, String serieLink) {
        mRepoEpisode = EpisodeRepository.getInstace();
        getSerieTitleMutableLiveData = mRepoEpisode.getSerieTitle(context, serieLink);
    }

    public void initSerieDescription(Context context, String serieLink) {
        mRepoEpisode = EpisodeRepository.getInstace();
        getSerieDescriptionMutableLiveData = mRepoEpisode.getSerieDescription(context, serieLink);
    }

    public void initSerieImage(Context context, String serieLink) {
        mRepoEpisode = EpisodeRepository.getInstace();
        getSerieImageMutableLiveData = mRepoEpisode.getSerieImage(context, serieLink);
    }

    public void initSerieSeason(Context context, String serieLink) {
        mRepoEpisode = EpisodeRepository.getInstace();
        getSerieSeasonMutableLiveData = mRepoEpisode.getSerieSeason(context, serieLink);
    }

    public MutableLiveData<String> getSerieTitle() {
        return getSerieTitleMutableLiveData;
    }

    public MutableLiveData<String> getSerieDescription() {
        return getSerieDescriptionMutableLiveData;
    }

    public MutableLiveData<String> getSerieImage() {
        return getSerieImageMutableLiveData;
    }

    public MutableLiveData<List<String>> getSerieSeason() {
        return getSerieSeasonMutableLiveData;
    }
}
