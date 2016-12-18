package com.pietrantuono.podcasts.main.dagger;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.pietrantuono.podcasts.addpodcast.model.SearchPodcastInteractor;
import com.pietrantuono.podcasts.main.model.interactor.ModelInteractor;
import com.pietrantuono.podcasts.main.model.interactor.ModelInteractorImpl;
import com.pietrantuono.podcasts.main.presenter.MainPresenter;
import com.pietrantuono.podcasts.main.model.ModelService;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    private final Intent modelServiceIntent;
    private MainPresenter mainPresenter;

    public MainModule(Activity activity) {
        this.modelServiceIntent = new Intent(activity, ModelService.class);
        activity.startService(modelServiceIntent);
    }

    public MainModule() {
        modelServiceIntent = null;
    }

    @Provides
    MainPresenter provideMainPresenter() {
        mainPresenter = new MainPresenter();
        return mainPresenter;
    }

    @Provides
    ServiceConnection provideServiceConnection() {
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder binder) {
                ModelService mainModel = ((ModelService.ModelServiceBinder) binder).getModel();
                mainPresenter.onModelConnected(mainModel);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mainPresenter.onModelDisconnected();
            }
        };
    }

    @Provides
    Intent provideServiceIntent() {
        return modelServiceIntent;
    }

    @Provides
    ModelInteractor provideModelInteractor(SearchPodcastInteractor searchPodcastInteractor) {
        return new ModelInteractorImpl(searchPodcastInteractor);
    }
}
