package com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.text.Html
import android.view.View
import com.pietrantuono.podcasts.Constants
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.apis.Episode
import java.text.SimpleDateFormat

@SuppressLint("ParcelCreator")
class EpisodeViewModel(episode: Episode, private val resourcesProvider: ResourcesProvider) : Episode by episode {

    val visibilityOfFooter: Int
        get() =
        if (visibilityOfType == View.VISIBLE || visibilityOfDuration == View.VISIBLE) {
            View.VISIBLE
        } else {
            View.GONE
        }

    val downloadIconShouldbeVisible: Int
        get() = if (downloaded) {
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

    val dowloadedText: String
        get() = if (downloaded) resourcesProvider.getString(R.string.downloaded) else ""


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
        if (pubDate != null) {
            return simpleDateFormat.format(pubDate);
        }
        return null;
    }

    fun getSummaryNotHtml(): String? {
        if (summary != null) {
            return fromHtml(summary!!)
        }
        return null
    }

    fun getDescriptionNotHtml(): String? {
        if (description != null) {
            return fromHtml(description!!)
        }
        return null
    }

    private fun fromHtml(text: String) = Html.fromHtml(text).toString().replace('\n', 32.toChar())
            .replace(160.toChar(), 32.toChar()).replace(65532.toChar(), 32.toChar()).trim();
}
