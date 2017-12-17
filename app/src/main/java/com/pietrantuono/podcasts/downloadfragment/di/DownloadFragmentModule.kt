package com.pietrantuono.podcasts.downloadfragment.di

import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import com.pietrantuono.podcasts.downloadfragment.model.DownloadFragmentModel
import com.pietrantuono.podcasts.downloadfragment.model.DownloadFragmentModelImpl
import com.pietrantuono.podcasts.downloadfragment.presenter.DataChecker
import com.pietrantuono.podcasts.downloadfragment.presenter.DownloadFragmentPresenter
import com.pietrantuono.podcasts.downloadfragment.presenter.MessageCreator
import dagger.Module
import dagger.Provides
import repo.repository.EpisodesRepository
import repo.repository.PodcastRepo
import rx.android.schedulers.AndroidSchedulers

@Module
class DownloadFragmentModule {

    @Provides
    fun providePresenter(model: DownloadFragmentModel, messagesCreator: MessageCreator, dowloader: Downloader, checker: DataChecker)
            = DownloadFragmentPresenter(model, messagesCreator, dowloader, checker)

    @Provides
    fun provideModel(repo: PodcastRepo, resources: ResourcesProvider, episodesRepository: EpisodesRepository, logger: DebugLogger): DownloadFragmentModel
            = DownloadFragmentModelImpl(repo, resources, AndroidSchedulers.mainThread(), logger)

    @Provides
    fun provideMessageCreator(resources: ResourcesProvider) = MessageCreator(resources)

    @Provides
    fun provideDataChecker() = DataChecker()
}

