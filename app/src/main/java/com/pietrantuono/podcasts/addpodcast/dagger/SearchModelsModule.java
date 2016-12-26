package com.pietrantuono.podcasts.addpodcast.dagger;

import com.pietrantuono.podcasts.addpodcast.model.SearchApi;
import com.pietrantuono.podcasts.addpodcast.model.SearchApiRetrofit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModelsModule {

    @AddPodcastScope
    @Provides
    SearchApi providesSearchApi() {
        return new SearchApiRetrofit();
    }

}
