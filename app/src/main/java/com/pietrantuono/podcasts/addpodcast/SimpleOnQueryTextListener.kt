package com.pietrantuono.podcasts.addpodcast

import android.support.v7.widget.SearchView


open class SimpleOnQueryTextListener : SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}