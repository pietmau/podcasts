package com.pietrantuono.podcasts.fullscreenplay.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.BitmapColorExtractor
import com.pietrantuono.podcasts.fullscreenplay.model.FullscreenModel
import com.pietrantuono.podcasts.fullscreenplay.model.FullscreenModelImpl
import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import com.pietrantuono.podcasts.fullscreenplay.presenter.ServiceConnector
import com.pietrantuono.podcasts.main.view.Transitions
import com.pietrantuono.podcasts.player.player.service.Player
import com.pietrantuono.podcasts.repository.EpisodesRepository
import dagger.Module
import dagger.Provides
import rx.android.schedulers.AndroidSchedulers

@Module
class FullscreenModule(private val activity: FragmentActivity) {

    @Provides
    fun provideFullscreenPresenter(model: FullscreenModel, player: Player?, factory: FullscreenPresenterFactory): FullscreenPresenter {
        return ViewModelProviders.of(activity, factory).get(FullscreenPresenter::class.java);
    }

    @Provides
    fun provideTransitionImageLoadingListener(framework: Transitions): BitmapColorExtractor {
        return BitmapColorExtractor()
    }

    @Provides
    fun provideModel(repository: EpisodesRepository): FullscreenModel {
        return FullscreenModelImpl(repository, AndroidSchedulers.mainThread())
    }

    @Provides
    fun provideFullscreenPresenterFactory(model: FullscreenModel, player: Player?): FullscreenPresenterFactory {
        return FullscreenPresenterFactory(model, player, ServiceConnector())
    }
}

class FullscreenPresenterFactory(private val model: FullscreenModel, val player: Player?, val connector: ServiceConnector) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        return FullscreenPresenter(model, player, connector) as T
    }
}
