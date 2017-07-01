package com.pietrantuono.podcasts.application;


import android.support.annotation.Nullable;

import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsRecycler;
import com.pietrantuono.podcasts.addpodcast.dagger.ApiLevelCheckerlModule;
import com.pietrantuono.podcasts.addpodcast.dagger.SearchModelsModule;
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule;
import com.pietrantuono.podcasts.main.dagger.MainModule;
import com.pietrantuono.podcasts.main.dagger.TransitionsModule;
import com.pietrantuono.podcasts.player.player.service.Player;
import com.pietrantuono.podcasts.singlepodcast.customviews.EpisodesRecycler;
import com.pietrantuono.podcasts.singlepodcast.dagger.SinglePodcastModule;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiLevelCheckerlModule.class,
        SearchModelsModule.class, ImageLoaderModule.class,
        TransitionsModule.class,})
public interface ApplicationComponent {

    void inject(@NotNull App app);

    void inject(@NotNull PodcastsRecycler podcastsRecycler);

    void inject(@NotNull EpisodesRecycler episodesRecycler);

    @NotNull MainComponent with(MainModule mainModule);

    @NotNull SinglePodcastComponent with(SinglePodcastModule singlePodcastModule);

    @Nullable public Player player();

}
