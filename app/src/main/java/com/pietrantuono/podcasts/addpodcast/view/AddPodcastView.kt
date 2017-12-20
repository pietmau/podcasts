package com.pietrantuono.podcasts.addpodcast.view

import android.annotation.TargetApi
import android.os.Build
import android.widget.ImageView
import android.widget.LinearLayout

import models.pojos.Podcast

interface AddPodcastView {

    fun isProgressShowing(): Boolean

    fun onError(throwable: Throwable)

    fun updateSearchResults(items: List<Podcast>)

    fun showProgressBar(show: Boolean)

    fun startDetailActivityWithTransition(podcast: Podcast, imageView: ImageView, titleContainer: LinearLayout)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun startDetailActivityWithoutTransition(podcast: Podcast)

    fun isPartiallyHidden(imageView: Int): Boolean

    fun setEmpty(empty: Boolean)

}
