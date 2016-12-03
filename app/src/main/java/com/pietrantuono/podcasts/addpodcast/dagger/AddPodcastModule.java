package com.pietrantuono.podcasts.addpodcast.dagger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.pietrantuono.podcasts.main.model.MainModel;
import com.pietrantuono.podcasts.main.model.ModelService;
import com.pietrantuono.podcasts.main.presenter.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class AddPodcastModule {
    private final Intent modelServiceIntent;
    private final ServiceConnection modelServiceConnection;
    private MainPresenter mainPresenter;
    private MainModel mainModel;

    public AddPodcastModule(Activity activity) {
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
        mainPresenter = new MainPresenter();
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
