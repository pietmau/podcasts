package com.pietrantuono.podcasts.playlist.view.custom

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet


class MediaItemRecycler(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    init {
        adapter = MediaItemAdapter(context)
    }

}
