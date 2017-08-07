package com.pietrantuono.podcasts.subscribedpodcasts.di;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker;
import com.pietrantuono.podcasts.repository.repository.Repository;
import com.pietrantuono.podcasts.subscribedpodcasts.model.SubscribedPodcastModel;
import com.pietrantuono.podcasts.subscribedpodcasts.model.SubscribedPodcastModelImpl;
import com.pietrantuono.podcasts.subscribedpodcasts.presenter.SubscribedPodcastPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SubscribedPodcastModule {
    private final Activity activity;

    public SubscribedPodcastModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    SubscribedPodcastPresenterFactory provideSubscribedPodcastPresenterFactory(SubscribedPodcastModel model,
                                                                               ApiLevelChecker apiLevelChecker) {
        return new SubscribedPodcastPresenterFactory(model, apiLevelChecker);
    }

    @Provides
    SubscribedPodcastModel provideSubscribedPodcastModel(Repository repository) {
        return new SubscribedPodcastModelImpl(repository);
    }

    @Provides
    SubscribedPodcastPresenter provideSubscribedPodcastPresenter(SubscribedPodcastPresenterFactory factory) {
        return ViewModelProviders.of(activity, factory).get(SubscribedPodcastPresenter.class);
    }

}
