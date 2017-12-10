package com.pietrantuono.podcasts.playlist.model

import android.support.v4.media.MediaBrowserCompat
import io.realm.Realm
import models.pojos.Episode
import models.pojos.RealmEpisode
import repo.repository.EpisodesRepository
import rx.Observable
import rx.Observer

class PlaylistModelImpl(
        private val repo: EpisodesRepository) : PlaylistModel {
    companion object {
        const val URI: String = "uri"
    }

    override fun mapItems(playlist: MutableList<MediaBrowserCompat.MediaItem>, observer: Observer<List<Episode>>) {
        Observable.just(
                Realm.getDefaultInstance().use { realm ->
                    playlist.map {
                        val findFirstAsync = realm.where(RealmEpisode::class.java)
                                .equalTo(URI, it.description.mediaId)
                                .findFirst()
                        findFirstAsync
                    }.filter { it != null }
                            //.filter { it.isLoaded && it.isValid }
                            //.map { realm.copyFromRealm(it) }
                            .map { it as Episode }
                }).subscribe(observer)
    }
}