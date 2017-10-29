package com.pietrantuono.podcasts.addpodcast.singlepodcast.model

import io.reactivex.Scheduler

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class PodcastSchedulers {

    open fun mainThread(): io.reactivex.Scheduler? {
        return AndroidSchedulers.mainThread()
    }

    open fun newThread(): Scheduler {
        return Schedulers.newThread()
    }
}
