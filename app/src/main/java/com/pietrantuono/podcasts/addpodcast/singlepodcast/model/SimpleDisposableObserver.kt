package com.pietrantuono.podcasts.addpodcast.singlepodcast.model

import io.reactivex.observers.DisposableObserver

open class SimpleDisposableObserver<T>: DisposableObserver<T>() {
    override fun onComplete() {

    }

    override fun onError(e: Throwable) {

    }

    override fun onNext(t: T) {

    }

}