package com.pietrantuono.podcasts.main.dagger;

import android.support.v7.app.AppCompatActivity;

import com.pietrantuono.podcasts.PresenterManager;
import com.pietrantuono.podcasts.main.presenter.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    private final PresenterManager presenterManager;
    private MainPresenter mainPresenter;
    public MainModule(AppCompatActivity activity) {
        PresenterManager manager = (PresenterManager) activity.getLastCustomNonConfigurationInstance();
        if (manager == null) {
            manager = new PresenterManager();
        }
        presenterManager = manager;
    }

    @Provides
    MainPresenter provideMainPresenter() {
        mainPresenter = new MainPresenter();
        return mainPresenter;
    }

    @Provides
    PresenterManager providePresenterManager() {
        return presenterManager;
    }
}
