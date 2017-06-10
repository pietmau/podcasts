package com.pietrantuono.podcasts.singlepodcast.presenter

import android.net.Uri
import android.os.Handler
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel
import com.pietrantuono.podcasts.apis.PodcastFeed
import java.util.*

class MediaSourceCreator(val mediaDataSourceFactory: DataSource.Factory, val mainHandler: Handler,
                         val eventLogger: ExtractorMediaSource.EventListener) {

    fun createConcatenateMediaSource(podcastFeed: PodcastFeed): MediaSource {
        val array = createArgs(podcastFeed)
        return ConcatenatingMediaSource(* array)
    }

    private fun createArgs(podcastFeed: PodcastFeed): Array<out MediaSource> {
        val result = ArrayList<MediaSource>()
        for (episode in podcastFeed.episodes) {
            val source = createSource(episode)
            if (source != null) {
                result.add(source)
            }
        }
        return result.toTypedArray()
    }

    private fun createSource(episode: PodcastEpisodeModel): MediaSource? {
        val enclosure = episode.enclosures.get(0)
        if (enclosure != null && enclosure.url != null) {
            return ExtractorMediaSource(Uri.parse(enclosure.url), mediaDataSourceFactory,
                    DefaultExtractorsFactory(), mainHandler, eventLogger)
        } else {
            return null
        }
    }

}