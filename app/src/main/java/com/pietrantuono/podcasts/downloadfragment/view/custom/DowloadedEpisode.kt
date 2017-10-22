package com.pietrantuono.podcasts.downloadfragment.view.custom

import android.os.Parcel
import android.os.Parcelable
import com.pietrantuono.podcasts.apis.Episode

class DowloadedEpisode(private val episode: Episode) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.readParcelable<Episode>(Episode::class.java.classLoader) as Episode) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(episode, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DowloadedEpisode> {
        override fun createFromParcel(parcel: Parcel): DowloadedEpisode {
            return DowloadedEpisode(parcel)
        }

        override fun newArray(size: Int): Array<DowloadedEpisode?> {
            return arrayOfNulls(size)
        }
    }


}