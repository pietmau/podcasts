package com.pietrantuono.podcasts.addpodcast.singlepodcast.view


import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.graphics.Palette
import android.view.View
import android.widget.ImageView
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
        val bitmap = ((view as? ImageView)?.drawable as? BitmapDrawable)?.bitmap
        if (bitmap != null) {
            Palette.from(bitmap).generate {
                extractColors(it)
                callback?.onColorExtractionCompleted()
            }
        } else {
            callback?.onColorExtractionCompleted()
        }
    }

    private fun extractColors(palette: Palette) {
        backgroundColor = getVibrantSwatch(palette)?.rgb
        colorForBackgroundAndText = extractTextColors(palette)
    }

    private fun extractTextColors(palette: Palette): ColorForBackgroundAndText {
        val swatch = getMutedSwatch(palette)
        return ColorForBackgroundAndText(swatch?.rgb,
                swatch?.titleTextColor, swatch?.bodyTextColor)
    }

    private fun getMutedSwatch(palette: Palette): Palette.Swatch? {
        return (palette?.darkMutedSwatch) ?: (palette?.mutedSwatch ?: palette?.lightMutedSwatch)
    }

    private fun getVibrantSwatch(palette: Palette): Palette.Swatch? {
        return (palette?.vibrantSwatch) ?: (palette?.darkVibrantSwatch ?: palette?.lightMutedSwatch)
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

