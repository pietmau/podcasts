package com.pietrantuono.podcasts.addpodcast.model

import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.observers.TestSubscriber
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class AddPodcastsModelImplTest {
    lateinit var addPodcastsModel: AddPodcastsModelImpl
    lateinit var observable: Observable<SearchResult>
    lateinit var testSubscriber: TestSubscriber<SearchResult>
    private val list = ArrayList<SearchResult>()
    lateinit var cachedRequest: Observable<SearchResult>
    @Mock lateinit var api: SearchApi
    @Mock lateinit var observer: Observer<SearchResult>
    @Mock lateinit var searchResult: SearchResult

    @Before
    fun setUp() {
        testSubscriber = TestSubscriber<SearchResult>()
        observable = Observable.just(searchResult)
        addPodcastsModel = AddPodcastsModelImpl(api)
        `when`(api.search(anyString())).thenReturn(observable)
        //testSubscriber = TestSubscriber<SearchResult>()
        list.add(searchResult)
        cachedRequest = Observable.just(searchResult)
    }

    @Test
    fun given_Interactor_when_search_then_subscribes() {
        /*
        * WHEN
        */
        addPodcastsModel.subscribeToSearch(observer)
        addPodcastsModel.searchPodcasts(STRING)
        /*
        * THEN
        */
        verify<SearchApi>(api).search(STRING)
    }

    @Test
    fun given_Interactor_when_unsubscribe_then_subscriptionUnsubscribe() {
        /*
        * WHEN
        */
        addPodcastsModel.subscription = TestSubscription()
        addPodcastsModel.unsubscribeFromSearch()
        /*
        * THEN
        */
        assertTrue((addPodcastsModel.subscription as TestSubscription).unsunbscribeRequested)
    }

    @Test
    fun given_Interactor_when_subscribeToSearch_then_bserverSubscribes() {
        /*
        * GIVEN
        */
        val result = ArrayList<SearchResult>()
        result.add(searchResult)
        /*
        * WHEN
        */
        addPodcastsModel.cachedRequest = Observable.just(searchResult)
        addPodcastsModel.subscribeToSearch(testSubscriber)
        /*
        * THEN
        */
        testSubscriber.assertReceivedOnNext(result)
    }

    @Test
    fun given_Model_when_search_then_search() {
        /*
        * WHEN
        */
        //val testSubscriber = TestSubscriber()
        addPodcastsModel.subscribeToSearch(testSubscriber)
        addPodcastsModel.searchPodcasts(STRING)
        /*
        * THEN
        */
        testSubscriber.assertReceivedOnNext(list)
    }

    @Test
    fun given_Model_when_unSubscribe_then_searchIsUnSubscribed() {
        /*
        * WHEN
        */
        val testSubscriber = TestSubscriber<Any>()
        addPodcastsModel.subscription = testSubscriber
        addPodcastsModel.unsubscribeFromSearch()
        /*
        * THEN
        */
        testSubscriber.assertUnsubscribed()
    }

    @Test
    fun given_Model_when_subscribe_then_searchIsSubscribed() {
        /*
        * WHEN
        */
        addPodcastsModel.cachedRequest = cachedRequest
        //val testSubscriber = TestSubscriber()
        addPodcastsModel.subscribeToSearch(testSubscriber)
        /*
        * THEN
        */
        testSubscriber.assertReceivedOnNext(list)
    }

    private inner class TestSubscription : Subscription {
        internal var unsunbscribeRequested: Boolean = false

        override fun unsubscribe() {
            unsunbscribeRequested = true
        }

        override fun isUnsubscribed(): Boolean {
            return false
        }
    }

    companion object {
        private val STRING = "string"
    }
}

