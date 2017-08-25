package com.pietrantuono.podcasts.main.view

import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.support.v7.widget.Toolbar


import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker

class TransitionsFramework(private val apiLevelChecker: ApiLevelChecker) {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) fun initMainActivityTransitions(activity: AppCompatActivity) {
        if (!apiLevelChecker.isLollipopOrHigher) {
            return
        }
        val window = activity.window
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) fun initDetailTransitions(activity: AppCompatActivity) {
        if (!apiLevelChecker.isLollipopOrHigher) {
            return
        }
        val window = activity.window
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        activity.postponeEnterTransition()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) fun startPostponedEnterTransition(activity: AppCompatActivity) {
        if (!apiLevelChecker.isLollipopOrHigher) {
            return
        }
        activity.startPostponedEnterTransition()
    }

    fun getPairs(imageView: ImageView, activity: Activity, titleContainer: LinearLayout): Array<Pair<View, String>?> {
        if (!apiLevelChecker.isLollipopOrHigher) {
            return emptyArray()
        }
        var pairs: Array<Pair<View, String>?> = getNavigationBarAndImage(imageView, activity)
        pairs = getLinearLayout(activity, titleContainer, pairs)
        return pairs
    }

    fun getNavigationBarAndImage(imageView: ImageView, activity: Activity): Array<Pair<View, String>?> {
        val pairs = getNavigationBar(activity)
        pairs[0] = Pair(imageView, activity.getString(R.string.detail_transition_image))
        return pairs
    }

    fun getLinearLayout(activity: Activity, titleContainer: LinearLayout, pairs: Array<Pair<View, String>?>): Array<Pair<View, String>?> {
        pairs[1] = Pair(titleContainer, activity.getString(R.string.detail_transition_toolbar))
        return pairs
    }

    fun getNavigationBar(activity: Activity): Array<Pair<View, String>?> {
        val decor = activity.window.decorView
        val navBar = decor.findViewById(android.R.id.navigationBarBackground)
        val pairs: Array<Pair<View, String>?>
        if (navBar != null) {
            pairs = arrayOfNulls<Pair<View, String>>(3)
            pairs[2] = Pair(navBar, "transition")
        } else {
            pairs = arrayOfNulls<Pair<View, String>>(2)
        }
        return pairs
    }

    fun getPairs(imageView: ImageView, activity: Activity, toolbar: Toolbar): Array<Pair<View, String>?> {
        if (!apiLevelChecker.isLollipopOrHigher) {
            return emptyArray()
        }
        var pairs: Array<Pair<View, String>?> = getNavigationBarAndImage(imageView, activity)
        pairs = getCardView(activity, toolbar, pairs)
        return pairs
    }

    fun getCardView(activity: Activity, toolbar: Toolbar, pairs: Array<Pair<View, String>?>): Array<Pair<View, String>?> {
        pairs[1] = Pair(toolbar, activity.getString(R.string.detail_transition_toolbar))
        return pairs
    }
}
