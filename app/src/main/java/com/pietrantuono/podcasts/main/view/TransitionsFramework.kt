package com.pietrantuono.podcasts.main.view

import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import android.support.v4.util.Pair
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.support.v7.widget.Toolbar

interface TransitionsFramework {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun initMainActivityTransitions(activity: AppCompatActivity)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun initDetailTransitions(activity: AppCompatActivity)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun startPostponedEnterTransition(activity: AppCompatActivity)

    fun getPairs(imageView: ImageView, activity: Activity, titleContainer: LinearLayout): Array<Pair<View, String>>

    fun getPairs(imageView: ImageView, activity: Activity, toolbar: Toolbar): Array<Pair<View, String>>
}
