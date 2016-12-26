package com.pietrantuono.podcasts.addpodcast.dagger;

import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel;
import com.pietrantuono.podcasts.addpodcast.model.SearchApi;
import com.pietrantuono.podcasts.addpodcast.presenter.AddPodcastPresenter;
import com.pietrantuono.podcasts.main.model.interactor.AddPodcastsModelImpl;

import dagger.Module;
import dagger.Provides;

@AddPodcastScope
@Module
public class AddPodcastModule {
    private AddPodcastPresenter addPodcastPresenter;

    @Provides
    AddPodcastPresenter provideAddPodcastPresenter(AddPodcastsModel addPodcastsModel) {
        addPodcastPresenter = new AddPodcastPresenter(addPodcastsModel);
        return addPodcastPresenter;
    }

    @Provides
    AddPodcastsModel provideAddPodcastsModel(SearchApi api){
        return new AddPodcastsModelImpl(api);
    }
}
