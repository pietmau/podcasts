package com.pietrantuono.podcasts.addpodcast.singlepodcast.model;

import com.pietrantuono.podcasts.addpodcast.repository.repository.Repository;
import com.pietrantuono.podcasts.apis.SinglePodcastApi;
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast;

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
public class PodcastModelImplTest {
    private static final String TEXT = "text";
    private static final int TRACK_ID = 1;
    @Mock SinglePodcastApi api;
    @Mock Repository repository;
    @Mock Podcast podcast;
    private SinglePodcastModelImpl model;

    @Before
    public void setUp() throws Exception {
        model = new SinglePodcastModelImpl(api, repository);
        when(podcast.getTrackId()).thenReturn(TRACK_ID);
        when(podcast.getFeedUrl()).thenReturn(TEXT);
        when(repository.getIfSubscribed(TRACK_ID)).thenReturn(Observable.just(true));
        model.startModel(podcast);
    }

    @Test
    public void when_getIsSubscribed_then_getsIfSubscribed() {
        //THEN
        verify(repository).getIfSubscribed(TRACK_ID);
    }

    @Test
    public void when_subscribesToIsSubscribed_then_subscribes() {
        //WHEN
        TestObserver<Boolean> ifSubscribedObserver = new TestObserver<>();
        model.subscribeToIsSubscribedToPodcast(ifSubscribedObserver);
        //THEN
        List<Boolean> result = new ArrayList<>();
        result.add(true);
        ifSubscribedObserver.assertReceivedOnNext(result);
    }

    @Test
    public void when_init_then_getsFeed() {
        //THEN
        verify(api).getFeed(TEXT);
    }

    @Test
    public void when_init_then_getsIsSubscribedToPodcast() {
        //THEN
        verify(repository).getIfSubscribed(TRACK_ID);
    }

    @Test
    public void when_actuallySubscribes_then_repositorySubscribes() {
        //WHEN
        model.actuallySubscribesToPodcast();
        //THEN
        verify(repository).actuallySubscribesToPodcast(podcast);
    }

    @Test
    public void when_actuallyUnSubscribes_then_repositoryUnSubscribes() {
        //WHEN
        model.actuallyUnSubscribesToPodcast();
        //THEN
        verify(repository).actuallyUnSubscribesToPodcast(podcast);
    }
}