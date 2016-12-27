package com.pietrantuono.podcasts.singlepodcast.dagger;

import android.support.v7.app.AppCompatActivity;

import com.pietrantuono.podcasts.PresenterManager;
import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.addpodcast.presenter.AddPodcastPresenter;
import com.pietrantuono.podcasts.main.presenter.MainPresenter;
import com.pietrantuono.podcasts.singlepodcast.presenter.SinglePodcastApi;
import com.pietrantuono.podcasts.singlepodcast.presenter.SinglePodcastApiImpl;
import com.pietrantuono.podcasts.singlepodcast.presenter.SinglePodcastPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SinglePodcastModule {
    private final PresenterManager presenterManager;

    public SinglePodcastModule(AppCompatActivity activity) {
        PresenterManager manager = (PresenterManager) activity.getLastCustomNonConfigurationInstance();
        if (manager == null) {
            manager = new PresenterManager();
        }
        presenterManager = manager;
    }

    @Provides
    SinglePodcastPresenter provideSinglePodcastPresenter(SinglePodcastApi api) {
        SinglePodcastPresenter addPodcastPresenter = (SinglePodcastPresenter) presenterManager.getPresenter(SinglePodcastPresenter.TAG);
        if (addPodcastPresenter == null) {
            addPodcastPresenter = new SinglePodcastPresenter(api);
            presenterManager.put(SinglePodcastPresenter.TAG, addPodcastPresenter);
        }
        return addPodcastPresenter;
    }

    @Provides
    PresenterManager providePresenterManager() {
        return presenterManager;
    }

    @Provides
    SinglePodcastApi provideSinglePodcastApi(){
        return new SinglePodcastApiImpl();
    }
}
