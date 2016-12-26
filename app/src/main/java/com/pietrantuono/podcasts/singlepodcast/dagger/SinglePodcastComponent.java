package com.pietrantuono.podcasts.singlepodcast.dagger;

import com.pietrantuono.podcasts.addpodcast.dagger.ApiLevelCheckerlModule;
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule;
import com.pietrantuono.podcasts.main.dagger.TransitionsModule;
import com.pietrantuono.podcasts.singlepodcast.view.SinglePodcastActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {
        ApiLevelCheckerlModule.class,
        TransitionsModule.class,
        ImageLoaderModule.class,
        SinglePodcastModule.class
})
public interface SinglePodcastComponent {

    void inject(SinglePodcastActivity singlePodcastActivity);
}
