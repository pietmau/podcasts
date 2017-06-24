package com.pietrantuono.podcasts.player.player

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class PodcastFeedSourceImpl(override val uris: List<Uri>) : PodcastFeedSource, Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<PodcastFeedSourceImpl> = object : Parcelable.Creator<PodcastFeedSourceImpl> {
            override fun createFromParcel(source: Parcel): PodcastFeedSourceImpl = PodcastFeedSourceImpl(source)
            override fun newArray(size: Int): Array<PodcastFeedSourceImpl?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.createTypedArrayList(Uri.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeTypedList(uris)
    }
}