package com.pietrantuono.podcasts.downloadfragment.view.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import com.pietrantuono.podcasts.R
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class DowloandAdapter(data: MutableList<DownloadedPodcast>) : ExpandableRecyclerViewAdapter<PodcastDowloadHolder, EpisodeDownloadHolder>(data) {

    override fun onCreateChildViewHolder(parent: ViewGroup?, viewType: Int): EpisodeDownloadHolder {
        return EpisodeDownloadHolder(LayoutInflater.from(parent?.context)?.inflate(R.layout.download_group_item, parent, false))
    }

    override fun onBindGroupViewHolder(holder: PodcastDowloadHolder?, flatPosition: Int, group: ExpandableGroup<*>?) {

    }

    override fun onBindChildViewHolder(holder: EpisodeDownloadHolder?, flatPosition: Int, group: ExpandableGroup<*>?, childIndex: Int) {

    }

    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): PodcastDowloadHolder {
        return PodcastDowloadHolder(LayoutInflater.from(parent?.context)?.inflate(R.layout.download_group_item, parent, false))
    }

}

