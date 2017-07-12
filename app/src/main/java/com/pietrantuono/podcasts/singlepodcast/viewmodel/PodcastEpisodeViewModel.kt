package com.pietrantuono.podcasts.singlepodcast.viewmodel

import android.graphics.drawable.Drawable

import com.pietrantuono.podcasts.Constants
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel

class PodcastEpisodeViewModel(podcastEpisodeModel: PodcastEpisodeModel, private val resourcesProvider:
ResourcesProvider) : BasePodcastEpisodeViewModel(podcastEpisodeModel) {

    val visibilityOfFooter: VisibilityWrapper
        get() =
        if (visibilityOfType.isVisible || visibilityOfDuration.isVisible) {
            VisibilityWrapper.VISIBLE
        } else {
            VisibilityWrapper.GONE
        }

    val visibilityOfType: VisibilityWrapper
        get() =
        if (mediaTypeText == null) {
            VisibilityWrapper.GONE
        } else {
            VisibilityWrapper.VISIBLE
        }

    val visibilityOfDuration: VisibilityWrapper
        get() =
        if (duration == null || duration!!.isEmpty()) {
            VisibilityWrapper.GONE
        } else {
            VisibilityWrapper.VISIBLE
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

}
