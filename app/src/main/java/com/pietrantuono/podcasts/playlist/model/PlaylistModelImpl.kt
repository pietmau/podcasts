package com.pietrantuono.podcasts.playlist.model

import android.support.v4.media.MediaBrowserCompat
import models.pojos.Episode
import models.pojos.RealmEpisode
import repo.repository.EpisodesRepository
import rx.Observable
import rx.Observer
import rx.subscriptions.CompositeSubscription


class PlaylistModelImpl(
        private val repo: EpisodesRepository) : PlaylistModel {
    private val compositeSubscription = CompositeSubscription()

    override fun mapItems(playlist: MutableList<MediaBrowserCompat.MediaItem>, observer: Observer<Episode>) {
        val subscription = Observable
                .just(0)
                .flatMapIterable {
                    playlist.filter { it.description?.mediaId != null }
                            .map {
                                repo.getEpisodeByUriAsObservable(it.description.mediaId!!)
                                        .map { it as RealmEpisode }
                            }
                }
                .flatMap { it }
                .subscribe(observer)
        compositeSubscription.add(subscription)
    }

    override fun unsubscribe() {
        compositeSubscription.unsubscribe()
    }

}