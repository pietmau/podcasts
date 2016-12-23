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
    private static SearchPodcastInteractorImpl searchPodcastInteractor;

    @Singleton
    @Provides
    SearchPodcastInteractor provideSearchPodcastInteractor(SearchApi searchApi) {
        if (searchPodcastInteractor == null) {
            searchPodcastInteractor = new SearchPodcastInteractorImpl(searchApi);
        }
        return searchPodcastInteractor;
    }

    @Singleton
    @Provides
    SearchApi providesSearchApi() {
        return new SearchApiRetrofit();
    }

}
