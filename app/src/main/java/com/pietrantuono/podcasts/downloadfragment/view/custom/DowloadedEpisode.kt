package com.pietrantuono.podcasts.downloadfragment.view.custom

import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.apis.Episode

class DowloadedEpisode(
        episode: Episode,
        private val resources: ResourcesProvider?) : Parcelable {

    val title = episode.title
    private val downloaded = episode.downloaded

    val downloadedAsText: String?
        get() = if (downloaded) {
            " " + resources?.getString(R.string.downloaded)
        } else {
            " " + resources?.getString(R.string.not_downloaded)
        }

    val icon: Drawable?
        get() = if (downloaded) {
            resources?.getDrawable(R.drawable.ic_cloud_done_white_24dp)
        } else {
            resources?.getDrawable(R.drawable.ic_cloud_download_white_24dp)
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

    companion object CREATOR : Parcelable.Creator<DowloadedEpisode> {
        override fun createFromParcel(parcel: Parcel): DowloadedEpisode {
            throw UnsupportedOperationException("Not supported")
        }

        override fun newArray(size: Int): Array<DowloadedEpisode?> {
            throw UnsupportedOperationException("Not supported")
        }
    }


}