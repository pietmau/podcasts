package com.pietrantuono.podcasts.player.player

import android.net.Uri
import android.os.Handler
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel
import com.pietrantuono.podcasts.apis.PodcastFeed

class MediaSourceCreator(val mediaDataSourceFactory: DataSource.Factory, val mainHandler: Handler,
                         val eventLogger: ExtractorMediaSource.EventListener) {

    fun createConcatenateMediaSource(podcastFeed: PodcastFeed): MediaSource {
        val array = createMediaSourcesFromUris(podcastFeed)
        return ConcatenatingMediaSource(* array)
    }

    private fun createMediaSourcesFromUris(podcastFeed: PodcastFeed): Array<out MediaSource> {
        val result = mutableListOf<MediaSource>()
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

    fun createSourceFromFeed(feed: PodcastFeed?): PodcastFeedSource {
        val uris = mutableListOf<Uri>()
        if (feed != null) {
            for (episode in feed.episodes) {
                if (episode.imageUrl != null) {
                    uris.add(Uri.parse(episode.imageUrl))
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


