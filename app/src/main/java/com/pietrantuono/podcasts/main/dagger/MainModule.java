package com.pietrantuono.podcasts.main.dagger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.pietrantuono.podcasts.main.MainPresenter;
import com.pietrantuono.podcasts.main.MainPresenterImpl;
import com.pietrantuono.podcasts.main.ModelService;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    private final Intent modelServiceIntent;
    private final ServiceConnection modelServiceConnection;

    public MainModule(Activity activity) {
        this.modelServiceIntent = new Intent(activity, ModelService.class);
        activity.startService(modelServiceIntent);
        modelServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
        activity.bindService(modelServiceIntent, modelServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Provides
    MainPresenter provideMainPresenter() {
        return new MainPresenterImpl();
    }

    @Provides
    ServiceConnection provideServiceConnection() {
        return modelServiceConnection;
    }

    @Provides
    Intent provideServiceIntent() {
        return modelServiceIntent;
    }
}
