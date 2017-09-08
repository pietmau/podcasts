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
        get() = if (visibilityOfType == View.VISIBLE || visibilityOfDuration == View.VISIBLE) View.VISIBLE else View.GONE

    val downloadIconShouldbeVisible: Int
        get() = if (downloaded) View.VISIBLE else View.GONE

    val visibilityOfType: Int
        get() = if (mediaTypeText == null) View.GONE else View.VISIBLE

    val visibilityOfDuration: Int
        get() = if (duration == null || duration!!.isEmpty()) View.GONE else View.VISIBLE

    val mediaTypeImage: Drawable?
        get() = enclosures?.map { it.type }?.filterNotNull()?.map { getImageResouce(it) }?.firstOrNull()


    val dowloadedText: String
        get() = if (downloaded) resourcesProvider.getString(R.string.downloaded) else resourcesProvider.getString(R.string.not_downloaded)


    private fun getImageResouce(type: String): Drawable? =
            when {
                type.contains(Constants.AUDIO) -> resourcesProvider.ContextCompatgetDrawable(R.drawable.ic_audio_icon)
                type.contains(Constants.VIDEO) -> resourcesProvider.ContextCompatgetDrawable(R.drawable.ic_video_icon)
                else -> null
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
        if (pubDate != null) {
            val simpleDateFormat = SimpleDateFormat("MMM d yyyy");
            return simpleDateFormat.format(pubDate);
        }
        return null;
    }

    fun getSummaryNotHtml(): String? = fromHtml(summary)

    fun getDescriptionNotHtml(): String? = fromHtml(description)

    private fun fromHtml(text: String?): String? {
        if (text == null) {
            return null
        }
        return Html.fromHtml(text).toString().replace('\n', 32.toChar())
                .replace(160.toChar(), 32.toChar()).replace(65532.toChar(), 32.toChar()).trim();
    }
}
