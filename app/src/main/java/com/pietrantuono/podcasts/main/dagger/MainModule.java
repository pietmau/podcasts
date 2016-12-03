package com.pietrantuono.podcasts.main.dagger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.pietrantuono.podcasts.main.MainPresenter;
import com.pietrantuono.podcasts.main.MainPresenterImpl;
import com.pietrantuono.podcasts.main.model.MainModel;
import com.pietrantuono.podcasts.main.model.ModelService;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    private final Intent modelServiceIntent;
    private final ServiceConnection modelServiceConnection;
    private MainPresenter mainPresenter;
    private MainModel mainModel;

    public MainModule(Activity activity) {
        this.modelServiceIntent = new Intent(activity, ModelService.class);
        activity.startService(modelServiceIntent);
        modelServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder binder) {
                mainModel = ((ModelService.ModelServiceBinder) binder).getModel();
                mainPresenter.onModelConnected(mainModel);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mainPresenter.onModelDisconnected();
            }
        };
    }

    @Provides
    MainPresenter provideMainPresenter() {
        mainPresenter = new MainPresenterImpl();
        return mainPresenter;
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
