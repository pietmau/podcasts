package com.pietrantuono.podcasts.main.model.interactor;

import com.pietrantuono.podcasts.addpodcast.model.SearchPodcastInteractor;
import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;

import java.util.List;

import rx.Observer;

public class ModelInteractorImpl implements ModelInteractor {
    private SearchPodcastInteractor searchPodcastInteractor;

    public ModelInteractorImpl(SearchPodcastInteractor searchPodcastInteractor) {
        this.searchPodcastInteractor = searchPodcastInteractor;
    }

    @Override
    public void subscribeToSearch(Observer<List<PodcastSearchResult>> observer) {
        searchPodcastInteractor.subscribeToSearch(observer);
    }

    @Override
    public void unsubscribeFromSearch() {
        searchPodcastInteractor.unsubscribeFromSearch();
    }

    @Override
    public void searchPodcasts(String query) {
        searchPodcastInteractor.searchPodcasts(query);
    }
}
