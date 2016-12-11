package com.pietrantuono.podcasts.addpodcast.presenter;

import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel;
import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastFragmentMemento;
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import rx.Observer;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddPodcastPresenterTest {
    private static final String QUERY = "query";

    private AddPodcastPresenter addPodcastPresenter;

    @Mock AddPodcastView view;
    @Mock AddPodcastsModel model;
    @Mock Throwable error;
    @Mock List<PodcastSearchResult> list;
    @Mock AddPodcastFragmentMemento memento;

    @Captor ArgumentCaptor<Observer<List<PodcastSearchResult>>> observerCaptor;

    @Before
    public void setUp() {
        addPodcastPresenter = new AddPodcastPresenter();
        addPodcastPresenter.bindView(view, memento);
        addPodcastPresenter.onModelConnected(model);
    }

    @Test
    public void given_Presenter_when_onPause_then_Unsubscribes() {
        /*
        * WHEN
        */
        addPodcastPresenter.onPause();
        /*
        * THEN
        */
        verify(model).unsubscribeFromSearch();
    }

    @Test
    public void given_Presenter_when_bound_then_Subscribes() {
        verify(model).subscribeToSearch(isA(Observer.class));
    }

    @Test
    public void given_Presenter_when_onQueryTextSubmit_then_searchPodcasts() {
        /*
        * WHEN
        */
        addPodcastPresenter.onQueryTextSubmit(QUERY);
        /*
        * THEN
        */
        verify(model).searchPodcasts(QUERY);
    }


    @Test
    public void given_Presenter_when_onError_then_viewIsNotified() {
        /*
        * GIVEN
        */
        addPodcastPresenter.onResume();
        /*
        * WHEN
        */
        verify(model).subscribeToSearch(observerCaptor.capture());
        observerCaptor.getValue().onError(error);
        /*
        * THEN
        */
        verify(view).onError(error);
    }


    @Test
    public void given_Presenter_when_onItemsAvailable_then_viewIsUpdated() {
        /*
        * GIVEN
        */
        addPodcastPresenter.onResume();
        /*
        * WHEN
        */
        verify(model).subscribeToSearch(observerCaptor.capture());
        observerCaptor.getValue().onNext(list);
        /*
        * THEN
        */
        verify(view).updateSearchResults(list);
    }

}