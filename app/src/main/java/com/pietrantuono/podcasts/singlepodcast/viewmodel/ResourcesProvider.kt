package com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat

import javax.inject.Inject

class ResourcesProvider @Inject
constructor(private val context: Context) {

    fun getDrawable(@DrawableRes drawableResource: Int): Drawable {
        return ContextCompat.getDrawable(context, drawableResource)
    }

    fun getString(@StringRes stringRes: Int): String {
        return context.getString(stringRes)
    }

    fun getColor(color: Int): Int {
        return ContextCompat.getColor(context, color)
    }


}
