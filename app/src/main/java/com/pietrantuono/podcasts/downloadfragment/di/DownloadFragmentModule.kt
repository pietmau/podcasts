package com.pietrantuono.podcasts.downloadfragment.di

import com.pietrantuono.podcasts.downloadfragment.model.DownloadFragmentModel
import com.pietrantuono.podcasts.downloadfragment.model.DownloadFragmentModelImpl
import com.pietrantuono.podcasts.downloadfragment.presenter.DownloadFragmentPresenter
import com.pietrantuono.podcasts.repository.EpisodesRepository
import dagger.Module
import dagger.Provides

@Module
class DownloadFragmentModule {

    @Provides
    fun providePresenter(model: DownloadFragmentModel) = DownloadFragmentPresenter(model)

    @Provides
    fun provideModel(repo: EpisodesRepository): DownloadFragmentModel = DownloadFragmentModelImpl(repo)
}

