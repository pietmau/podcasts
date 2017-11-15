package com.pietrantuono.podcasts.main.presenter

import rx.Subscription
import rx.subscriptions.CompositeSubscription

open class SubscriptionsManager {
    private val compositeSubscription = CompositeSubscription()

    open fun add(subscription: Subscription?) {
        compositeSubscription.add(subscription)
    }

    open fun unsubscribe() {
        compositeSubscription.unsubscribe()

    }

}