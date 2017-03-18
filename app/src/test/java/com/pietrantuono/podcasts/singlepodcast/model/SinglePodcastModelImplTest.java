package com.pietrantuono.podcasts.singlepodcast.model;

import com.pietrantuono.interfaceadapters.apis.SinglePodcastApi;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.observers.TestObserver;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SinglePodcastModelImplTest {
    private static final Integer TRACK_ID = 1;
    @Mock SinglePodcastApi api;
    @Mock Repository repository;
    private SinglePodcastModelImpl model;

    @Before
    public void setUp() throws Exception {
        model = new SinglePodcastModelImpl(api, repository);
    }

    @Test
    public void when_getIsSubscribed_then_getsIfSubscribed() {
        //WHEN
        model.getIsSubscribedToPodcast(TRACK_ID);
        //THEN
        verify(repository).getIfSubscribed(TRACK_ID);
    }

    @Test
    public void when_subscribesToIsSubscribed_then_subscribes() {
        //GIVEN
        when(repository.getIfSubscribed(TRACK_ID)).thenReturn(Observable.just(true));
        model.getIsSubscribedToPodcast(TRACK_ID);
        //WHEN
        TestObserver<Boolean> ifSubscribedObserver = new TestObserver<>();
        model.subscribeToIsSubscribedToPodcast(ifSubscribedObserver);
        //THEN
        List<Boolean> result = new ArrayList<>();
        result.add(true);
        ifSubscribedObserver.assertReceivedOnNext(result);
    }
}