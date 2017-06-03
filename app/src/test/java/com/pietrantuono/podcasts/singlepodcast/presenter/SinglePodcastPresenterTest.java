package com.pietrantuono.podcasts.singlepodcast.presenter;


import com.pietrantuono.CrashlyticsWrapper;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.singlepodcast.model.SinglePodcastModel;
import com.pietrantuono.podcasts.singlepodcast.view.SinglePodcastView;

import org.junit.Before;
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
        presenter.onStart();
        // THEN
        verify(model).subscribeToFeed(isA(Observer.class));
    }

    @Test
    public void when_resume_subscribesToIsSubscribedToPodcast() {
        // WHEN
        presenter.onStart();
        // THEN
        verify(model).subscribeToIsSubscribedToPodcast(isA(Observer.class));
    }

    @Test
    public void when_pause_then_unsubscribes() {
        // WHEN
        presenter.onStop();
        // THEN
        verify(model).unsubscribe();
    }

    @Test
    public void when_subscribedToPodcast_then_setsSubscribedToPodcastInTheView() {
        //WHEN
        presenter.onStart();
        subscribeToSubscribedToPodcast();
        //THEN
        verify(view).setSubscribedToPodcast(true);
    }

    @Test
    public void when_init_then_initModel() {
        //WHEN
        presenter.startPresenter(podcast, true);
        //THEN
        verify(model).init(podcast);
    }

    @Test
    public void when_onSubscribeUnsubscribeToPodcastClicked_And_IsSubscribedToPodcast_then_actuallyUnsubscribes() {
        //WHEN
        isSubscribedToPodcast(true);
        //THEN
        verify(model).actuallyUnSubscribesToPodcast();
    }

    private void isSubscribedToPodcast(boolean t) {
        presenter.onSubscribeUnsubscribeToPodcastClicked();
        verify(model).isSubscribedToPodcast(captor.capture());
        captor.getValue().onNext(t);
    }

    @Test
    public void when_onSubscribeUnsubscribeToPodcastClicked_And_IsNoSubscribedToPodcast_then_actuallySubscribes() {
        //WHEN
        isSubscribedToPodcast(false);
        //THEN
        verify(model).actuallySubscribesToPodcast();
    }


    private void subscribeToSubscribedToPodcast() {
        verify(model).subscribeToIsSubscribedToPodcast(captor.capture());
        captor.getValue().onNext(true);
    }
}