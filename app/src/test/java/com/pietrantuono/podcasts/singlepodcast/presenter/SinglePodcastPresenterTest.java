package com.pietrantuono.podcasts.singlepodcast.presenter;


import com.pietrantuono.CrashlyticsWrapper;
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
    @Mock SinglePodcastView view;
    @Mock SinglePodcastModel model;
    @Mock CrashlyticsWrapper crashlyticsWrapper;
    @InjectMocks SinglePodcastPresenter presenter;
    @Captor ArgumentCaptor<Observer<Boolean>> captor;

    @Before
    public void setUp() throws Exception {
        presenter.bindView(view);
    }

    @Test
    public void when_resume_subscribes() {
        // WHEN
        presenter.onResume();
        // THEN
        verify(model).subscribeToFeed(isA(Observer.class));
    }

    @Test
    public void when_pause_then_unsubscribes() {
        // WHEN
        presenter.onPause();
        // THEN
        verify(model).unsubscribe();
    }

    @Test
    public void when_subscribed_then_setsSubscribed() {
        //WHEN
        presenter.onResume();
        verify(model).subscribeToIsSubscribed(captor.capture());
        captor.getValue().onNext(true);
        //THEN
        verify(view).setSubscribed(true);
    }
}