package com.pietrantuono.podcasts.main.dagger;

import com.pietrantuono.podcasts.addpodcast.dagger.AddPodcastComponent;
import com.pietrantuono.podcasts.addpodcast.dagger.ApiLevelCheckerlModule;
import com.pietrantuono.podcasts.addpodcast.dagger.SearchModelsModule;

import com.pietrantuono.podcasts.main.view.MainActivity;
import com.pietrantuono.podcasts.subscribedpodcasts.di.SubscribedPodcastComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        MainModule.class,
        SearchModelsModule.class,
        ApiLevelCheckerlModule.class,
        TransitionsModule.class})
public interface MainComponent {

    void inject(MainActivity mainActivity);

    AddPodcastComponent addPodcastComponent();

    SubscribedPodcastComponent subscribedPodcastComponent();
}
