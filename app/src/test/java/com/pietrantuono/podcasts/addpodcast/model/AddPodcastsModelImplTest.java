package com.pietrantuono.podcasts.addpodcast.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddPodcastsModelImplTest {
    private static final String STRING = "string";
    private AddPodcastsModelImpl addPodcastsModel;
    private Observable<SearchResult> observable;
    private TestSubscriber testSubscriber;
    private List<SearchResult> list = new ArrayList<>();
    private Observable<SearchResult> cachedRequest;

    @Mock SearchApi api;
    @Mock Observer<SearchResult> observer;
    @Mock SearchResult searchResult;

    @Before
    public void setUp() {
        observable = Observable.just(searchResult);
        addPodcastsModel = new AddPodcastsModelImpl(api);
        when(api.search(anyString())).thenReturn(observable);
        testSubscriber = new TestSubscriber();
        list.add(searchResult);
        cachedRequest = Observable.just(searchResult);
    }

    @Test
    public void given_Interactor_when_search_then_subscribes() {
        /*
        * WHEN
        */
        addPodcastsModel.subscribeToSearch(observer);
        addPodcastsModel.searchPodcasts(STRING);
        /*
        * THEN
        */
        verify(api).search(STRING);
    }

    @Test
    public void given_Interactor_when_unsubscribe_then_subscriptionUnsubscribe() {
        /*
        * WHEN
        */
        addPodcastsModel.subscription = new TestSubscription();
        addPodcastsModel.unsubscribeFromSearch();
        /*
        * THEN
        */
        assertTrue(((TestSubscription) addPodcastsModel.subscription).unsunbscribeRequested);
    }

    @Test
    public void given_Interactor_when_subscribeToSearch_then_bserverSubscribes() {
        /*
        * GIVEN
        */
        List<SearchResult> result = new ArrayList();
        result.add(searchResult);
        /*
        * WHEN
        */
        addPodcastsModel.cachedRequest = Observable.just(searchResult);
        addPodcastsModel.subscribeToSearch(testSubscriber);
        /*
        * THEN
        */
        testSubscriber.assertReceivedOnNext(result);
    }

    @Test
    public void given_Model_when_search_then_search() {
        /*
        * WHEN
        */
        TestSubscriber testSubscriber = new TestSubscriber();
        addPodcastsModel.subscribeToSearch(testSubscriber);
        addPodcastsModel.searchPodcasts(STRING);
        /*
        * THEN
        */
        testSubscriber.assertReceivedOnNext(list);
    }

    @Test
    public void given_Model_when_unSubscribe_then_searchIsUnSubscribed() {
        /*
        * WHEN
        */
        TestSubscriber<Object> testSubscriber = new TestSubscriber<>();
        addPodcastsModel.subscription = testSubscriber;
        addPodcastsModel.unsubscribeFromSearch();
        /*
        * THEN
        */
        testSubscriber.assertUnsubscribed();
    }

    @Test
    public void given_Model_when_subscribe_then_searchIsSubscribed() {
        /*
        * WHEN
        */
        addPodcastsModel.cachedRequest = cachedRequest;
        TestSubscriber testSubscriber = new TestSubscriber();
        addPodcastsModel.subscribeToSearch(testSubscriber);
        /*
        * THEN
        */
        testSubscriber.assertReceivedOnNext(list);
    }

    private class TestSubscription implements Subscription {
        boolean unsunbscribeRequested;

        @Override
        public void unsubscribe() {
            unsunbscribeRequested = true;
        }

        @Override
        public boolean isUnsubscribed() {
            return false;
        }
    }
}

