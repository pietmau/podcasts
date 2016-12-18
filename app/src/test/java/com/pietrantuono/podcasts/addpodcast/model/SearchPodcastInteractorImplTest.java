package com.pietrantuono.podcasts.addpodcast.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;

import org.junit.Assert;
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
public class SearchPodcastInteractorImplTest {
    private static final String STRING = "string";
    private SearchPodcastInteractorImpl interactor;
    private Observable<List<PodcastSearchResult>> observable;
    private TestSubscriber testSubscriber;

    @Mock SearchApi api;
    @Mock Observer<List<PodcastSearchResult>> observer;
    @Mock List<PodcastSearchResult> list;

    @Before
    public void setUp() {
        observable = Observable.just(list);
        interactor = new SearchPodcastInteractorImpl(api);
        when(api.search(anyString())).thenReturn(observable);
        testSubscriber = new TestSubscriber();
    }

    @Test
    public void given_Interactor_when_search_then_subscribes() {
        /*
        * WHEN
        */
        interactor.subscribeToSearch(observer);
        interactor.searchPodcasts(STRING);
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
        interactor.subscription = new TestSubscription();
        interactor.unsubscribeFromSearch();
        /*
        * THEN
        */
        assertTrue(((TestSubscription) interactor.subscription).unsunbscribeRequested);
    }

    @Test
    public void given_Interactor_when_subscribeToSearch_then_bserverSubscribes() {
        /*
        * GIVEN
        */
        List<List<PodcastSearchResult>> result = new ArrayList();
        result.add(list);
        /*
        * WHEN
        */
        interactor.observable = Observable.just(list);
        interactor.subscribeToSearch(testSubscriber);
        /*
        * THEN
        */
        testSubscriber.assertReceivedOnNext(result);
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

