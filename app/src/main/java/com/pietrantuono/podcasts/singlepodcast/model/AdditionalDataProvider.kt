package com.pietrantuono.podcasts.singlepodcast.model

import com.pietrantuono.podcasts.apis.PodcastFeed

interface AdditionalDataProvider {
    fun enrichFeed(podcastFeed: PodcastFeed)

}