package com.pietrantuono.podcasts.main;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import hugo.weaving.DebugLog;

public class ModelService extends Service implements MainModel {
    @DebugLog
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
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
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
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

}
