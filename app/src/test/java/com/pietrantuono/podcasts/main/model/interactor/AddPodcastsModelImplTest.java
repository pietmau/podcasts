package com.pietrantuono.podcasts.main.model.interactor;

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import rx.Observer;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AddPodcastsModelImplTest {
    private static final String QUERY = "query";

    private AddPodcastsModelImpl modelInteractor;

    @Mock SearchPodcastInteractor searchInteractor;
    @Mock Observer<List<SinglePodcast>> observer;

    @Before
    public void setUp() {
        modelInteractor = new AddPodcastsModelImpl(searchInteractor);
    }

    @Test
    public void given_Model_when_subscribe_then_searchIsSubscribed() {
        /*
        * WHEN
        */
        modelInteractor.subscribeToSearch(observer);
        /*
        * THEN
        */
        verify(searchInteractor).subscribeToSearch(observer);
    }

    @Test
    public void given_Model_when_unSubscribe_then_searchIsUnSubscribed() {
        /*
        * WHEN
        */
        modelInteractor.unsubscribeFromSearch();
        /*
        * THEN
        */
        verify(searchInteractor).unsubscribeFromSearch();
    }

    @Test
    public void given_Model_when_search_then_search() {
        /*
        * WHEN
        */
        modelInteractor.searchPodcasts(QUERY);
        /*
        * THEN
        */
        verify(searchInteractor).searchPodcasts(QUERY);
    }



}