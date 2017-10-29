package com.pietrantuono.podcasts.addpodcast.singlepodcast.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.pietrantuono.podcasts.apis.RoomEpisode


@Dao
interface EpisodeDao {
    @Query("SELECT * FROM roomepisode WHERE link = :link")
    fun findByLink(link: String): List<RoomEpisode>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisode(episode: RoomEpisode)
}