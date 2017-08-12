package com.pietrantuono.podcasts.addpodcast.view

import android.os.Build

class ApiLevelCheckerImpl : ApiLevelChecker {

    override val isLollipopOrHigher: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    override val isMarshmallowOrHigher: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}
