package com.pietrantuono.podcasts.downloadfragment.view.custom

import com.pietrantuono.podcasts.databinding.DownloadGroupItemBinding
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder

class PodcastDowloadHolder(private val binding: DownloadGroupItemBinding) : GroupViewHolder(binding.root) {

    fun bind(group: ExpandableGroup<*>?) {
        binding.podcast = group as? DownloadedPodcast
    }
}