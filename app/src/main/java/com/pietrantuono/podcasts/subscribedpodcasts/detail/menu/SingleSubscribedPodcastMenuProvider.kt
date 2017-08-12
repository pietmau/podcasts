package com.pietrantuono.podcasts.subscribedpodcasts.detail.menu

import android.view.Menu
import android.view.MenuItem

interface SingleSubscribedPodcastMenuProvider {
    fun onCreateOptionsMenu(menu: Menu): Boolean
    fun onPrepareOptionsMenu(menu: Menu): Boolean
    fun onOptionsItemSelected(item: MenuItem):Boolean
}