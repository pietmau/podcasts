package com.pietrantuono.podcasts.subscribedpodcasts.detail.di


import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity

import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.TransitionImageLoadingListener
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.main.view.TransitionsFramework
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.service.Player
import com.pietrantuono.podcasts.subscribedpodcasts.detail.model.SingleSubscribedModel
import com.pietrantuono.podcasts.subscribedpodcasts.detail.model.SingleSubscribedModelImpl
import com.pietrantuono.podcasts.subscribedpodcasts.detail.presenter.SingleSubscribedPodcastPresenter
import com.pietrantuono.podcasts.subscribedpodcasts.detail.views.SingleSubscribedPodcastsAdapter

import dagger.Module
import dagger.Provides
import io.realm.Realm

@Module
class SingleSubscribedModule(private val activity: AppCompatActivity) {

    @Provides
    internal fun provideSinglePodcastPresenter(factory: ViewModelProvider.Factory): SingleSubscribedPodcastPresenter {
        return ViewModelProviders.of(activity, factory).get(SingleSubscribedPodcastPresenter::class.java)
    }

    @Provides
    internal fun provideTransitionImageLoadingListener(framework: TransitionsFramework): TransitionImageLoadingListener {
        return TransitionImageLoadingListener(activity, framework)
    }

    @Provides
    internal fun provideSingleSubscribedPodcastsAdapter(loader: SimpleImageLoader, provider: ResourcesProvider): SingleSubscribedPodcastsAdapter {
        return SingleSubscribedPodcastsAdapter(provider)
    }

    @Provides
    internal fun provideSingleSubscribedModel(realm: Realm): SingleSubscribedModel {
        return SingleSubscribedModelImpl(realm)
    }

    @Provides
    internal fun provideViewModelProviderFactory(wrapper: CrashlyticsWrapper, player: Player, creator: MediaSourceCreator, model: SingleSubscribedModel): ViewModelProvider.Factory {

        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
                return SingleSubscribedPodcastPresenter(model,wrapper,creator,player) as T
            }
        }
    }
}



