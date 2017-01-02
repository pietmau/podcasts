package com.pietrantuono.podcasts.singlepodcast.dagger;


import android.support.v7.app.AppCompatActivity;

import com.pietrantuono.CrashlyticsWrapper;
import com.pietrantuono.interfaceadapters.apis.SinglePodcastApi;
import com.pietrantuono.interfaceadapters.apis.SinglePodcastApiRetrofit;
import com.pietrantuono.podcasts.PresenterManager;
import com.pietrantuono.podcasts.singlepodcast.model.SinglePodcastModel;
import com.pietrantuono.podcasts.singlepodcast.model.SinglePodcastModelImpl;
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
    SinglePodcastApi provideSinglePodcastApi(CrashlyticsWrapper crashlyticsWrapper){
        return new SinglePodcastApiRetrofit(crashlyticsWrapper);
    }

    @Provides
    SinglePodcastModel provideSinglePodcastModel(SinglePodcastApi api){
        return new SinglePodcastModelImpl(api);
    }
}
