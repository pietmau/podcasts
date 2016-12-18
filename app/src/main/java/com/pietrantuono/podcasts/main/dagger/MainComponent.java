package com.pietrantuono.podcasts.main.dagger;

import com.pietrantuono.podcasts.addpodcast.dagger.AddPodcastComponent;
import com.pietrantuono.podcasts.addpodcast.dagger.ApiLevelCheckerlModule;
import com.pietrantuono.podcasts.addpodcast.dagger.SearchModelsModule;
import com.pietrantuono.podcasts.main.model.ModelService;
import com.pietrantuono.podcasts.main.view.MainActivity;

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

    void inject(ModelService mainActivity);

    AddPodcastComponent newAddPodcastComponent();
}
