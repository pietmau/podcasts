package com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger


import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModelImpl
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SinglePodcastPresenter
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.ViewStateSetter
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.BitmapColorExtractor
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.apis.SinglePodcastApi
import com.pietrantuono.podcasts.main.view.TransitionsHelper
import dagger.Module
import dagger.Provides
import repo.repository.PodcastRepo
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


@Module
class SinglePodcastModule {
    private var activity: AppCompatActivity? = null

    constructor()

    constructor(activity: AppCompatActivity?) {
        this.activity = activity
    }

    @Provides
    fun provideSinglePodcastPresenter(factory: SinglePodcastPresenterFactory): SinglePodcastPresenter =
            ViewModelProviders.of(activity!!, factory).get(SinglePodcastPresenter::class.java)

    @SinglePodcastScope
    @Provides
    fun provideSinglePodcastModel(api: SinglePodcastApi, repository: PodcastRepo): SinglePodcastModel =
            SinglePodcastModelImpl(api, repository, Schedulers.io(), AndroidSchedulers.mainThread())

    @Provides
    fun provideTransitionImageLoadingListener(framework: TransitionsHelper) = BitmapColorExtractor()

    @Provides
    fun provideSinglePodcastPresenterFactory(model: SinglePodcastModel, crashlyticsWrapper: CrashlyticsWrapper, resources: ResourcesProvider, viewStateSetter: ViewStateSetter) =
            SinglePodcastPresenterFactory(model, viewStateSetter)

    @Provides
    fun provideViewStateSetter(model: SinglePodcastModel) = ViewStateSetter(model)

}

