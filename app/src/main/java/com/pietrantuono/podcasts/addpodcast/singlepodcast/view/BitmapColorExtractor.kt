package com.pietrantuono.podcasts.addpodcast.singlepodcast.view


import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.graphics.Palette
import android.view.View
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener

class BitmapColorExtractor() : SimpleImageLoadingListener(), Parcelable {
    var callback: Callback? = null
    var backgroundColor: Int? = null
    var colorForBackgroundAndText: ColorForBackgroundAndText? = null

    constructor(parcel: Parcel) : this() {
        backgroundColor = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
        callback?.onColorExtractionCompleted()
    }

    override fun onLoadingCancelled(imageUri: String?, view: View?) {
        callback?.onColorExtractionCompleted()
    }

    override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
        if (loadedImage != null) {
            Palette.from(loadedImage).generate {
                backgroundColor = it?.vibrantSwatch?.rgb
                colorForBackgroundAndText = ColorForBackgroundAndText(it?.darkMutedSwatch?.rgb,
                        it?.darkMutedSwatch?.titleTextColor, it?.darkMutedSwatch?.bodyTextColor)
                callback?.onColorExtractionCompleted()
            }
        } else {
            callback?.onColorExtractionCompleted()
        }
    }

    interface Callback {
        fun onColorExtractionCompleted()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(backgroundColor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BitmapColorExtractor> {
        override fun createFromParcel(parcel: Parcel): BitmapColorExtractor {
            return BitmapColorExtractor(parcel)
        }

        override fun newArray(size: Int): Array<BitmapColorExtractor?> {
            return arrayOfNulls(size)
        }
    }


}

