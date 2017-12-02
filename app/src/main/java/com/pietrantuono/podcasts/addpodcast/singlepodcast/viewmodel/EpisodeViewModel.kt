package com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.text.Html
import android.view.View
import com.pietrantuono.podcasts.Constants
import com.pietrantuono.podcasts.R
import models.pojos.Episode
import java.text.SimpleDateFormat

@SuppressLint("ParcelCreator")
class EpisodeViewModel(episode: Episode, private val resourcesProvider: ResourcesProvider) : Episode by episode {

    val visibilityOfFooter: Int
        get() = if (visibilityOfType == View.VISIBLE || visibilityOfDuration == View.VISIBLE) View.VISIBLE else View.GONE

    val visibilityOfType: Int
        get() = if (mediaTypeText == null) View.GONE else View.VISIBLE

    val visibilityOfDuration: Int
        get() = if (duration == null || duration!!.isEmpty()) View.GONE else View.VISIBLE

    val mediaTypeImage: Drawable?
        get() = enclosures?.map { it.type }?.filterNotNull()?.map { it.toLowerCase() }?.map { getImageResouce(it) }?.firstOrNull()

    val dowloadedText: String
        get() = if (downloaded) resourcesProvider.getString(R.string.downloaded) else resourcesProvider.getString(R.string.not_downloaded)

    val playedText: String
        get() = if (played) resourcesProvider.getString(R.string.played) else resourcesProvider.getString(R.string.not_played)

    val visibilityOfAuthor: Int
        get() = if (author.isNullOrBlank()) View.GONE else View.VISIBLE

    val dowloadedDrawable: Drawable
        get() = if (downloaded) {
            resourcesProvider.getDrawable(R.drawable.ic_check_white_24dp)
        } else {
            resourcesProvider.getDrawable(R.drawable.ic_file_download_white_24dp)
        }


    private fun getImageResouce(type: String): Drawable? =
            when {
                type.contains(Constants.AUDIO, true) -> resourcesProvider.getDrawable(R.drawable.ic_audio_icon)
                type.contains(Constants.VIDEO, true) -> resourcesProvider.getDrawable(R.drawable.ic_video_icon)
                else -> null
            }

    val mediaTypeText: String?
        get() = enclosures?.map { it.type }?.filterNotNull()?.map { it.toLowerCase() }?.map { getStringResource(it) }?.firstOrNull()

    private fun getStringResource(type: String): String? =
            when {
                type.contains(Constants.AUDIO, true) -> resourcesProvider.getString(R.string.audio)
                type.contains(Constants.VIDEO, true) -> resourcesProvider.getString(R.string.video)
                else -> null
            }

    fun getDate(): String? = pubDate?.let { SimpleDateFormat("MMM d yyyy").format(pubDate); } ?: null

    fun getSummaryNotHtml(): String? = fromHtml(summary)

    fun getDescriptionNotHtml(): String? = fromHtml(description)

    private fun fromHtml(text: String?): String? = text?.let {
        Html.fromHtml(text).toString().replace('\n', 32.toChar())
                .replace(160.toChar(), 32.toChar()).replace(65532.toChar(), 32.toChar()).trim();
    } ?: null

}
