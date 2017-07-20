package com.pietrantuono.podcasts.subscribedpodcasts.detail.di;


import android.support.v7.app.AppCompatActivity;

import com.pietrantuono.podcasts.subscribedpodcasts.detail.presenter.SingleSubscribedPodcastPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SingleSubscribedModule {
    private final SingleSubscribedPodcastPresenter presenter;

    public SingleSubscribedModule(AppCompatActivity activity) {
        SingleSubscribedPodcastPresenter presenter = (SingleSubscribedPodcastPresenter) activity.getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            presenter = new SingleSubscribedPodcastPresenter();
        }
        this.presenter = presenter;
    }

    @Provides
    SingleSubscribedPodcastPresenter provideSinglePodcastPresenter() {
        return presenter;
    }
}
