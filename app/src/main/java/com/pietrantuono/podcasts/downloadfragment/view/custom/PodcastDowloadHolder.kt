package com.pietrantuono.podcasts.downloadfragment.view.custom

import android.support.v7.widget.PopupMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.databinding.DownloadGroupItemBinding
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder


class PodcastDowloadHolder(private val binding: DownloadGroupItemBinding) : GroupViewHolder(binding.root) {
    private var callback: DownloadAdapter.Callback? = null

    fun bind(group: ExpandableGroup<*>?, callback: DownloadAdapter.Callback?) {
        binding.podcast = group as? DownloadedPodcast
        this.callback = callback
        binding.downloaded.setOnClickListener {
            deleteEpisodes()
        }
        binding.notDownloaded.setOnClickListener {
            downloadEpisodes()
        }
        binding.overflow.setOnClickListener { view -> showPopup(view) }
    }

    private fun downloadEpisodes() {
        callback?.downloadAllEpisodes(binding.podcast)
    }

    private fun deleteEpisodes() {
        callback?.deleteAllEpisodes(binding.podcast)
    }

    private fun showPopup(view: View?) {
        view?.let {
            PopupMenu(it.context, it).apply {
                setOnMenuItemClickListener { menuItem -> onMenuItemClicked(menuItem) };
                getMenuInflater().inflate(R.menu.downloaded_podcast_popup, menu)
                removeUnusedItem(menu)
                show()
            }
        }
    }

    private fun removeUnusedItem(menu: Menu) {
        if (noEpisodes()) {
            menu.removeItem(R.id.download)
            menu.removeItem(R.id.delete)
            return
        }

        if (allDownloaded()) {
            menu.removeItem(R.id.download)
            return
        }
        if (allNotDownloaded()) {
            menu.removeItem(R.id.delete)
            return
        }
    }

    private fun noEpisodes(): Boolean {
        if (binding.podcast == null) {
            return true
        }
        if (binding.podcast?.total == null) {
            return true
        }
        if (binding.podcast?.total == 0) {
            return true
        }
        return false
    }

    private fun allDownloaded(): Boolean {
        if (noEpisodes()) {
            return true
        }
        //TODO This is ugly
        return binding.podcast!!.total!! <= binding.podcast!!.downloadedCount
    }


    private fun allNotDownloaded(): Boolean {
        if (noEpisodes()) {
            return true
        }
        //TODO This is ugly
        return binding.podcast!!.total!! <= binding.podcast!!.notDownloadedCount
    }

    private fun onMenuItemClicked(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.download -> {
                downloadEpisodes()
                return true
            }
            R.id.delete -> {
                deleteEpisodes()
                return true
            }
            else -> {
                return false
            }
        }
    }
}