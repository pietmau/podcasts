package com.pietrantuono.podcasts.fullscreenplay.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Handler
import android.support.v4.app.FragmentActivity
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.BitmapColorExtractor
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.fullscreenplay.customcontrols.CustomControlsPresenter
import com.pietrantuono.podcasts.fullscreenplay.customcontrols.SimpleExecutor
import com.pietrantuono.podcasts.fullscreenplay.customcontrols.StateResolver
import com.pietrantuono.podcasts.fullscreenplay.model.FullscreenModel
import com.pietrantuono.podcasts.fullscreenplay.model.FullscreenModelImpl
import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import com.pietrantuono.podcasts.main.view.TransitionsHelper
import dagger.Module
import dagger.Provides
import repo.repository.EpisodesRepository
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.Executors

@Module
class FullscreenModule(private val activity: FragmentActivity) {

    @Provides
    fun provideFullscreenPresenter(model: FullscreenModel, factory: FullscreenPresenterFactory): FullscreenPresenter {
        return ViewModelProviders.of(activity, factory).get(FullscreenPresenter::class.java);
    }

    @Provides
    fun provideTransitionImageLoadingListener(framework: TransitionsHelper): BitmapColorExtractor {
        return BitmapColorExtractor()
    }

    @Provides
    fun provideModel(repository: EpisodesRepository): FullscreenModel
            = FullscreenModelImpl(repository, AndroidSchedulers.mainThread())


    @Provides
    fun provideFullscreenPresenterFactory(model: FullscreenModel, apiLevelChecker: ApiLevelChecker): FullscreenPresenterFactory {
        return FullscreenPresenterFactory(model, apiLevelChecker)
    }

    @Provides
    fun provideCustomControlsPresenter(context: Context, resolver: StateResolver, logger: DebugLogger, executor: SimpleExecutor) =
            CustomControlsPresenter(context, resolver, logger, executor)

    @Provides
    fun provideExecutor() = SimpleExecutor(Executors.newSingleThreadScheduledExecutor(), Handler())

    @Provides
    fun provideStateResolver() = StateResolver()
}

class FullscreenPresenterFactory(private val model: FullscreenModel
                                 , val apiLevelChecker: ApiLevelChecker)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        return FullscreenPresenter(model) as T
    }
}
