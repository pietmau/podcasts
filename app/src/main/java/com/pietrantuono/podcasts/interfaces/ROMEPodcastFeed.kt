package com.pietrantuono.podcasts.interfaces

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.apis.PodcastFeed

class ROMEPodcastFeed(override val episodes: List<Episode>) : PodcastFeed

