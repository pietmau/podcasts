package com.pietrantuono.podcasts.episode

import android.view.Menu
import android.view.MenuItem
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.EpisodeActivity

class EpisodePresenter {
    fun onStart(episodeActivity: EpisodeActivity, intExtra: Int, booleanExtra: Boolean) {

    }

    fun onStop() {
        
        
    }

    fun onCreateOptionsMenu(menu: Menu): Boolean {
        return true
    }

    fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return true
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }

}