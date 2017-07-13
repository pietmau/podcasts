package com.pietrantuono.podcasts.singlepodcast.model


import com.pietrantuono.podcasts.apis.PodcastFeed

class AdditionalDataProviderImpl(repository: Repository) : AdditionalDataProvider {

    override fun enrichFeed(podcastFeed: PodcastFeed): PodcastFeed {
        for (episode in podcastFeed.episodes) {
        }
        return podcastFeed
    }
}
