package com.pietrantuono.podcasts.addpodcast.dagger;

import com.pietrantuono.podcasts.PresenterManager;
import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel;
import com.pietrantuono.podcasts.addpodcast.model.SearchApi;
import com.pietrantuono.podcasts.addpodcast.presenter.AddPodcastPresenter;
import com.pietrantuono.podcasts.main.model.interactor.AddPodcastsModelImpl;

import dagger.Module;
import dagger.Provides;

@AddPodcastScope
@Module
public class AddPodcastModule {

    @Provides
    AddPodcastPresenter provideAddPodcastPresenter(AddPodcastsModel addPodcastsModel, PresenterManager presenterManager) {
        AddPodcastPresenter addPodcastPresenter = (AddPodcastPresenter) presenterManager.getPresenter(AddPodcastPresenter.TAG);
        if (addPodcastPresenter == null) {
            addPodcastPresenter = new AddPodcastPresenter(addPodcastsModel);
            presenterManager.put(AddPodcastPresenter.TAG, addPodcastPresenter);
        }
        return addPodcastPresenter;
    }

    @Provides
    AddPodcastsModel provideAddPodcastsModel(SearchApi api) {
        return new AddPodcastsModelImpl(api);
    }
}
