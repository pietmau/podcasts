package com.pietrantuono.podcasts.downloadfragment.view.custom

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.downloadfragment.di.DownloadFragmentModule
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import javax.inject.Inject

class DownloadRecycler(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {
    @Inject lateinit var imageLoader: SimpleImageLoader

    init {
        ((context as? Activity)?.applicationContext as App).applicationComponent?.with(DownloadFragmentModule())?.inject(this)
    }

    var callback: DownloadAdapter.Callback? = null
        set(value) {
            field = value
            (adapter as? DownloadAdapter)?.callback = value
        }

    fun setData(data: MutableList<DownloadedPodcast>) {
        layoutManager = LinearLayoutManager(context)
        adapter = DownloadAdapter(data, callback, imageLoader)
    }


}

