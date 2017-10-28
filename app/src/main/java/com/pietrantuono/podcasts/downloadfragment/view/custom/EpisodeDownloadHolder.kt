package com.pietrantuono.podcasts.downloadfragment.view.custom

import android.support.v7.widget.PopupMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.databinding.DownloadEpisodeItemBinding
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder

class EpisodeDownloadHolder(private val binding: DownloadEpisodeItemBinding) : ChildViewHolder(binding.root) {
    private var callback: DownloadAdapter.Callback? = null
    private val downloaded
        get() = binding.episode?.downloaded

    fun bind(group: ExpandableGroup<*>?, childIndex: Int, callback: DownloadAdapter.Callback?) {
        binding.episode = (group as? DownloadedPodcast)?.items?.get(childIndex)
        this.callback = callback
        binding.overflow.setOnClickListener { view -> showPopUp(view) }
        binding.downloaded.setOnClickListener { onTextClicked() }
    }

    private fun onTextClicked() {
        if (downloaded == true) {
            deleteEpisode()
        } else {
            downloadEpisode()
        }
    }

    private fun showPopUp(view: View?) {
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
        if (downloaded == true) {
            menu.removeItem(R.id.download)
            return
        }
        menu.removeItem(R.id.delete)
    }

    private fun onMenuItemClicked(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.download -> {
                downloadEpisode()
                return true
            }
            R.id.delete -> {
                deleteEpisode()
                return true
            }
            else -> {
                return false
            }
        }
    }

    private fun deleteEpisode() {
        callback?.deleteEpisode(binding.episode)
    }

    private fun downloadEpisode() {
        callback?.downloadEpisode(binding.episode)
    }

}