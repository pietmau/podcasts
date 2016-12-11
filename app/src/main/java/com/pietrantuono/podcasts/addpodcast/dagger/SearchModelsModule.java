package com.pietrantuono.podcasts.addpodcast.dagger;

import com.pietrantuono.podcasts.addpodcast.model.SearchApi;
import com.pietrantuono.podcasts.addpodcast.model.SearchApiRetrofit;
import com.pietrantuono.podcasts.addpodcast.model.SearchPodcastInteractor;
import com.pietrantuono.podcasts.addpodcast.model.SearchPodcastInteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModelsModule {
    private static SearchPodcastInteractorImpl foo;

    @Singleton
    @Provides
    SearchPodcastInteractor provideSearchPodcastInteractor(SearchApi searchApi) {
        if (foo == null) {
            foo = new SearchPodcastInteractorImpl(searchApi);
        }
        return foo;
    }

    @Singleton
    @Provides
    SearchApi providesSearchApi() {
        return new SearchApiRetrofit();
    }

}
