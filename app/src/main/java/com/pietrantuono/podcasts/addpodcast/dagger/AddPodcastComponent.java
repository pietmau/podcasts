package com.pietrantuono.podcasts.addpodcast.dagger;

import com.pietrantuono.podcasts.addpodcast.view.AddPodcastFragment;

import dagger.Subcomponent;

@Subcomponent(modules = {AddPodcastModule.class, SearchModelsModule.class})
public interface AddPodcastComponent {

    void inject(AddPodcastFragment addPodcastFragment);

}
