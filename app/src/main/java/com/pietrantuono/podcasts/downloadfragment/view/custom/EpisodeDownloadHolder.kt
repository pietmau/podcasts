package com.pietrantuono.podcasts.downloadfragment.view.custom

import com.pietrantuono.podcasts.databinding.DownloadEpisodeItemBinding
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder

class EpisodeDownloadHolder(private val binding: DownloadEpisodeItemBinding) : ChildViewHolder(binding.root) {

    fun bind(group: ExpandableGroup<*>?, childIndex: Int) {
        binding.episode = (group as? DownloadedPodcast)?.items?.get(childIndex)
    }

}