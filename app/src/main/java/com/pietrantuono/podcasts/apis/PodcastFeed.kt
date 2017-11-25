package com.pietrantuono.podcasts.apis

import pojos.Episode

interface PodcastFeed {

    val episodes: List<Episode>

}
