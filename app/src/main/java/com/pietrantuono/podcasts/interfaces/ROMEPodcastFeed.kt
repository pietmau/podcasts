package com.pietrantuono.podcasts.interfaces

import com.pietrantuono.podcasts.apis.PodcastFeed
import models.pojos.Episode

class ROMEPodcastFeed(override val episodes: MutableList<Episode>) : PodcastFeed

