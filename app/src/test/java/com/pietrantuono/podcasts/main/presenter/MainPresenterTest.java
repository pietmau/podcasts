package com.pietrantuono.podcasts.main.presenter;

import com.pietrantuono.podcasts.main.view.MainView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {
    private MainPresenter mainPresenter;
    @Mock MainView view;

    @Before
    public void setUp() {
        mainPresenter = new MainPresenter();
        mainPresenter.bindView(view);
    }

    @Test
    public void given_MainPresenter_when_navigateToAddPodcast_then_navigateToAddPodcast() {
        // WHEN
        mainPresenter.onAddPodcastSelected();
        // THEN
        verify(view).navigateToAddPodcast();
    }

    @Test
    public void when_created_then_showsSubscribed() {
        // WHEN
        mainPresenter.onCreate(false);
        // THEN
        verify(view).navigateToSubscribedPodcasts();
    }

    @Test
    public void when_recreated_then_NotshowsSubscribed() {
        // WHEN
        mainPresenter.onCreate(true);
        // THEN
        verify(view, never()).navigateToSubscribedPodcasts();
    }
}
