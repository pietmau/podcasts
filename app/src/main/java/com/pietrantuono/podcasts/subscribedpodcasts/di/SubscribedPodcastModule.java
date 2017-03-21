package com.pietrantuono.podcasts.subscribedpodcasts.di;

import com.pietrantuono.podcasts.singlepodcast.model.RealmRepository;
import com.pietrantuono.podcasts.singlepodcast.model.Repository;
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
    SubscribedPodcastModel provideSubscribedPodcastModel(Repository repository){
        return new SubscribedPodcastModelImpl(repository);
    }

    @Provides
    Repository provideRepository(){
        return new RealmRepository();
    }

}
