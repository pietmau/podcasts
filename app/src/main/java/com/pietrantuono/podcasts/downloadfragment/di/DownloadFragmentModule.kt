package com.pietrantuono.podcasts.downloadfragment.di

import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import com.pietrantuono.podcasts.downloadfragment.model.DownloadFragmentModel
import com.pietrantuono.podcasts.downloadfragment.model.DownloadFragmentModelImpl
import com.pietrantuono.podcasts.downloadfragment.presenter.DownloadFragmentPresenter
import com.pietrantuono.podcasts.downloadfragment.presenter.MessageCreator
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import dagger.Module
import dagger.Provides
import rx.android.schedulers.AndroidSchedulers

@Module
class DownloadFragmentModule {

    @Provides
    fun providePresenter(model: DownloadFragmentModel, messagesCreator: MessageCreator, dowloader: Downloader)
            = DownloadFragmentPresenter(model, messagesCreator, dowloader)

    @Provides
    fun provideModel(repo: PodcastRepo, resources: ResourcesProvider, episodesRepository: EpisodesRepository): DownloadFragmentModel
            = DownloadFragmentModelImpl(repo, episodesRepository, resources, AndroidSchedulers.mainThread())

    @Provides
    fun provideMessageCreator(resources: ResourcesProvider) = MessageCreator(resources)
}
