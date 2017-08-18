package com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.di


import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.TransitionImageLoadingListener
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import com.pietrantuono.podcasts.downloader.PodcastDownLoadManager
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.main.view.TransitionsFramework
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.service.Player
import com.pietrantuono.podcasts.repository.repository.Repository
import com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.menu.EpisodesListMenuProviderImpl
import com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.model.EpisodesListModel
import com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.model.EpisodesListModelImpl
import com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.presenter.EpisodesListPresenter
import com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.views.EpisodesListAdapter
import dagger.Module
import dagger.Provides

@Module
class SingleSubscribedModule(private val activity: AppCompatActivity) {

    @Provides
    fun provideSinglePodcastPresenter(factory: ViewModelProvider.Factory): EpisodesListPresenter {
        return ViewModelProviders.of(activity, factory).get(EpisodesListPresenter::class.java)
    }

    @Provides
    fun provideTransitionImageLoadingListener(framework: TransitionsFramework): TransitionImageLoadingListener {
        return TransitionImageLoadingListener(activity, framework)
    }

    @Provides
    fun provideSingleSubscribedPodcastsAdapter(loader: SimpleImageLoader, provider: ResourcesProvider): EpisodesListAdapter {
        return EpisodesListAdapter(provider)
    }

    @SubscribedPodcastScope
    @Provides
    fun provideSingleSubscribedModel(repository: Repository, downloadManager: PodcastDownLoadManager): EpisodesListModel {
        return EpisodesListModelImpl(repository, downloadManager)
    }

    @SubscribedPodcastScope
    @Provides
    fun provideSingleSubscribedPodcastMenuProvider(model: EpisodesListModel, context: Context): EpisodesListMenuProviderImpl {
        val inflater = activity.menuInflater
        return EpisodesListMenuProviderImpl(model, inflater)
    }

    @Provides
    fun provideViewModelProviderFactory(checker: ApiLevelChecker,
                                        model: EpisodesListModel,
                                        menuProvider: EpisodesListMenuProviderImpl
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
                return EpisodesListPresenter(model, menuProvider, checker) as T
            }
        }
    }
}



