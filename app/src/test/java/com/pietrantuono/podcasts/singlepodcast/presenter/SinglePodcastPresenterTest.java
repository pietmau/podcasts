package com.pietrantuono.podcasts.singlepodcast.presenter;


import com.pietrantuono.CrashlyticsWrapper;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.singlepodcast.model.SinglePodcastModel;
import com.pietrantuono.podcasts.singlepodcast.view.SinglePodcastView;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import rx.Observer;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SinglePodcastPresenterTest {
    private static final String TEXT = "text";
    private static final int TRACK_ID = 1;
    @Mock SinglePodcastView view;
    @Mock SinglePodcastModel model;
    @Mock CrashlyticsWrapper crashlyticsWrapper;
    @InjectMocks SinglePodcastPresenter presenter;
    @Captor ArgumentCaptor<Observer<Boolean>> captor;
    @Mock SinglePodcast podcast;

    @Before
    public void setUp() throws Exception {
        presenter.bindView(view);
    }

    @Test
    public void when_resume_subscribesToFeed() {
        // WHEN
        presenter.onResume();
        // THEN
        verify(model).subscribeToFeed(isA(Observer.class));
    }

    @Test
    public void when_resume_subscribesToIsSubscribedToPodcast() {
        // WHEN
        presenter.onResume();
        // THEN
        verify(model).subscribeToIsSubscribedToPodcast(isA(Observer.class));
    }

    @Test
    public void when_pause_then_unsubscribes() {
        // WHEN
        presenter.onPause();
        // THEN
        verify(model).unsubscribe();
    }

    @Test
    public void when_subscribedToPodcast_then_setsSubscribedToPodcastInTheView() {
        //WHEN
        presenter.onResume();
        verify(model).subscribeToIsSubscribedToPodcast(captor.capture());
        captor.getValue().onNext(true);
        //THEN
        verify(view).setSubscribedToPodcast(true);
    }

    @Test
    public void when_subscribedToPodcast_then_setsSubscribedInTheModel() {
        //WHEN
        presenter.onResume();
        verify(model).subscribeToIsSubscribedToPodcast(captor.capture());
        captor.getValue().onNext(true);
        //THEN
        verify(model).setSubscribedToPodcast(true);
    }

    @Ignore
    @Test
    public void when_subscribeToPodcastIspressed_andIsNotSubascibedToPodcast_then_subscribesToPodcast() {
        //GIVEN
        when(model.isSubscribedToPodcasat()).thenReturn(false);
        //WHEN
        presenter.onSubscribeUnsubscribeToPodcastClicked();
        //THEN
        verify(model).actuallySubscribesToPodcast(isA(SinglePodcast.class));
    }

    @Test
    public void when_init_then_initModel() {
        //WHEN
        presenter.init(podcast, true);
        //THEN
        verify(model).init(podcast);
    }
}