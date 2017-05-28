package com.pietrantuono.podcasts.singlepodcast.dagger;


import android.support.v7.app.AppCompatActivity;

import com.pietrantuono.CrashlyticsWrapper;
import com.pietrantuono.interfaceadapters.apis.SinglePodcastApi;
import com.pietrantuono.interfaceadapters.apis.SinglePodcastApiRetrofit;
import com.pietrantuono.podcasts.ImageParser;
import com.pietrantuono.podcasts.PodcastEpisodeParser;
import com.pietrantuono.podcasts.PresenterManager;
import com.pietrantuono.podcasts.main.view.TransitionsFramework;
import com.pietrantuono.podcasts.singlepodcast.model.RealmRepository;
import com.pietrantuono.podcasts.singlepodcast.model.Repository;
import com.pietrantuono.podcasts.singlepodcast.model.SinglePodcastModel;
import com.pietrantuono.podcasts.singlepodcast.model.SinglePodcastModelImpl;
import com.pietrantuono.podcasts.singlepodcast.presenter.SinglePodcastPresenter;
import com.pietrantuono.podcasts.singlepodcast.view.TransitionImageLoadingListener;

import dagger.Module;
import dagger.Provides;

@Module
public class SinglePodcastModule {
    private final PresenterManager presenterManager;
    private final AppCompatActivity activity;

    public SinglePodcastModule(AppCompatActivity activity) {
        this.activity = activity;
        PresenterManager manager = (PresenterManager) activity.getLastCustomNonConfigurationInstance();
        if (manager == null) {
            manager = new PresenterManager();
        }
        presenterManager = manager;
    }

    @Provides
    SinglePodcastPresenter provideSinglePodcastPresenter(SinglePodcastModel model, CrashlyticsWrapper crashlyticsWrapper) {
        SinglePodcastPresenter addPodcastPresenter = (SinglePodcastPresenter) presenterManager.getPresenter(SinglePodcastPresenter.TAG);
        if (addPodcastPresenter == null) {
            addPodcastPresenter = new SinglePodcastPresenter(model, crashlyticsWrapper);
            presenterManager.put(SinglePodcastPresenter.TAG, addPodcastPresenter);
        }
        return addPodcastPresenter;
    }

    @Provides
    PresenterManager providePresenterManager() {
        return presenterManager;
    }

    @Provides
    SinglePodcastApi provideSinglePodcastApi(CrashlyticsWrapper crashlyticsWrapper, PodcastEpisodeParser episodeparser){
        return new SinglePodcastApiRetrofit(crashlyticsWrapper, episodeparser);
    }

    @Provides
    SinglePodcastModel provideSinglePodcastModel(SinglePodcastApi api, Repository repository){
        return new SinglePodcastModelImpl(api, repository);
    }

    @Provides
    PodcastEpisodeParser providePodcastEpisodeParser(ImageParser imageParser){
        return new PodcastEpisodeParser(imageParser);
    }

    @Provides
    Repository provideRepository(){
        return new RealmRepository();
    }

    @Provides
    TransitionImageLoadingListener provideTransitionImageLoadingListener(TransitionsFramework framework){
        return new TransitionImageLoadingListener(activity, framework);
    }
}
