package com.pietrantuono.podcasts.addpodcast.model;

import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel;
import com.pietrantuono.podcasts.addpodcast.model.SearchResult;
import com.pietrantuono.podcasts.addpodcast.model.SearchApi;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

public class AddPodcastsModelImpl implements AddPodcastsModel {
    private final SearchApi searchApi;
    Subscription subscription;
    private Observer<SearchResult> observer;
    Observable<SearchResult> cachedRequest;

    public AddPodcastsModelImpl(SearchApi searchApi) {
        this.searchApi = searchApi;
    }

    @Override
    public void subscribeToSearch(Observer<SearchResult> observer) {
        this.observer = observer;
        if (cachedRequest != null) {
            subscription = cachedRequest.subscribe(observer);
        }
    }

    @Override
    public void unsubscribeFromSearch() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void searchPodcasts(String query) {
        cachedRequest = searchApi.search(query);
        subscription = cachedRequest.subscribe(observer);
    }

}
