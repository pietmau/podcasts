package com.pietrantuono.podcasts.addpodcast.dagger


import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel
import com.pietrantuono.podcasts.addpodcast.presenter.AddPodcastPresenter
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker

internal class AddPodcastPresenterFactory(private val addPodcastsModel: AddPodcastsModel,
                                          private val apiLevelChecker: ApiLevelChecker): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddPodcastPresenter(addPodcastsModel,apiLevelChecker) as T
    }

}
