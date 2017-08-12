package com.pietrantuono.podcasts.subscribedpodcasts.detail.menu

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.subscribedpodcasts.detail.model.SingleSubscribedModel
import com.pietrantuono.podcasts.subscribedpodcasts.detail.presenter.SingleSubscribedPodcastPresenter


class SingleSubscribedPodcastMenuProviderImpl(private val model: SingleSubscribedModel,
                                              private val menuInflater: MenuInflater) :
        SingleSubscribedPodcastMenuProvider {
    private var callback: SingleSubscribedPodcastPresenter? = null

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dowload_all -> {
                callback?.onDownLoadAllSelected()
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.subscribed_menu, menu)
        return true
    }

    fun setCallback(presenter: SingleSubscribedPodcastPresenter?) {
        this@SingleSubscribedPodcastMenuProviderImpl.callback = presenter
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return true
    }

}

