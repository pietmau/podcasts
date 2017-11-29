package com.pietrantuono.podcasts.utils

import android.os.Build
import android.support.v7.app.AppCompatActivity

const val STARTED_WITH_TRANSITION = "with_transition"
const val SINGLE_PODCAST_TRACK_ID: String = "track_id"
const val EPISODE_LINK: String = "episode_link"
const val EPISODE_TITLE: String = "episode_uri"
const val ARTWORK: String = "artwork"
const val BACKGROUND_COLOR: String = "background_color"
const val STARTED_FROM_NOTIFCATION: String = "from_notification"

fun AppCompatActivity.isInValidState(): Boolean {
    val destroyed = this.isDestroyed
    val finishing = this.isFinishing
    val changingConfigurations = this.isChangingConfigurations
    return !destroyed && !finishing && !changingConfigurations
}

val isLollipopOrHigher: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

val isMarshmallowOrHigher: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

val AppCompatActivity.isValidState: Boolean
    get() {
        val isNull = this == null
        val destroyed = this.isDestroyed
        val finishing = this.isFinishing
        val changingConfigurations = this.isChangingConfigurations
        return !isNull && !destroyed && !finishing && !changingConfigurations
    }

val TRANSITION_DURATION: Long = 200