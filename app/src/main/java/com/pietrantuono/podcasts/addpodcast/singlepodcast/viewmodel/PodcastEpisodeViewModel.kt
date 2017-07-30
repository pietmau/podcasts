package com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel

import android.graphics.drawable.Drawable
import android.view.View
import com.pietrantuono.podcasts.Constants
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel
import java.text.SimpleDateFormat

class PodcastEpisodeViewModel(podcastEpisodeModel: PodcastEpisodeModel, private val resourcesProvider:
ResourcesProvider) : PodcastEpisodeModel by podcastEpisodeModel {

    val visibilityOfFooter: Int
        get() =
        if (visibilityOfType == View.VISIBLE || visibilityOfDuration == View.VISIBLE) {
            View.VISIBLE
        } else {
            View.GONE
        }

    val visibilityOfType: Int
        get() =
        if (mediaTypeText == null) {
            View.GONE
        } else {
            View.VISIBLE
        }

    val visibilityOfDuration: Int
        get() =
        if (duration == null || duration!!.isEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }

    val mediaTypeImage: Drawable?
        get() =
        try {
            val type = enclosures!![0].type.toLowerCase()
            getImageResouce(type)
        } catch (e: Exception) {
            null
        }

    private fun getImageResouce(type: String): Drawable? {
        if (type.contains(Constants.AUDIO)) {
            return resourcesProvider.ContextCompatgetDrawable(R.drawable.ic_audio_icon)
        }
        if (type.contains(Constants.VIDEO)) {
            resourcesProvider.ContextCompatgetDrawable(R.drawable.ic_video_icon)
        }
        return null
    }

    val mediaTypeText: String?
        get() =
        try {
            val type = enclosures!![0].type.toLowerCase()
            getStringResource(type)
        } catch (e: Exception) {
            null
        }


    private fun getStringResource(type: String): String? {
        if (type.contains(Constants.AUDIO)) {
            return resourcesProvider.getString(R.string.audio)
        }
        if (type.contains(Constants.VIDEO)) {
            return resourcesProvider.getString(R.string.video)
        }
        return null
    }

    fun getDate(): String? {
        val simpleDateFormat = SimpleDateFormat("MMM d yyyy");
        if (getPubDate() != null) {
            return simpleDateFormat.format(getPubDate());
        }
        return null;
    }
}
