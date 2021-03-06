package com.pietrantuono.podcasts.subscribedpodcasts.model;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import models.pojos.Podcast;
import repo.repository.PodcastRepo;
import rx.Observable;
import rx.observers.TestObserver;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubscribedPodcastModelTest {
    @Mock PodcastRepo repository;
    @InjectMocks SubscribedPodcastModelImpl model;
    private Observable<List<Podcast>> observable;
    private List<Podcast> result = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        observable = Observable.just(result);
    }

    @Test
    public void when_subscribeToSubscribedPodcasts_then_repositorySubscribeToSubscribedPodcasts() {
        when(repository.getSubscribedPodcasts()).thenReturn(observable);
        //WHEN
        TestObserver<List<Podcast>> observer = new TestObserver<>();
        model.subscribeToSubscribedPodcasts(observer);
        //THEN
        List<List<Podcast>> list= new ArrayList<>();
        list.add(result);
        observer.assertReceivedOnNext(list);
    }


}