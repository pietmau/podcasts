package com.pietrantuono.podcasts.addpodcast.singlepodcast.model

import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

open class PodcastSchedulers {

    open fun mainThread(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    open fun newThread(): Scheduler {
        return Schedulers.newThread()
    }
}
