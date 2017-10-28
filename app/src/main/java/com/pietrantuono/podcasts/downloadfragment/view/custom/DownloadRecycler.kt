package com.pietrantuono.podcasts.downloadfragment.view.custom

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

class DownloadRecycler(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {

    fun setData(data: MutableList<DownloadedPodcast>) {
        layoutManager = LinearLayoutManager(context)
        val decoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        adapter = DowloandAdapter(data)
    }
}

