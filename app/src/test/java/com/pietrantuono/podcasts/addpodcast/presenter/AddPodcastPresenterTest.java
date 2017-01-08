package com.pietrantuono.podcasts.addpodcast.presenter;

import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel;
import com.pietrantuono.podcasts.addpodcast.model.SearchResult;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddPodcastPresenterTest {
    private static final String QUERY = "query";
    private AddPodcastPresenter addPodcastPresenter;

    @Mock AddPodcastView view;
    @Mock Throwable error;
    @Mock List<SinglePodcast> list;
    @Mock AddPodcastFragmentMemento memento;
    @Mock AddPodcastsModel addPodcastsModel;
    @Mock SearchResult result;

    @Captor ArgumentCaptor<Observer<SearchResult>> observerCaptor;

    @Before
    public void setUp() {
        when(result.getList()).thenReturn(list);
        addPodcastPresenter = new AddPodcastPresenter(addPodcastsModel, apiLevelChecker);
        addPodcastPresenter.bindView(view, memento);
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
        verify(addPodcastsModel).unsubscribeFromSearch();
    }

    @Test
    public void given_Presenter_when_bound_then_Subscribes() {
        addPodcastPresenter.onResume();
        verify(addPodcastsModel).subscribeToSearch(isA(Observer.class));
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
        verify(addPodcastsModel).searchPodcasts(QUERY);
    }


    @Test
    public void given_Presenter_when_onError_then_viewIsNotified() {
        /*
        * WHEN
        */
        addPodcastPresenter.onResume();
        verify(addPodcastsModel).subscribeToSearch(observerCaptor.capture());
        observerCaptor.getValue().onError(error);
        /*
        * THEN
        */
        verify(view).onError(error);
    }


    @Test
    public void given_Presenter_when_onItemsAvailable_then_viewIsUpdated() {
        /*
        * WHEN
        */
        addPodcastPresenter.onResume();
        verify(addPodcastsModel).subscribeToSearch(observerCaptor.capture());
        observerCaptor.getValue().onNext(result);
        /*
        * THEN
        */
        verify(view).updateSearchResults(list);
    }

    @Test
    public void given_Presenter_when_onQueryTextChange_then_viewIsUpdated() {
        /*
        * WHEN
        */
        addPodcastPresenter.onQueryTextChange(QUERY);
        /*
        * THEN
        */
        verify(view).onQueryTextChange(QUERY);
    }

    @Test
    public void given_Presenter_when_onCompleted_then_viewIsUpdated() {
        /*
        * WHEN
        */
        addPodcastPresenter.onResume();
        verify(addPodcastsModel).subscribeToSearch(observerCaptor.capture());
        observerCaptor.getValue().onCompleted();
        /*
        * THEN
        */
        verify(view, times(2)).showProgressBar(false);
    }

    @Test
    public void given_Presenter_when_onSaveInstanceState_then_mementoIsUpdated() {
        /*
        * WHEN
        */
        when(view.isProgressShowing()).thenReturn(true);
        addPodcastPresenter.onSaveInstanceState(memento);
        /*
        * THEN
        */
        verify(memento).setProgressShowing(true);
    }


}