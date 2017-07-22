package com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.pietrantuono.podcasts.CrashlyticsWrapper;
import com.pietrantuono.podcasts.PresenterManager;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.Repository;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModel;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModelImpl;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SinglePodcastPresenter;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.TransitionImageLoadingListener;
import com.pietrantuono.podcasts.apis.SinglePodcastApi;
import com.pietrantuono.podcasts.apis.SinglePodcastApiRetrofit;
import com.pietrantuono.podcasts.interfaces.ImageParser;
import com.pietrantuono.podcasts.interfaces.PodcastEpisodeParser;
import com.pietrantuono.podcasts.main.view.TransitionsFramework;
import com.pietrantuono.podcasts.player.player.LocalPlayback;
import com.pietrantuono.podcasts.player.player.MediaSourceCreator;
import com.pietrantuono.podcasts.player.player.Playback;
import com.pietrantuono.podcasts.player.player.service.Player;

import dagger.Module;
import dagger.Provides;

@Module
public class SinglePodcastModule {
    private PresenterManager presenterManager;
    private AppCompatActivity activity;


    public SinglePodcastModule(AppCompatActivity activity) {
        this.activity = activity;
        PresenterManager manager = (PresenterManager) activity.getLastCustomNonConfigurationInstance();
        if (manager == null) {
            manager = new PresenterManager();
        }
        presenterManager = manager;
    }

    public SinglePodcastModule() {
    }

    @Provides
    SinglePodcastPresenter provideSinglePodcastPresenter(SinglePodcastModel model, CrashlyticsWrapper
            crashlyticsWrapper, @Nullable Player player, MediaSourceCreator creator) {
        SinglePodcastPresenter addPodcastPresenter = (SinglePodcastPresenter)
                presenterManager.getPresenter(SinglePodcastPresenter.Companion.getTAG());
        if (addPodcastPresenter == null) {
            addPodcastPresenter = new SinglePodcastPresenter(model, crashlyticsWrapper, creator, player);
            presenterManager.put(SinglePodcastPresenter.Companion.getTAG(), addPodcastPresenter);
        }
        return addPodcastPresenter;
    }

    @Provides
    PresenterManager providePresenterManager() {
        return presenterManager;
    }

    @Provides
    SinglePodcastApi provideSinglePodcastApi(CrashlyticsWrapper crashlyticsWrapper, PodcastEpisodeParser episodeparser) {
        return new SinglePodcastApiRetrofit(crashlyticsWrapper, episodeparser);
    }

    @Provides
    SinglePodcastModel provideSinglePodcastModel(SinglePodcastApi api, Repository repository) {
        return new SinglePodcastModelImpl(api, repository);
    }

    @Provides
    PodcastEpisodeParser providePodcastEpisodeParser(ImageParser imageParser) {
        return new PodcastEpisodeParser(imageParser);
    }

    @Provides
    TransitionImageLoadingListener provideTransitionImageLoadingListener(TransitionsFramework framework) {
        return new TransitionImageLoadingListener(activity, framework);
    }

    @Provides
    Playback providesPlayback(Context context, SimpleExoPlayer exoplayer) {
        return new LocalPlayback(context, exoplayer);
    }




}

