package com.pietrantuono.podcasts.repository.repository

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast

interface RepoServices {
    fun subscribeUnsubscribeToPodcast(podcast: Podcast)

}