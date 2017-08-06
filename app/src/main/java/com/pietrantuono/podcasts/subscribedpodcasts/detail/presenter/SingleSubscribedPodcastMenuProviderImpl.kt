package com.pietrantuono.podcasts.subscribedpodcasts.detail.presenter

import android.view.Menu
import android.view.MenuInflater
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.subscribedpodcasts.detail.model.SingleSubscribedModel


class SingleSubscribedPodcastMenuProviderImpl(private val model: SingleSubscribedModel,
                                              private val menuInflater: MenuInflater) :
        SingleSubscribedPodcastMenuProvider {
    private var callback: SingleSubscribedPodcastPresenter? = null

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.other_actions, menu)
        return true
    }

    fun setCallback(presenter: SingleSubscribedPodcastPresenter) {
        this@SingleSubscribedPodcastMenuProviderImpl.callback = presenter
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return true
    }
}

