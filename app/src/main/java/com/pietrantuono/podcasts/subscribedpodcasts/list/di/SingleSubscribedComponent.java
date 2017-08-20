package com.pietrantuono.podcasts.subscribedpodcasts.list.di;

import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.EpisodesListActivity;
import com.pietrantuono.podcasts.subscribedpodcasts.list.views.EpisodedListRecycler;

import org.jetbrains.annotations.NotNull;

import dagger.Subcomponent;

@SubscribedPodcastScope
@Subcomponent(modules = SingleSubscribedModule.class)
public interface SingleSubscribedComponent {

    void inject(@NotNull EpisodesListActivity episodesListActivity);

    void inject(@NotNull EpisodedListRecycler episodedListRecycler);
}
