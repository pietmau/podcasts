package com.pietrantuono.podcasts.main.dagger;


import com.pietrantuono.podcasts.addpodcast.customviews.PodcastHolder;
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsRecycler;
import com.pietrantuono.podcasts.singlepodcast.customviews.EpisodesRecycler;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = ImageLoaderModule.class)
public interface ImageLoaderComponent {

    void inject(PodcastsRecycler podcastsRecycler);

    void inject(PodcastHolder podcastHolder);

    void inject(EpisodesRecycler episodesRecycler);
}
