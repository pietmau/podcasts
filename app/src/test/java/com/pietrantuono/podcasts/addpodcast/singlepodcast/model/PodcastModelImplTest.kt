package com.pietrantuono.podcasts.addpodcast.singlepodcast.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.apis.PodcastFeed
import com.pietrantuono.podcasts.apis.SinglePodcastApi
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import rx.Observable
import rx.observers.TestObserver
import rx.schedulers.Schedulers
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class PodcastModelImplTest {
    @Mock lateinit var api: SinglePodcastApi
    @Mock lateinit var repository: PodcastRepo
    @Mock lateinit var podcast: Podcast
    lateinit var model: SinglePodcastModelImpl
    @Mock lateinit var feed: PodcastFeed
    private val io = Schedulers.trampoline()
    private val main = Schedulers.trampoline()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        model = SinglePodcastModelImpl(api, repository, io, main)
        `when`<String>(podcast.feedUrl).thenReturn(TEXT)
        `when`(repository.getIfSubscribed(podcast)).thenReturn(Observable.just<Boolean>(true))
        val observable = Observable.just(feed)
        `when`(api.getFeed(ArgumentMatchers.anyString())).thenReturn(observable)
        model.startModel(podcast)
    }


    @Test
    fun when_subscribesToIsSubscribed_then_subscribes() {
        //WHEN
        val ifSubscribedObserver = TestObserver<Boolean>()
        model.subscribeToIsSubscribedToPodcast(ifSubscribedObserver)
        //THEN
        val result = ArrayList<Boolean>()
        result.add(true)
        ifSubscribedObserver.assertReceivedOnNext(result)
    }

    @Test
    fun when_init_then_getsFeed() {
        //THEN
        verify<SinglePodcastApi>(api).getFeed(TEXT)
    }

    companion object {
        private val TEXT = "text"
        private val TRACK_ID = 1
    }
}