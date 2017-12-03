package com.pietrantuono.podcasts.downloader.downloader

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import com.pietrantuono.podcasts.downloader.service.*
import com.pietrantuono.podcasts.settings.PreferencesManager
import models.pojos.Episode
import models.pojos.Podcast

class DownloaderImpl(
        context: Context,
        private val preferencesManager: PreferencesManager) : SimpleDownloader(context) {

    override fun downloadEpisodeFromUri(uri: String) {
        val intent = getIntentWitUri(uri)
        startService(intent)
    }

    private fun getIntentWitUri(uri: String): Intent {
        val intent = getIntent(COMMAND_DOWNLOAD_EPISODE)
        intent.putExtra(EXTRA_TRACK, uri)
        return intent
    }

    override fun downloadAndPlayFromUri(uri: String) {
        val intent = getIntentWitUri(uri)
        intent.putExtra(PLAY_WHEN_READY, true)
        startService(intent)
    }

    override fun downloadIfAppropriate(podcast: Podcast?) {
        if (!preferencesManager.downloadAutomatically) {
            return
        }
        podcast?.episodes?.
                map { it.uri }?.
                filterNotNull()?.
                toList()?.let {
            downloadAllInternal(ArrayList(it))
        }
    }

    override fun deleteEpisode(episode: Episode) {
        val intent = getIntent(COMMAND_DELETE_EPISODE)
        intent.putExtra(EXTRA_DOWNLOAD_REQUEST_ID, episode.downloadRequestId)
        startService(intent)
    }

    override fun downLoadAllEpisodes(episodes: List<Episode>) {
        val tracks = makeTracks(episodes)
        downloadAllInternal(tracks)
    }

    override fun deleteAllEpisodes(podcast: Podcast) {
        val intent = getIntent(COMMAND_DELETE_ALL_EPISODES)
        intent.putExtra(EXTRA_DOWNLOAD_REQUEST_ID_LIST, getIds(podcast.episodes))
        startService(intent)
    }

    private fun getIds(episodesList: List<Episode>?): ArrayList<Long> {
        if (episodesList == null) {
            return ArrayList(emptyList())
        }
        return ArrayList(episodesList.map { it.downloadRequestId }.toList())
    }

    private fun makeTracks(episodes: List<Episode>?): ArrayList<String> {
        val tracks = arrayListOf<String>()
        if (episodes == null) {
            return tracks
        }
        for (episode in episodes) {
            episode.uri?.let { tracks.add(it) }
        }
        return tracks
    }

    private fun downloadAllInternal(tracks: ArrayList<String>) {
        val intent = getIntent(COMMAND_DOWNLOAD_ALL_EPISODES)
        intent.putStringArrayListExtra(EXTRA_TRACK_LIST, tracks)
        startService(intent)
    }


}