package com.pietrantuono.podcasts.apis

import models.pojos.Episode

interface PodcastFeed {

    val episodes: MutableList<Episode>

}
