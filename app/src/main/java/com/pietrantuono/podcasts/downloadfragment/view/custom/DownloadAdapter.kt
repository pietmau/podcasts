package com.pietrantuono.podcasts.downloadfragment.view.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import com.pietrantuono.podcasts.databinding.DownloadEpisodeItemBinding
import com.pietrantuono.podcasts.databinding.DownloadGroupItemBinding
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class DownloadAdapter(
        data: MutableList<DownloadedPodcast>,
        var callback: Callback?) : ExpandableRecyclerViewAdapter<PodcastDowloadHolder, EpisodeDownloadHolder>(data) {

    override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): EpisodeDownloadHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = DownloadEpisodeItemBinding.inflate(inflater, parent, false);
        return EpisodeDownloadHolder(binding)
    }

    override fun onBindGroupViewHolder(holder: PodcastDowloadHolder?, flatPosition: Int, group: ExpandableGroup<*>?) {
        holder?.bind(group, callback)
    }

    override fun onBindChildViewHolder(holder: EpisodeDownloadHolder?, flatPosition: Int, group: ExpandableGroup<*>?, childIndex: Int) {
        holder?.bind(group, childIndex, callback)
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): PodcastDowloadHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val binding = DownloadGroupItemBinding.inflate(inflater, parent, false);
        return PodcastDowloadHolder(binding)
    }

    interface Callback {
        fun downloadEpisode(link: DownloadedEpisode?)

        fun downloadEpisodes(trackId: DownloadedPodcast?)

        fun deleteEpisode(link: DownloadedEpisode?)

        fun deleteEpisodes(trackId: DownloadedPodcast?)
    }
}

