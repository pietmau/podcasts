package com.pietrantuono.podcasts.addpodcast.model;

import java.util.List;

import rx.Observer;

public class SearchPodcastInteractorImpl implements SearchPodcastInteractor {
    private SearchApi searchApi;

    public SearchPodcastInteractorImpl(SearchApi searchApi) {
        this.searchApi = searchApi;
    }


    @Override
    public void subscribeToSearch(Observer<List<SearchPodcastItem>> observer) {

    }

    @Override
    public void unsubscribeFromSearch() {

    }

    @Override
    public void searchPodcasts(String query) {

    }
}
