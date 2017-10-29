package com.pietrantuono.podcasts.repository

import android.arch.persistence.room.Dao
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import io.reactivex.Flowable

@Dao
interface PodcastDao {
    fun findBytrackId(trackId: Int): Flowable<Podcast>

}