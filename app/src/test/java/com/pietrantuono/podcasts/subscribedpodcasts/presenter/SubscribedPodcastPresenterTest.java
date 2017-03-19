package com.pietrantuono.podcasts.subscribedpodcasts.presenter;

import com.pietrantuono.podcasts.subscribedpodcasts.model.SubscribedPodcastModel;
import com.pietrantuono.podcasts.subscribedpodcasts.view.SubscribedPodcastView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import rx.Observer;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SubscribedPodcastPresenterTest {
    @Mock SubscribedPodcastModel model;
    @InjectMocks SubscribedPodcastPresenter presenter;
    @Mock SubscribedPodcastView view;

    @Before
    public void setUp() throws Exception {
        presenter.bindView(view);
    }

    @Test
    public void when_resumes_then_subscribesToModel() {
        //WHEN
        presenter.onResume();
        //THEN
        verify(model).subscribeToSubscribedPodcasts(isA(Observer.class));
    }
}