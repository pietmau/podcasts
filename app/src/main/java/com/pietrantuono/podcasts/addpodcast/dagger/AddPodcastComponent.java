package com.pietrantuono.podcasts.addpodcast.dagger;

import com.pietrantuono.podcasts.addpodcast.view.AddPodcastFragment;
import com.pietrantuono.podcasts.main.dagger.MainModule;
import com.pietrantuono.podcasts.main.model.MainModel;
import com.pietrantuono.podcasts.main.view.MainActivity;

import dagger.Component;
import dagger.Subcomponent;
@Subcomponent(modules = AddPodcastModule.class)
public interface AddPodcastComponent {

    void inject(AddPodcastFragment addPodcastFragment);

}
