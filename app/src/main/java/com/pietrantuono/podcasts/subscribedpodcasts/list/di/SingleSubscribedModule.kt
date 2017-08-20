package com.pietrantuono.podcasts.subscribedpodcasts.list.di


import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.TransitionImageLoadingListener
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import com.pietrantuono.podcasts.downloader.PodcastDownLoadManager
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.main.view.TransitionsFramework
import com.pietrantuono.podcasts.repository.repository.Repository
import com.pietrantuono.podcasts.subscribedpodcasts.list.menu.EpisodesListMenuProviderImpl
import com.pietrantuono.podcasts.subscribedpodcasts.list.model.EpisodesListModel
import com.pietrantuono.podcasts.subscribedpodcasts.list.model.EpisodesListModelImpl
import com.pietrantuono.podcasts.subscribedpodcasts.list.presenter.EpisodesListPresenter
import com.pietrantuono.podcasts.subscribedpodcasts.list.views.EpisodesListAdapter
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



