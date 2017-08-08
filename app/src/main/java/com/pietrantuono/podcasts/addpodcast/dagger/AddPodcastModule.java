package com.pietrantuono.podcasts.addpodcast.dagger;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.pietrantuono.podcasts.CrashlyticsWrapper;
import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel;
import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModelImpl;
import com.pietrantuono.podcasts.addpodcast.model.SearchApi;
import com.pietrantuono.podcasts.addpodcast.presenter.AddPodcastPresenter;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger.SinglePodcastPresenterFactoryFactory;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModel;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SinglePodcastPresenter;
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker;
import com.pietrantuono.podcasts.player.player.MediaSourceCreator;
import com.pietrantuono.podcasts.player.player.service.Player;

import dagger.Module;
import dagger.Provides;

@AddPodcastScope
@Module
public class AddPodcastModule {
    private final FragmentActivity activity;

    public AddPodcastModule(FragmentActivity activity) {
        this.activity = activity;
    }

    @Provides
    AddPodcastPresenter provideAddPodcastPresenter(AddPodcastPresenterFactory factory) {
        return ViewModelProviders.of(activity, factory).get(AddPodcastPresenter.class);
    }

    @Provides
    AddPodcastsModel provideAddPodcastsModel(SearchApi api) {
        return new AddPodcastsModelImpl(api);
    }

    @Provides
    AddPodcastPresenterFactory provideAddPodcastPresenterFactory(AddPodcastsModel addPodcastsModel, ApiLevelChecker apiLevelChecker) {
        return new AddPodcastPresenterFactory(addPodcastsModel, apiLevelChecker);
    }
}
