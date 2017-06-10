package com.pietrantuono.podcasts.application;

import com.pietrantuono.podcasts.addpodcast.dagger.AddPodcastComponent;
import com.pietrantuono.podcasts.addpodcast.dagger.AddPodcastModule;
import com.pietrantuono.podcasts.main.dagger.MainModule;
import com.pietrantuono.podcasts.main.view.MainActivity;
import com.pietrantuono.podcasts.subscribedpodcasts.di.SubscribedPodcastComponent;
import com.pietrantuono.podcasts.subscribedpodcasts.di.SubscribedPodcastModule;

import dagger.Subcomponent;

@Subcomponent(modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);

    SubscribedPodcastComponent with(SubscribedPodcastModule subscribedPodcastModule);

    AddPodcastComponent with(AddPodcastModule addPodcastModule);
}
