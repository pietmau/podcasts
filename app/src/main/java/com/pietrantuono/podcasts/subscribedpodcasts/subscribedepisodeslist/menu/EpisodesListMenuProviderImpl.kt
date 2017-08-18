package com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.menu

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.model.EpisodesListModel
import com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.presenter.EpisodesListPresenter


class EpisodesListMenuProviderImpl(private val model: EpisodesListModel,
                                   private val menuInflater: MenuInflater) :
        EpisodesListMenuProvider {
    private var callback: EpisodesListPresenter? = null

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

    fun setCallback(presenter: EpisodesListPresenter?) {
        this@EpisodesListMenuProviderImpl.callback = presenter
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return true
    }

}

