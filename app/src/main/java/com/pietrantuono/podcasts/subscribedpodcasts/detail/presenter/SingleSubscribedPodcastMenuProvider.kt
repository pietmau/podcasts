package com.pietrantuono.podcasts.subscribedpodcasts.detail.presenter

import android.view.Menu
import android.view.MenuInflater
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.subscribedpodcasts.detail.model.SingleSubscribedModel


class SingleSubscribedPodcastMenuProvider(private val model: SingleSubscribedModel,
                                          private val menuInflater: MenuInflater) {

    fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.other_actions, menu)
        return true
    }
}