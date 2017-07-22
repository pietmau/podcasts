package com.pietrantuono.podcasts.subscribedpodcasts.detail.model

import android.arch.lifecycle.ViewModel
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.apis.PodcastFeed
import rx.Observer

class SingleSubscribedModelViewModel : SingleSubscribedModel, ViewModel() {
    override fun unsubscribe() {

    }

    override fun subscribeToFeed(observer: Observer<PodcastFeed>) {

    }

    override fun subscribeToIsSubscribedToPodcast(observer: SimpleObserver<Boolean>) {

    }

    override fun startModel(podcast: SinglePodcast?) {

    }

    override fun onSubscribeUnsubscribeToPodcastClicked() {

    }
}