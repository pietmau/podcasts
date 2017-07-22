package com.pietrantuono.podcasts.subscribedpodcasts.detail.di;


import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;

import com.pietrantuono.podcasts.CrashlyticsWrapper;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.TransitionImageLoadingListener;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider;
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;
import com.pietrantuono.podcasts.main.view.TransitionsFramework;
import com.pietrantuono.podcasts.player.player.MediaSourceCreator;
import com.pietrantuono.podcasts.player.player.service.Player;
import com.pietrantuono.podcasts.subscribedpodcasts.detail.model.SingleSubscribedModel;
import com.pietrantuono.podcasts.subscribedpodcasts.detail.model.SingleSubscribedModelViewModel;
import com.pietrantuono.podcasts.subscribedpodcasts.detail.presenter.SingleSubscribedPodcastPresenter;
import com.pietrantuono.podcasts.subscribedpodcasts.detail.views.SingleSubscribedPodcastsAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class SingleSubscribedModule {
    private AppCompatActivity activity;

    public SingleSubscribedModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    SingleSubscribedPodcastPresenter provideSinglePodcastPresenter(CrashlyticsWrapper warapper, Player player, MediaSourceCreator creator, SingleSubscribedModel model) {
        return new SingleSubscribedPodcastPresenter(model, warapper, creator, player);
    }

    @Provides
    TransitionImageLoadingListener provideTransitionImageLoadingListener(TransitionsFramework framework) {
        return new TransitionImageLoadingListener(activity, framework);
    }

    @Provides
    SingleSubscribedPodcastsAdapter provideSingleSubscribedPodcastsAdapter(SimpleImageLoader loader, ResourcesProvider provider) {
        return new SingleSubscribedPodcastsAdapter(provider);
    }

    @Provides
    SingleSubscribedModel provideSingleSubscribedModel() {
        return ViewModelProviders.of(activity).get(SingleSubscribedModelViewModel.class);
    }


}
