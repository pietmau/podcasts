package com.pietrantuono.podcasts.downloadfragment.view.custom

import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import models.pojos.Episode
import java.text.NumberFormat

class DownloadedEpisode(
        val title: String?,
        val link: String?,
        val downloaded: Boolean,
        val fileSizeInKb: String?,
        val uri: String?,
        val resources: ResourcesProvider?,
        val downloadRequestId: Long) : Parcelable {

    val downloadedAsText: String?
        get() = if (downloaded) {
            " " + resources?.getString(R.string.downloaded)
        } else {
            " " + resources?.getString(R.string.not_downloaded)
        }

    val icon: Drawable?
        get() = if (downloaded) {
            resources?.getDrawable(R.drawable.ic_check_white_24dp)
        } else {
            resources?.getDrawable(R.drawable.ic_file_download_white_24dp)
        }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            TODO("resources"), 0) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        throw UnsupportedOperationException()
    }

    override fun describeContents(): Int {
        throw UnsupportedOperationException()
    }

    companion object CREATOR : Parcelable.Creator<DownloadedEpisode> {
        override fun createFromParcel(parcel: Parcel): DownloadedEpisode {
            return DownloadedEpisode(parcel)
        }

        override fun newArray(size: Int): Array<DownloadedEpisode?> {
            return arrayOfNulls(size)
        }

        fun fromEpisode(episode: Episode, resources: ResourcesProvider?): DownloadedEpisode {
            val title = episode.title
            val link = episode.link
            val dowloanded = episode.downloaded
            val sizeinkb = NumberFormat.getInstance().format(episode.fileSizeInBytes / (1024));
            val uri = episode.uri
            val downloadRequestId = episode.downloadRequestId
            return DownloadedEpisode(title, link, dowloanded, sizeinkb, uri, resources, downloadRequestId)
        }
    }

}