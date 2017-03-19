package com.pietrantuono.podcasts.subscribedpodcasts.di;

import com.pietrantuono.podcasts.subscribedpodcasts.model.SubscribedPodcastModel;
import com.pietrantuono.podcasts.subscribedpodcasts.model.SubscribedPodcastModelImpl;
import com.pietrantuono.podcasts.subscribedpodcasts.presenter.SubscribedPodcastPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SubscribedPodcastModule {

    @Provides
    SubscribedPodcastPresenter provideSubscribedPodcastPresenter(SubscribedPodcastModel model){
        return new SubscribedPodcastPresenter(model);
    }

    @Provides
    SubscribedPodcastModel provideSubscribedPodcastModel(){
        return new SubscribedPodcastModelImpl();
    }

}
