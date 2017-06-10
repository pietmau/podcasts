package com.pietrantuono.podcasts.application;


import com.pietrantuono.podcasts.addpodcast.dagger.AddPodcastModule;
import com.pietrantuono.podcasts.addpodcast.dagger.ApiLevelCheckerlModule;
import com.pietrantuono.podcasts.addpodcast.dagger.SearchModelsModule;
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule;
import com.pietrantuono.podcasts.main.dagger.MainModule;
import com.pietrantuono.podcasts.main.dagger.TransitionsModule;
import com.pietrantuono.podcasts.singlepodcast.dagger.SinglePodcastModule;
import com.pietrantuono.podcasts.subscribedpodcasts.di.SubscribedPodcastModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, AddPodcastModule.class, ApiLevelCheckerlModule.class,
        SearchModelsModule.class, PlayerViewModule.class, ImageLoaderModule.class, MainModule.class,
        TransitionsModule.class, SinglePodcastModule.class, SubscribedPodcastModule.class})
public interface ApplicationComponent {

    void inject(App app);

    PlayerViewComponent with(PlayerViewModule playerViewComponent);

}
