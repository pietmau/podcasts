package com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SinglePodcastPresenter
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.service.Player


class SinglePodcastPresenterFactoryFactory(private val model: SinglePodcastModel,
                                                    private val crashlyticsWrapper: CrashlyticsWrapper,
                                                    private val player: Player,
                                                    private val creator: MediaSourceCreator) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        return SinglePodcastPresenter(model, crashlyticsWrapper, creator, player) as T
    }

}
