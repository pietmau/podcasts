package com.pietrantuono.podcasts.addpodcast.dagger;

import com.pietrantuono.podcasts.addpodcast.model.SearchApi;
import com.pietrantuono.podcasts.addpodcast.model.SearchApiRetrofit;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModelsModule {

    @Provides
    SearchApi providesSearchApi() {
        return new SearchApiRetrofit();
    }

}
