package com.pietrantuono.podcasts.apis

import diocan.pojos.Episode

interface PodcastFeed {

    val episodes: List<Episode>

}
