package com.pietrantuono.podcasts.subscribedpodcasts.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.Repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubscribedPodcastModelTest {
    @Mock Repository repository;
    @InjectMocks SubscribedPodcastModelImpl model;
    private Observable<List<SinglePodcast>> observable;
    private List<SinglePodcast> result = new ArrayList<>();

    @Before
    public void setUp() throws Exception {

        observable = Observable.just(result);
    }

    @Test
    public void when_subscribeToSubscribedPodcasts_then_repositorySubscribeToSubscribedPodcasts() {
        when(repository.subscribeToSubscribedPodcasts(any())).thenReturn(observable);
        //WHEN
        TestObserver<List<SinglePodcast>> observer = new TestObserver<>();
        model.subscribeToSubscribedPodcasts(observer);
        //THEN
        List<List<SinglePodcast>> list= new ArrayList<>();
        list.add(result);
        observer.assertReceivedOnNext(list);
    }


}