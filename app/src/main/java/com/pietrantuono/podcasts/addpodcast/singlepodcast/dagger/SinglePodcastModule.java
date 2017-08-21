package com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.pietrantuono.podcasts.CrashlyticsWrapper;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModel;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModelImpl;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SinglePodcastPresenter;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.TransitionImageLoadingListener;
import com.pietrantuono.podcasts.apis.SinglePodcastApi;
import com.pietrantuono.podcasts.main.view.TransitionsFramework;
import com.pietrantuono.podcasts.player.player.LocalPlayback;
import com.pietrantuono.podcasts.player.player.MediaSourceCreator;
import com.pietrantuono.podcasts.player.player.Playback;
import com.pietrantuono.podcasts.player.player.service.Player;
import com.pietrantuono.podcasts.repository.repository.Repository;

import dagger.Module;
import dagger.Provides;

@Module
public class SinglePodcastModule {
    private AppCompatActivity activity;

    public SinglePodcastModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    public SinglePodcastModule() {
    }

    @Provides
    SinglePodcastPresenter provideSinglePodcastPresenter(SinglePodcastPresenterFactoryFactory factory) {
        return ViewModelProviders.of(activity, factory).get(SinglePodcastPresenter.class);
    }

    @Provides
    SinglePodcastModel provideSinglePodcastModel(SinglePodcastApi api, Repository repository) {
        return new SinglePodcastModelImpl(api, repository);
    }

    @Provides
    TransitionImageLoadingListener provideTransitionImageLoadingListener(TransitionsFramework framework) {
        return new TransitionImageLoadingListener(framework);
    }

    @Provides
    Playback providesPlayback(Context context, SimpleExoPlayer exoplayer) {
        return new LocalPlayback(context, exoplayer);
    }

    @Provides
    SinglePodcastPresenterFactoryFactory provideSinglePodcastPresenterFactory(SinglePodcastModel model, CrashlyticsWrapper
            crashlyticsWrapper, @Nullable Player player, MediaSourceCreator creator) {
        return new SinglePodcastPresenterFactoryFactory(model, crashlyticsWrapper, player, creator);
    }

}

