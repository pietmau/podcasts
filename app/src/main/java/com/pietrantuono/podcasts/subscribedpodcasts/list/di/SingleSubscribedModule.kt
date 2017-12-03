package com.pietrantuono.podcasts.subscribedpodcasts.list.di


import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.BitmapColorExtractor
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.main.view.TransitionsHelper
import com.pietrantuono.podcasts.subscribedpodcasts.list.menu.EpisodesListMenuProviderImpl
import com.pietrantuono.podcasts.subscribedpodcasts.list.model.EpisodesListModel
import com.pietrantuono.podcasts.subscribedpodcasts.list.model.EpisodesListModelImpl
import com.pietrantuono.podcasts.subscribedpodcasts.list.presenter.EpisodesListPresenter
import com.pietrantuono.podcasts.subscribedpodcasts.list.views.EpisodesListAdapter
import dagger.Module
import dagger.Provides
import repo.repository.PodcastRepo

@Module
class SingleSubscribedModule(private val activity: AppCompatActivity) {

    @Provides
    fun provideSinglePodcastPresenter(factory: ViewModelProvider.Factory): EpisodesListPresenter {
        return ViewModelProviders.of(activity, factory).get(EpisodesListPresenter::class.java)
    }

    @Provides
    fun provideTransitionImageLoadingListener(framework: TransitionsHelper): BitmapColorExtractor {
        return BitmapColorExtractor()
    }

    @Provides
    fun provideSingleSubscribedPodcastsAdapter(loader: SimpleImageLoader, provider: ResourcesProvider): EpisodesListAdapter {
        return EpisodesListAdapter(provider)
    }

    @SubscribedPodcastScope
    @Provides
    fun provideSingleSubscribedModel(repository: PodcastRepo, downloadManager: Downloader): EpisodesListModel {
        return EpisodesListModelImpl(repository, downloadManager)
    }

    @SubscribedPodcastScope
    @Provides
    fun provideSingleSubscribedPodcastMenuProvider(model: EpisodesListModel, context: Context): EpisodesListMenuProviderImpl {
        return EpisodesListMenuProviderImpl(model)
    }

    @Provides
    fun provideViewModelProviderFactory(model: EpisodesListModel, menuProvider: EpisodesListMenuProviderImpl
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
                return EpisodesListPresenter(model, menuProvider) as T
            }
        }
    }
}



