package com.pietrantuono.podcasts.downloadfragment.view.custom

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

class DownloadRecycler(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {

    var callback: DownloadAdapter.Callback? = null
        set(value) {
            field = value
            (adapter as? DownloadAdapter)?.callback = value
        }

    fun setData(data: MutableList<DownloadedPodcast>) {
        layoutManager = LinearLayoutManager(context)
        adapter = DownloadAdapter(data, callback)
    }

    fun updateItem(i: Int, j: Int, episode: DownloadedEpisode) {
        (adapter as DownloadAdapter).updateItem(i,j,episode)
    }


}

