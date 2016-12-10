package com.pietrantuono.podcasts.main.model;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel;
import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;
import com.pietrantuono.podcasts.main.dagger.DaggerMainComponent;
import com.pietrantuono.podcasts.main.dagger.MainModule;
import com.pietrantuono.podcasts.main.model.interactor.ModelInteractor;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;

public class ModelService extends Service implements MainModel, AddPodcastsModel {
    @Inject ModelInteractor interactor;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ModelServiceBinder();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        DaggerMainComponent.builder().mainModule(new MainModule()).build().inject(ModelService.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void unsubscribeFromSearch() {
        interactor.unsubscribeFromSearch();
    }

    @Override
    public void subscribeToSearch(Observer<List<PodcastSearchResult>> observer) {
        interactor.subscribeToSearch(observer);
    }

    @Override
    public void searchPodcasts(String query) {
        interactor.searchPodcasts(query);
    }

    public class ModelServiceBinder extends Binder {
        public ModelService getModel() {
            return ModelService.this;
        }
    }

}
