package com.pietrantuono.podcasts.main.presenter

import com.nhaarman.mockito_kotlin.any
import com.pietrantuono.podcasts.main.view.MainView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {
    @Mock lateinit var view: MainView
    @Mock lateinit var killswitch: KillSwitch
    @InjectMocks lateinit var mainPresenter: MainPresenter

    @Before
    fun setUp() {
        mainPresenter.bindView(view)
    }

    @Test
    fun given_MainPresenter_when_navigateToAddPodcast_then_navigateToAddPodcast() {
        // WHEN
        mainPresenter.onAddPodcastSelected()
        // THEN
        verify<MainView>(view).navigateToAddPodcast()
    }

    @Test
    fun when_created_then_showsSubscribed() {
        // WHEN
        mainPresenter.onCreate(false)
        // THEN
        verify<MainView>(view).navigateToSubscribedPodcasts()
    }

    @Test
    fun when_recreated_then_NotshowsSubscribed() {
        // WHEN
        mainPresenter.onCreate(true)
        // THEN
        verify<MainView>(view, never()).navigateToSubscribedPodcasts()
    }

    @Test
    fun when_starts_then_startsTheKillswitch() {
        //WHEN
        mainPresenter.onStart()
        //THEN
        verify(killswitch).checkIfNeedsToBeKilled(any())
    }

    @Test
    fun when_stop_then_unsubscribes() {
        //WHEN
        mainPresenter.onStop()
        //THEN
        verify(killswitch).unsubscribe()
    }

    @Test
    fun when_onAddPodcastSelected_then_navigateToAddPodcast() {
        //WHEN
        mainPresenter.onAddPodcastSelected()
        //THEN
        verify(view).navigateToAddPodcast()
    }

    @Test
    fun when_onSubscribeSelected_then_navigateToSubscribedPodcasts() {
        //WHEN
        mainPresenter.onSubscribeSelected()
        //THEN
        verify(view).navigateToSubscribedPodcasts()
    }

    @Test
    fun when_onSettingsSelected_then_navigateToSettings() {
        //WHEN
        mainPresenter.onSettingsSelected()
        //THEN
        verify(view).navigateToSettings()
    }


    @Test
    fun when_onDownloadsSelected_then_navigateToDownloads() {
        //WHEN
        mainPresenter.onDownloadsSelected()
        //THEN
        verify(view).navigateToDownloads()
    }
}
