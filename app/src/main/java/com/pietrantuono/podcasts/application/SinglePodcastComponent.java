package com.pietrantuono.podcasts.application;

import com.pietrantuono.podcasts.player.player.service.PlayerService;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger.SinglePodcastModule;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.AddSinglePodcastActivity;

import org.jetbrains.annotations.NotNull;

import dagger.Subcomponent;

@Subcomponent(modules = SinglePodcastModule.class)
public interface
SinglePodcastComponent {

    void inject(@NotNull AddSinglePodcastActivity singlePodcastActivity);

    void inject(@NotNull PlayerService playerService);
}
