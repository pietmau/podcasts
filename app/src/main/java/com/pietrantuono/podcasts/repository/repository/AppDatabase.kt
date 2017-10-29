package com.pietrantuono.podcasts.repository.repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastImpl
import com.pietrantuono.podcasts.repository.PodcastDao

@Database(entities = arrayOf(PodcastImpl::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        val PODCAST_DATABASE = "podcast_database"
    }

    abstract fun podcastDao(): PodcastDao
}