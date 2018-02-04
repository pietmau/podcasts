package com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SinglePodcastPresenter
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.ViewStateSetter


class SinglePodcastPresenterFactory(
        private val model: SinglePodcastModel,
        private val vievStateSetter: ViewStateSetter
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>?): T {
        return SinglePodcastPresenter(model, vievStateSetter) as T
    }

}
