package com.pietrantuono.podcasts.addpodcast.dagger;

import com.pietrantuono.podcasts.PresenterManager;
import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel;
import com.pietrantuono.podcasts.addpodcast.model.SearchApi;
import com.pietrantuono.podcasts.addpodcast.presenter.AddPodcastPresenter;
import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModelImpl;
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker;

import dagger.Module;
import dagger.Provides;

@AddPodcastScope
@Module
public class AddPodcastModule {

    @Provides
    AddPodcastPresenter provideAddPodcastPresenter(AddPodcastsModel addPodcastsModel, PresenterManager presenterManager, ApiLevelChecker apiLevelChecker) {
        AddPodcastPresenter addPodcastPresenter = (AddPodcastPresenter) presenterManager.getPresenter(AddPodcastPresenter.Companion.getTAG());
        if (addPodcastPresenter == null) {
            addPodcastPresenter = new AddPodcastPresenter(addPodcastsModel, apiLevelChecker);
            presenterManager.put(AddPodcastPresenter.Companion.getTAG(), addPodcastPresenter);
        }
        return addPodcastPresenter;
    }

    @Provides
    AddPodcastsModel provideAddPodcastsModel(SearchApi api) {
        return new AddPodcastsModelImpl(api);
    }
}
