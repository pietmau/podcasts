package com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter


import com.nhaarman.mockito_kotlin.KArgumentCaptor
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.isA
import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.SinglePodcastView
import com.pietrantuono.podcasts.apis.PodcastFeed
import models.pojos.Podcast
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import rx.Observer

@RunWith(MockitoJUnitRunner::class)
class PodcastPresenterTest {
    @Mock lateinit var view: SinglePodcastView
    @Mock lateinit var model: SinglePodcastModel
    @Mock lateinit var crashlyticsWrapper: CrashlyticsWrapper
    @InjectMocks lateinit var presenter: SinglePodcastPresenter
    lateinit var captor: KArgumentCaptor<Observer<Boolean>>
    @Mock lateinit var podcast: Podcast

    @Before
    @Throws(Exception::class)
    fun setUp() {
        captor = argumentCaptor()
        presenter.bindView(view)
    }

    @Test
    fun when_resume_subscribesToFeed() {
        // WHEN
        presenter.onResume()
        // THEN
        verify<SinglePodcastModel>(model).subscribeToFeed(isA<Observer<PodcastFeed>>(), )
    }

    @Test
    fun when_resume_subscribesToIsSubscribedToPodcast() {
        // WHEN
        presenter.onResume()
        // THEN
        verify<SinglePodcastModel>(model).subscribeToIsSubscribedToPodcast(isA<Observer<Boolean>>())
    }

    @Test
    fun when_pause_then_unsubscribes() {
        // WHEN
        presenter.onPause()
        // THEN
        verify<SinglePodcastModel>(model).unsubscribe()
    }

    @Test
    fun when_subscribedToPodcast_then_setsSubscribedToPodcastInTheView() {
        //WHEN
        presenter.onResume()
        subscribeToSubscribedToPodcast()
        //THEN
        verify<SinglePodcastView>(view).setSubscribedToPodcast(true)
    }

    @Test
    fun when_init_then_initModel() {
        //WHEN
        presenter.startPresenter(podcast, true, true)
        //THEN
        verify<SinglePodcastModel>(model).startModel(podcast)
    }

    private fun isSubscribedToPodcast(t: Boolean) {
        presenter.onOptionsItemSelected(R.id.subscribe_unsubscribe)
        verify<SinglePodcastModel>(model).onSubscribeUnsubscribeToPodcastClicked()
        captor.firstValue.onNext(t)
    }

    private fun subscribeToSubscribedToPodcast() {
        verify<SinglePodcastModel>(model).subscribeToIsSubscribedToPodcast(captor.capture())
        captor.firstValue.onNext(true)
    }

    companion object {
        private val TEXT = "text"
        private val TRACK_ID = 1
    }
}