package com.pietrantuono.podcasts.repository

import android.content.Context
import android.content.Intent
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.repository.repository.RepoServices
import io.realm.Realm

class RepoServicesImpl(private val context: Context, private val realm: Realm) : RepoServices {

    companion object {
        val TAG: String? = "RepoServicesImpl"
    }

    override fun getAndDowloadEpisodes(podcast: Podcast, subscribe: Boolean) {
        if (podcast.episodes?.isNotEmpty() == true && subscribe) {
            startService(podcast)
        }
    }

    private fun startService(singlePodcast: Podcast) {
        val intent = Intent(context, SaveAndDowloandEpisodeIntentService::class.java)
        intent.putExtra(SaveAndDowloandEpisodeIntentService.TRACK_ID, singlePodcast.trackId)
        intent.putExtra(SaveAndDowloandEpisodeIntentService.URL, singlePodcast.feedUrl)
        context.startService(intent)
    }

}

