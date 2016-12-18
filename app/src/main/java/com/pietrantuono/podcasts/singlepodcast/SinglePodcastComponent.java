package com.pietrantuono.podcasts.singlepodcast;

import com.pietrantuono.podcasts.addpodcast.dagger.ApiLevelCheckerlModule;
import com.pietrantuono.podcasts.main.dagger.TransitionsModule;

import dagger.Component;

@Component(modules = {
        ApiLevelCheckerlModule.class,
        TransitionsModule.class
})
public interface SinglePodcastComponent {

    void inject(SinglePodcastActivity singlePodcastActivity);
}
