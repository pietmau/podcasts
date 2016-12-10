package com.pietrantuono.podcasts.addpodcast.dagger;

import com.pietrantuono.podcasts.addpodcast.model.SearchApi;
import com.pietrantuono.podcasts.addpodcast.model.SearchApiRetrofit;
import com.pietrantuono.podcasts.addpodcast.model.SearchPodcastInteractor;
import com.pietrantuono.podcasts.addpodcast.model.SearchPodcastInteractorImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModelsModule {

    @Provides
    SearchPodcastInteractor provideSearchPodcastInteractor(SearchApi searchApi){
        return new SearchPodcastInteractorImpl(searchApi);
    }

    @Provides
    SearchApi providesSearchApi(){
        return new SearchApiRetrofit();
    }

}
