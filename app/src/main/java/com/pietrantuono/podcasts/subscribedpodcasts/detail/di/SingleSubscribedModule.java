package com.pietrantuono.podcasts.subscribedpodcasts.detail.di;


import android.support.v7.app.AppCompatActivity;

import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.TransitionImageLoadingListener;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider;
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;
import com.pietrantuono.podcasts.main.view.TransitionsFramework;
import com.pietrantuono.podcasts.subscribedpodcasts.detail.presenter.SingleSubscribedPodcastPresenter;
import com.pietrantuono.podcasts.subscribedpodcasts.detail.views.SingleSubscribedPodcastsAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class SingleSubscribedModule {
    private final SingleSubscribedPodcastPresenter presenter;
    private AppCompatActivity activity;

    public SingleSubscribedModule(AppCompatActivity activity) {
        this.activity = activity;
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

    @Provides
    TransitionImageLoadingListener provideTransitionImageLoadingListener(TransitionsFramework framework) {
        return new TransitionImageLoadingListener(activity, framework);
    }

    @Provides
    SingleSubscribedPodcastsAdapter provideSingleSubscribedPodcastsAdapter(SimpleImageLoader loader, ResourcesProvider provider) {
        return new SingleSubscribedPodcastsAdapter(provider);
    }
}
