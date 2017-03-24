package com.pietrantuono.podcasts.subscribedpodcasts.di;

import com.pietrantuono.podcasts.PresenterManager;
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
    SubscribedPodcastPresenter provideSubscribedPodcastPresenter(SubscribedPodcastModel model, PresenterManager presenterManager){
        SubscribedPodcastPresenter podcastPresenter = (SubscribedPodcastPresenter) presenterManager.getPresenter(SubscribedPodcastPresenter.TAG);
        if (podcastPresenter == null) {
            podcastPresenter = new SubscribedPodcastPresenter(model);
            presenterManager.put(SubscribedPodcastPresenter.TAG, podcastPresenter);
        }
        return podcastPresenter;
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
