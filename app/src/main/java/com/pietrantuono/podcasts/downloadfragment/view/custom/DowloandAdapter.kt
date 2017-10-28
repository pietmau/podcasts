package com.pietrantuono.podcasts.downloadfragment.view.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import com.pietrantuono.podcasts.databinding.DownloadEpisodeItemBinding
import com.pietrantuono.podcasts.databinding.DownloadGroupItemBinding
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class DowloandAdapter(data: MutableList<DownloadedPodcast>) : ExpandableRecyclerViewAdapter<PodcastDowloadHolder, EpisodeDownloadHolder>(data) {

    override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): EpisodeDownloadHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = DownloadEpisodeItemBinding.inflate(inflater, parent, false);
        return EpisodeDownloadHolder(binding)
    }

    override fun onBindGroupViewHolder(holder: PodcastDowloadHolder?, flatPosition: Int, group: ExpandableGroup<*>?) {
        holder?.bind(group)
    }

    override fun onBindChildViewHolder(holder: EpisodeDownloadHolder?, flatPosition: Int, group: ExpandableGroup<*>?, childIndex: Int) {
        holder?.bind(group, childIndex)
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): PodcastDowloadHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = DownloadGroupItemBinding.inflate(inflater, parent, false);
        return PodcastDowloadHolder(binding)
    }

}

