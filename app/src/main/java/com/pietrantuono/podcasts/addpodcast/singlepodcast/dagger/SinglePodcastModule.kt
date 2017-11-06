package com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger


import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModelImpl
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SinglePodcastPresenter
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.BitmapColorExtractor
import com.pietrantuono.podcasts.apis.SinglePodcastApi
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import com.pietrantuono.podcasts.main.view.TransitionsHelper
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import dagger.Module
import dagger.Provides
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

@Module
class SinglePodcastModule {
    private var activity: AppCompatActivity? = null

    constructor(activity: AppCompatActivity?) {
        this.activity = activity
    }

    constructor() {}

    @Provides
    fun provideSinglePodcastPresenter(factory: SinglePodcastPresenterFactory): SinglePodcastPresenter =
            ViewModelProviders.of(activity!!, factory).get(SinglePodcastPresenter::class.java)

    @Provides
    fun provideSinglePodcastModel(api: SinglePodcastApi, repository: PodcastRepo, dowloader: Downloader): SinglePodcastModel =
            SinglePodcastModelImpl(api, repository, Schedulers.io(), AndroidSchedulers.mainThread())

    @Provides
    fun provideTransitionImageLoadingListener(framework: TransitionsHelper) = BitmapColorExtractor()

    @Provides
    fun provideSinglePodcastPresenterFactory(model: SinglePodcastModel, crashlyticsWrapper: CrashlyticsWrapper) =
            SinglePodcastPresenterFactory(model, crashlyticsWrapper)

}

