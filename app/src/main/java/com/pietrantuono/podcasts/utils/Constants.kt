package com.pietrantuono.podcasts.utils

import android.support.v7.app.AppCompatActivity

const val STARTED_WITH_TRANSITION = "with_transition"
const val SINGLE_PODCAST_TRACK_ID: String = "track_id"
const val EPISODE_LINK: String = "episode_link"
const val ARTWORK: String = "artwork"
const val BACKGROUND_COLOR: String = "background_color"

fun AppCompatActivity.isInValidState(): Boolean {
    val destroyed = this.isDestroyed
    val finishing = this.isFinishing
    val changingConfigurations = this.isChangingConfigurations
    return !destroyed && !finishing && !changingConfigurations
}