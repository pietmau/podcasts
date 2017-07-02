package com.pietrantuono.podcasts.player.player

import android.net.Uri
import android.os.Handler
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.pietrantuono.podcasts.apis.PodcastFeed

class MediaSourceCreator(val mediaDataSourceFactory: DataSource.Factory, val mainHandler: Handler,
                         val eventLogger: ExtractorMediaSource.EventListener) {

    fun createSourceFromFeed(feed: PodcastFeed?): PodcastFeedSource {
        val uris = mutableListOf<Uri>()
        if (feed != null) {
            for (episode in feed.episodes) {
                val enclosures = episode.enclosures
                if (enclosures != null && enclosures.size > 0) {
                    try {
                        uris.add(Uri.parse(enclosures[0].url))
                    } catch (exception: Exception) {
                    }
                }
            }
        }
        return PodcastFeedSourceImpl(uris)
    }

    fun createConcatenateMediaSource(podcastFeed: PodcastFeedSource): MediaSource {
        val array = createMediaSourcesFromUris(podcastFeed)
        return ConcatenatingMediaSource(* array)
    }

    private fun createMediaSourcesFromUris(podcastFeed: PodcastFeedSource): Array<out MediaSource> {
        val result = mutableListOf<MediaSource>()
        for (uri in podcastFeed.uris) {
            result.add(ExtractorMediaSource(uri, mediaDataSourceFactory,
                    DefaultExtractorsFactory(), mainHandler, eventLogger))
        }
        return result.toTypedArray()
    }
}


