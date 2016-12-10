package com.pietrantuono.podcasts.addpodcast.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action;
import rx.functions.Action0;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchPodcastInteractorImplTest {
    private static final String STRING = "string";
    private SearchPodcastInteractorImpl interactor;
    private Observable<List<PodcastSearchResult>> observable;

    @Mock SearchApi api;
    @Mock Observer<List<PodcastSearchResult>> observer;
    @Mock List<PodcastSearchResult> list;

    @Before
    public void setUp() {
        observable = Observable.just(list);
        interactor = new SearchPodcastInteractorImpl(api);
        when(api.search(anyString())).thenReturn(observable);
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

}

