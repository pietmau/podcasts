package com.pietrantuono.podcasts.subscribedpodcasts.list.menu

import android.view.Menu
import android.view.MenuItem
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.subscribedpodcasts.list.model.EpisodesListModel
import com.pietrantuono.podcasts.subscribedpodcasts.list.presenter.EpisodesListPresenter


class EpisodesListMenuProviderImpl(private val model: EpisodesListModel) :
        EpisodesListMenuProvider {
    private var callback: EpisodesListPresenter? = null

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.unsubscribe -> {
                callback?.onDownLoadAllSelected()
            }
            android.R.id.home -> {
                callback?.onBackPressed()
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        callback?.menuInflater?.inflate(R.menu.subscribed_menu, menu)
        return true
    }

    fun setCallback(presenter: EpisodesListPresenter?) {
        this@EpisodesListMenuProviderImpl.callback = presenter
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return true
    }

}

