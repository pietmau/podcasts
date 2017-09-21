package com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger


import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModelImpl
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SinglePodcastPresenter
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.BitmapColorExtractor
import com.pietrantuono.podcasts.apis.SinglePodcastApi
import com.pietrantuono.podcasts.main.view.TransitionsHelper
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.player.Player
import com.pietrantuono.podcasts.repository.repository.Repository
import dagger.Module
import dagger.Provides

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
    fun provideSinglePodcastModel(api: SinglePodcastApi, repository: Repository): SinglePodcastModel =
            SinglePodcastModelImpl(api, repository)

    @Provides
    fun provideTransitionImageLoadingListener(framework: TransitionsHelper) = BitmapColorExtractor()

    @Provides
    fun provideSinglePodcastPresenterFactory(model: SinglePodcastModel, crashlyticsWrapper: CrashlyticsWrapper, player: Player?, creator: MediaSourceCreator) =
            SinglePodcastPresenterFactory(model, crashlyticsWrapper, creator)

}

