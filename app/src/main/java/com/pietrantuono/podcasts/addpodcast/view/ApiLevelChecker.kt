package com.pietrantuono.podcasts.addpodcast.view

interface ApiLevelChecker {

    val isLollipopOrHigher: Boolean

    val isMarshmallowOrHigher: Boolean
}
