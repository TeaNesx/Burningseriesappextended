package com.example.burningseriesapp_extended.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.burningseriesapp_extended.Repository.EpisodeRepository;

import java.util.List;

public class SerieViewActivityViewModel extends ViewModel {

    private MutableLiveData<String> getSerieTitleMutableLiveData;
    private MutableLiveData<String> getSerieDescriptionMutableLiveData;
    private MutableLiveData<String> getSerieImageMutableLiveData;
    private MutableLiveData<List<String>> getSerieSeasonMutableLiveData;
    private MutableLiveData<List<String>> getSerieEpisodeMutableLiveData;

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

    public void initSerieEpisode(Context context, String serieLink) {
        mRepoEpisode = EpisodeRepository.getInstace();
        getSerieEpisodeMutableLiveData = mRepoEpisode.getSerieEpisode(context, serieLink);
    }

    public void initSerieEpisode(Context context, String serieLink, String season) {
        mRepoEpisode = EpisodeRepository.getInstace();
        getSerieEpisodeMutableLiveData = mRepoEpisode.getSerieEpisode(context, serieLink, season);
    }

    public LiveData<String> getSerieTitle() {
        return getSerieTitleMutableLiveData;
    }

    public LiveData<String> getSerieDescription() {
        return getSerieDescriptionMutableLiveData;
    }

    public LiveData<String> getSerieImage() {
        return getSerieImageMutableLiveData;
    }

    public LiveData<List<String>> getSerieSeason() {
        return getSerieSeasonMutableLiveData;
    }

    public LiveData<List<String>> getSerieEpisode() {
        return getSerieEpisodeMutableLiveData;
    }
}
