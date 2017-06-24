package com.pietrantuono.podcasts.singlepodcast.dagger;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.pietrantuono.CrashlyticsWrapper;
import com.pietrantuono.interfaceadapters.apis.SinglePodcastApi;
import com.pietrantuono.interfaceadapters.apis.SinglePodcastApiRetrofit;
import com.pietrantuono.podcasts.ImageParser;
import com.pietrantuono.podcasts.PodcastEpisodeParser;
import com.pietrantuono.podcasts.PresenterManager;
import com.pietrantuono.podcasts.main.view.TransitionsFramework;
import com.pietrantuono.podcasts.player.player.LocalPlayback;
import com.pietrantuono.podcasts.player.player.Playback;
import com.pietrantuono.podcasts.singlepodcast.model.Repository;
import com.pietrantuono.podcasts.singlepodcast.model.SinglePodcastModel;
import com.pietrantuono.podcasts.singlepodcast.model.SinglePodcastModelImpl;
import com.pietrantuono.podcasts.singlepodcast.presenter.MediaSourceCreator;
import com.pietrantuono.podcasts.singlepodcast.presenter.SinglePodcastPresenter;
import com.pietrantuono.podcasts.singlepodcast.view.TransitionImageLoadingListener;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.google.common.net.HttpHeaders.USER_AGENT;

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
            crashlyticsWrapper, Playback playback, MediaSourceCreator creator) {
        SinglePodcastPresenter addPodcastPresenter = (SinglePodcastPresenter)
                presenterManager.getPresenter(SinglePodcastPresenter.Companion.getTAG());
        if (addPodcastPresenter == null) {
            addPodcastPresenter = new SinglePodcastPresenter(model, crashlyticsWrapper);
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

    @Provides
    MediaSourceCreator provideMediaSourceCreator(DataSource.Factory factory,
                                                 ExtractorMediaSource.EventListener logger) {
        return new MediaSourceCreator(factory, new Handler(Looper.myLooper()), logger);
    }

    @Provides
    DataSource.Factory provideDataSourceFactory(@Named(USER_AGENT) String userAgent, Context context) {
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        return new DefaultDataSourceFactory(context, defaultBandwidthMeter,
                new DefaultHttpDataSourceFactory(userAgent, defaultBandwidthMeter));
    }

    @Provides
    @Named(USER_AGENT)
    String getAgent() {
        return USER_AGENT;
    }

    @Provides
    ExtractorMediaSource.EventListener provideExtractorMediaSourceEventListener() {
        return error -> {
        };
    }


}

