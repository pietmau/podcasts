package com.pietrantuono.podcasts.addpodcast.presenter

import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel
import com.pietrantuono.podcasts.addpodcast.model.SearchResult
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastFragmentMemento
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastView
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers.isA
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import rx.Observer

@RunWith(MockitoJUnitRunner::class)
class AddPodcastPresenterTest {
    lateinit var addPodcastPresenter: AddPodcastPresenter
    @Mock lateinit var view: AddPodcastView
    @Mock lateinit var error: Throwable
    @Mock lateinit var list: List<Podcast>
    @Mock lateinit var memento: AddPodcastFragmentMemento
    @Mock lateinit var addPodcastsModel: AddPodcastsModel
    @Mock lateinit var result: SearchResult
    @Mock lateinit var apiLevelChecker: ApiLevelChecker
    @Captor lateinit var observerCaptor: ArgumentCaptor<Observer<SearchResult>>

    @Before
    fun setUp() {
        `when`(result.list).thenReturn(list)
        addPodcastPresenter = AddPodcastPresenter(addPodcastsModel, apiLevelChecker)
        addPodcastPresenter.bindView(view, memento)
    }

    @Test
    fun given_Presenter_when_onPause_then_Unsubscribes() {
        /*
        * WHEN
        */
        addPodcastPresenter.onPause()
        /*
        * THEN
        */
        verify<AddPodcastsModel>(addPodcastsModel).unsubscribeFromSearch()
    }

    @Test
    fun given_Presenter_when_bound_then_Subscribes() {
        addPodcastPresenter.onResume()
        verify<AddPodcastsModel>(addPodcastsModel).subscribeToSearch(isA(Observer::class.java) as Observer<SearchResult>?)
    }

    @Test
    fun given_Presenter_when_onQueryTextSubmit_then_searchPodcasts() {
        /*
        * WHEN
        */
        addPodcastPresenter.onQueryTextSubmit(QUERY)
        /*
        * THEN
        */
        verify<AddPodcastsModel>(addPodcastsModel).searchPodcasts(QUERY)
    }


    @Test
    fun given_Presenter_when_onError_then_viewIsNotified() {
        /*
        * WHEN
        */
        addPodcastPresenter.onResume()
        verify<AddPodcastsModel>(addPodcastsModel).subscribeToSearch(observerCaptor.capture())
        observerCaptor.value.onError(error)
        /*
        * THEN
        */
        verify<AddPodcastView>(view).onError(error)
    }


    @Test
    fun given_Presenter_when_onItemsAvailable_then_viewIsUpdated() {
        /*
        * WHEN
        */
        addPodcastPresenter.onResume()
        verify<AddPodcastsModel>(addPodcastsModel).subscribeToSearch(observerCaptor.capture())
        observerCaptor.value.onNext(result)
        /*
        * THEN
        */
        verify<AddPodcastView>(view).updateSearchResults(list)
    }

    @Test
    fun given_Presenter_when_onCompleted_then_viewIsUpdated() {
        /*
        * WHEN
        */
        addPodcastPresenter.onResume()
        verify<AddPodcastsModel>(addPodcastsModel).subscribeToSearch(observerCaptor.capture())
        observerCaptor.value.onCompleted()
        /*
        * THEN
        */
        verify<AddPodcastView>(view, times(2)).showProgressBar(false)
    }

    @Test
    fun given_Presenter_when_onSaveInstanceState_then_mementoIsUpdated() {
        /*
        * WHEN
        */
        `when`(view.isProgressShowing).thenReturn(true)
        addPodcastPresenter.onSaveInstanceState(memento)
        /*
        * THEN
        */
        verify<AddPodcastFragmentMemento>(memento).isProgressShowing = true
    }

    companion object {
        private val QUERY = "supportedVersionsQuery"
    }


}