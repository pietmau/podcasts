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

    override fun subscribeUnsubscribeToPodcast(podcast: Podcast) {
        if (podcast.episodes == null || podcast.episodes!!.isEmpty()) {
            startService(podcast)
        }
    }

    private fun startService(singlePodcast: Podcast) {
        val intent = Intent(context, SaveEpisodeIntentService::class.java)
        intent.putExtra(SaveEpisodeIntentService.TRACK_ID, singlePodcast.trackId)
        intent.putExtra(SaveEpisodeIntentService.URL, singlePodcast.feedUrl)
        context.startService(intent)
    }

}

