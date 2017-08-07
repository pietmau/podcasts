package com.pietrantuono.podcasts.subscribedpodcasts.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import com.pietrantuono.podcasts.subscribedpodcasts.model.SubscribedPodcastModel
import com.pietrantuono.podcasts.subscribedpodcasts.presenter.SubscribedPodcastPresenter


class SubscribedPodcastPresenterFactory(private val model: SubscribedPodcastModel,
                                        private val apiLevelChecker: ApiLevelChecker) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        return SubscribedPodcastPresenter(model, apiLevelChecker) as T
    }
}