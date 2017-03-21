package com.pietrantuono.podcasts.subscribedpodcasts.model;

import com.pietrantuono.podcasts.singlepodcast.model.Repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import rx.Observer;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SubscribedPodcastModelTest {
    @Mock Repository repository;
    @InjectMocks SubscribedPodcastModelImpl model;

    @Test
    public void when_subscribeToSubscribedPodcasts_then_repositorySubscribeToSubscribedPodcasts() {
        //WHEN
        model.subscribeToSubscribedPodcasts(isA(Observer.class));
        //THEN
        verify(repository).subscribeToSubscribedPodcasts(isA(Observer.class));
    }
}