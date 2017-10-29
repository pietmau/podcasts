package com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SinglePodcastPresenter


class SinglePodcastPresenterFactory(private val model: SinglePodcastModel,
                                    private val crashlyticsWrapper: CrashlyticsWrapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SinglePodcastPresenter(model, crashlyticsWrapper) as T
    }

}
