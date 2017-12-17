package com.pietrantuono.podcasts.downloadfragment.model

import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedEpisode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast
import io.fabric.sdk.android.services.concurrency.AsyncTask
import io.realm.Realm
import models.pojos.Episode
import models.pojos.Podcast
import models.pojos.PodcastRealm
import rx.subjects.PublishSubject

class GetDownloadsTask(
        private val resources: ResourcesProvider,
        private val subject: PublishSubject<List<DownloadedPodcast>>?)
    : AsyncTask<Void, List<DownloadedPodcast>, List<DownloadedPodcast>>() {

    override fun doInBackground(vararg p0: Void?): List<DownloadedPodcast> {
        val result = Realm.getDefaultInstance().where(PodcastRealm::class.java)
                .equalTo("podcastSubscribed", true)
                .findAll()
                .toList()
                .map { toDownloaded(it as Podcast) }
        return result
    }

    override fun onPostExecute(result: List<DownloadedPodcast>?) {
        subject?.onNext(result)
    }

    fun toDownloaded(podcast: Podcast): DownloadedPodcast = DownloadedPodcast.fromPodcast(podcast, podcast.trackName, makeEpisodes(podcast), resources)

    private fun makeEpisodes(podcast: Podcast): List<DownloadedEpisode>? = podcast.episodes?.map { toDownloadedEpisode(it) }

    fun toDownloadedEpisode(episode: Episode): DownloadedEpisode = DownloadedEpisode.fromEpisode(episode, resources)
}