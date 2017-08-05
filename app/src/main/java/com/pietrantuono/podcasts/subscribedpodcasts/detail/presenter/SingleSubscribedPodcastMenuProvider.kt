package com.pietrantuono.podcasts.subscribedpodcasts.detail.presenter

import android.view.Menu

interface SingleSubscribedPodcastMenuProvider {
    fun onCreateOptionsMenu(menu: Menu): Boolean
    fun onPrepareOptionsMenu(menu: Menu): Boolean
}