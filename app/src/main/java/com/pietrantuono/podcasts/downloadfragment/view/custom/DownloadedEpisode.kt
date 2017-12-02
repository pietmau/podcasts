package com.pietrantuono.podcasts.downloadfragment.view.custom

import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import models.pojos.Episode
import java.text.NumberFormat


class DownloadedEpisode(
        val episode: Episode,
        private val resources: ResourcesProvider?) : Parcelable {

    val title = episode.title
    val link = episode.link
    val downloaded = episode.downloaded
    val fileSizeInKb = NumberFormat.getInstance().format(episode.fileSizeInBytes /(1024));

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

    constructor(parcel: Parcel) : this(parcel.readParcelable<Episode>(Episode::class.java.classLoader) as Episode, null) {
        throw UnsupportedOperationException("Not supported")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        throw UnsupportedOperationException("Not supported")
    }

    override fun describeContents(): Int {
        throw UnsupportedOperationException("Not supported")
    }

    companion object CREATOR : Parcelable.Creator<DownloadedEpisode> {
        override fun createFromParcel(parcel: Parcel): DownloadedEpisode {
            throw UnsupportedOperationException("Not supported")
        }

        override fun newArray(size: Int): Array<DownloadedEpisode?> {
            throw UnsupportedOperationException("Not supported")
        }
    }

}