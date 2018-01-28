package com.pietrantuono.podcasts.addpodcast.view

import android.annotation.TargetApi
import android.os.Build
import android.widget.ImageView
import android.widget.LinearLayout
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.State

import models.pojos.Podcast

interface AddPodcastView {

    fun updateSearchResults(items: List<Podcast>)

    fun startDetailActivityWithTransition(podcast: Podcast, imageView: ImageView, titleContainer: LinearLayout)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun startDetailActivityWithoutTransition(podcast: Podcast)

    fun isPartiallyHidden(imageView: Int): Boolean


    fun setState(state: State)
}
