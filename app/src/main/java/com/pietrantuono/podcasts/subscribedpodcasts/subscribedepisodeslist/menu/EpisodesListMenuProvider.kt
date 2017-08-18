package com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.menu

import android.view.Menu
import android.view.MenuItem

interface EpisodesListMenuProvider {
    fun onCreateOptionsMenu(menu: Menu): Boolean
    fun onPrepareOptionsMenu(menu: Menu): Boolean
    fun onOptionsItemSelected(item: MenuItem):Boolean
}