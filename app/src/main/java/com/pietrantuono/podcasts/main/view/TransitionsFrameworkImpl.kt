package com.pietrantuono.podcasts.main.view


import android.annotation.TargetApi
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build

import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.transition.Transition
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker

class Transitions(private val apiLevelChecker: ApiLevelChecker) {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) fun initMainActivityTransitions(activity: AppCompatActivity) {
        if (!apiLevelChecker.isLollipopOrHigher) {
            return
        }
        activity.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) fun initDetailTransitions(
            activity: AppCompatActivity, target: View?, listener: Transition.TransitionListener?){
        if (!apiLevelChecker.isLollipopOrHigher) {
            return
        }
        val window = activity.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val transition = window?.sharedElementEnterTransition
        transition?.let {
            it.addTarget(target)
            it.addListener(listener)
        }
        activity.postponeEnterTransition()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) fun initDetailTransitions(activity: AppCompatActivity) {
        if (!apiLevelChecker.isLollipopOrHigher) {
            return
        }
        activity.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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
        val navBar = activity.window?.decorView?.findViewById(android.R.id.navigationBarBackground)
        val pairs: Array<Pair<View, String>?>
        if (navBar != null) {
            pairs = arrayOfNulls<Pair<View, String>>(3)
            pairs[2] = Pair(navBar, "transition")
        } else {
            pairs = arrayOfNulls<Pair<View, String>>(2)
        }
        return pairs
    }
}
