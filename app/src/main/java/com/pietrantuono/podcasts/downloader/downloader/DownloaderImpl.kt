package com.pietrantuono.podcasts.downloader.downloader

import android.content.Context
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.downloader.service.DownloaderService.Companion.COMMAND_DELETE_ALL_EPISODES
import com.pietrantuono.podcasts.downloader.service.DownloaderService.Companion.COMMAND_DELETE_EPISODE
import com.pietrantuono.podcasts.downloader.service.DownloaderService.Companion.COMMAND_DOWNLOAD_ALL_EPISODES
import com.pietrantuono.podcasts.downloader.service.DownloaderService.Companion.COMMAND_DOWNLOAD_EPISODE
import com.pietrantuono.podcasts.downloader.service.DownloaderService.Companion.EXTRA_DOWNLOAD_REQUEST_ID
import com.pietrantuono.podcasts.downloader.service.DownloaderService.Companion.EXTRA_DOWNLOAD_REQUEST_ID_LIST
import com.pietrantuono.podcasts.downloader.service.DownloaderService.Companion.EXTRA_TRACK
import com.pietrantuono.podcasts.downloader.service.DownloaderService.Companion.EXTRA_TRACK_LIST

class DownloaderImpl(context: Context) : SimpleDownloader(context) {

    override fun downloadEpisodeFromLink(link: String) {
        val intent = getIntent(COMMAND_DOWNLOAD_EPISODE)
        intent.putExtra(EXTRA_TRACK, link)
        startService(intent)
    }

    override fun downloadIfAppropriate(podcast: Podcast?) {
        podcast?.episodes?.
                map { it.link }?.
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
            episode.link?.let { tracks.add(it) }
        }
        return tracks
    }

    private fun downloadAllInternal(tracks: ArrayList<String>) {
        val intent = getIntent(COMMAND_DOWNLOAD_ALL_EPISODES)
        intent.putStringArrayListExtra(EXTRA_TRACK_LIST, tracks)
        startService(intent)
    }

}