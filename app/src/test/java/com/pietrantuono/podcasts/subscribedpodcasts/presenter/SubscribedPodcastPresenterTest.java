package com.pietrantuono.podcasts.subscribedpodcasts.presenter;

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast;
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker;
import com.pietrantuono.podcasts.subscribedpodcasts.model.SubscribedPodcastModel;
import com.pietrantuono.podcasts.subscribedpodcasts.view.SubscribedPodcastView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SubscribedPodcastPresenterTest {
    private static final String TEXT = "text";
    @Mock SubscribedPodcastModel model;
    @Mock ApiLevelChecker apiLevelChecker;
    @InjectMocks SubscribedPodcastPresenter presenter;
    @Mock SubscribedPodcastView view;
    @Captor ArgumentCaptor<Observer<List<Podcast>>> captor;

    @Before
    public void setUp() throws Exception {
        presenter.bindView(view);
    }

    @Test
    public void when_resumes_then_subscribesToModel() {
        //WHEN
        presenter.onStart();
        //THEN
        verify(model).subscribeToSubscribedPodcasts(isA(Observer.class));
    }

    @Test
    public void when_onError_then_userWarned() {
        //WHEN
        subscribesToPodcasts();
        Exception exception = new Exception(TEXT);
        captor.getValue().onError(exception);
        //THEN
        verify(view).onError(exception);
    }

    @Test
    public void when_onNext_then_podcatsSet() {
        //WHEN
        subscribesToPodcasts();
        List<Podcast> list = new ArrayList<>();
        captor.getValue().onNext(list);
        //THEN
        verify(view).setPodcasts(list);
    }

    private void subscribesToPodcasts() {
        presenter.onStart();
        verify(model).subscribeToSubscribedPodcasts(captor.capture());
    }

    @Test
    public void when_onPause_then_unsubscribes() {
        //WHEN
        presenter.onStop();
        //THEN
        verify(model).unsubscribe();
    }
}