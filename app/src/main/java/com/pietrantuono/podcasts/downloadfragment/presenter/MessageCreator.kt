package com.pietrantuono.podcasts.downloadfragment.presenter

import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider

class MessageCreator(private val resources: ResourcesProvider) {

    fun confirmDownloadEpisode(episodeTitle: String): AlertMessage {
        val title = resources.getString(R.string.download)
        val message = resources.getString(R.string.confirm_download_episode) + " \"" + episodeTitle + "\"?"
        return AlertMessage(title, message)
    }


    class AlertMessage(val title: String, val message: String)

    fun confirmDownloadAllEpisodes(podcastTitle: String): AlertMessage {
        val alertTitle = resources.getString(R.string.download_all)
        val alertMessage = resources.getString(R.string.confirm_download_all) + " \"" + podcastTitle + "\"?"
        return AlertMessage(alertTitle, alertMessage)
    }

    fun confirmDeleteEpisode(episodeTitle: String): AlertMessage {
        val alertTitle = resources.getString(R.string.delete)
        val alertMessage = resources.getString(R.string.confirm_delete_episode) + " \"" + episodeTitle + "\"?"
        return AlertMessage(alertTitle, alertMessage)
    }

    fun confirmDeleteAllEpisode(podcastTitle: String): AlertMessage {
        val alertTitle = resources.getString(R.string.delete)
        val alertMessage = resources.getString(R.string.confirm_delete_all_episodes) + " \"" + podcastTitle + "\"?"
        return AlertMessage(alertTitle, alertMessage)
    }
}