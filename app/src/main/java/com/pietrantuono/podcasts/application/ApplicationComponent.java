package com.pietrantuono.podcasts.application;


import android.support.annotation.Nullable;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsRecycler;
import com.pietrantuono.podcasts.addpodcast.dagger.ApiLevelCheckerlModule;
import com.pietrantuono.podcasts.addpodcast.dagger.SearchModelsModule;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.customviews.EpisodesRecycler;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger.SinglePodcastModule;
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule;
import com.pietrantuono.podcasts.main.dagger.MainModule;
import com.pietrantuono.podcasts.main.dagger.TransitionsModule;
import com.pietrantuono.podcasts.media.MediaModule;
import com.pietrantuono.podcasts.repository.RepositoryModule;
import com.pietrantuono.podcasts.repository.SaveEpisodeIntentService;
import com.pietrantuono.podcasts.subscribedpodcasts.detail.di.SingleSubscribedComponent;
import com.pietrantuono.podcasts.subscribedpodcasts.detail.di.SingleSubscribedModule;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiLevelCheckerlModule.class,
        SearchModelsModule.class, ImageLoaderModule.class,
        TransitionsModule.class, MediaModule.class, RepositoryModule.class})
public interface ApplicationComponent {

    void inject(@NotNull App app);

    void inject(@NotNull PodcastsRecycler podcastsRecycler);

    void inject(@NotNull EpisodesRecycler episodesRecycler);

    void inject(@NotNull SaveEpisodeIntentService saveEpisodeIntentService);

    @NotNull
    MainComponent with(MainModule mainModule);

    @NotNull
    SinglePodcastComponent with(SinglePodcastModule singlePodcastModule);

    @NotNull
    SingleSubscribedComponent with(SingleSubscribedModule mainModule);

    @Nullable
    SimpleExoPlayer simpleExoPlayer();

}
