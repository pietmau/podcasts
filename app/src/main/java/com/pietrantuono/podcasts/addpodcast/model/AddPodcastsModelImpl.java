package com.pietrantuono.podcasts.addpodcast.model;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class AddPodcastsModelImpl implements AddPodcastsModel {
    private final SearchApi searchApi;
    Disposable subscription;
    private DisposableObserver<SearchResult> observer;
    Observable<SearchResult> cachedRequest;

    public AddPodcastsModelImpl(SearchApi searchApi) {
        this.searchApi = searchApi;
    }

    @Override
    public void subscribeToSearch(DisposableObserver<SearchResult> observer) {
        this.observer = observer;
        if (cachedRequest != null) {
            subscription = cachedRequest.subscribeWith(observer);
        }
    }

    @Override
    public void unsubscribeFromSearch() {
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }

    @Override
    public void searchPodcasts(String query) {
        cachedRequest = searchApi.search(query);
        subscription = cachedRequest.subscribeWith(observer);
    }

}
