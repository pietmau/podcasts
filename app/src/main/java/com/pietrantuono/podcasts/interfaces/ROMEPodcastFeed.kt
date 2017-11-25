package com.pietrantuono.podcasts.interfaces

import com.pietrantuono.podcasts.apis.PodcastFeed
import pojos.Episode

class ROMEPodcastFeed(override val episodes: List<Episode>) : PodcastFeed

