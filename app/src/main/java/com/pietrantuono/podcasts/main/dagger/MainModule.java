package com.pietrantuono.podcasts.main.dagger;

import android.app.Activity;

import com.pietrantuono.podcasts.main.model.interactor.AddPodcastsModelImpl;
import com.pietrantuono.podcasts.main.presenter.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    private MainPresenter mainPresenter;

    public MainModule(Activity activity) {
    }

    @Provides
    MainPresenter provideMainPresenter() {
        mainPresenter = new MainPresenter();
        return mainPresenter;
    }

}
