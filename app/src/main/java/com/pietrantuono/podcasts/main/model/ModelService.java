package com.pietrantuono.podcasts.main.model;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel;
import com.pietrantuono.podcasts.addpodcast.model.SearchPodcastItem;

import java.util.List;

import hugo.weaving.DebugLog;
import rx.Observer;

public class ModelService extends Service implements MainModel, AddPodcastsModel {
    @DebugLog
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ModelServiceBinder();
    }

    @DebugLog
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @DebugLog
    @Override
    public void onCreate() {
    }

    @DebugLog
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @DebugLog
    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @DebugLog
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @DebugLog
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void unsubscribeFromSearch() {

    }

    @Override
    public void subscribeToSearch(Observer<List<SearchPodcastItem>> observer) {

    }

    @Override
    public void searchPodcasts(String query) {

    }

    public class ModelServiceBinder extends Binder {
        public ModelService getModel() {
            return ModelService.this;
        }
    }

}
