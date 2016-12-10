package com.pietrantuono.podcasts.addpodcast.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchPodcastInteractorImpl implements SearchPodcastInteractor {
    private SearchApi searchApi;
    private Subscription subscription;
    private Observer<List<PodcastSearchResult>> observer;
    private Observable<List<PodcastSearchResult>> observable;

    public SearchPodcastInteractorImpl(SearchApi searchApi) {
        this.searchApi = searchApi;
    }

    @Override
    public void subscribeToSearch(Observer<List<PodcastSearchResult>> observer) {
        this.observer = observer;
        if (observable != null) {
            subscription = observable.subscribe(observer);
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
        observable = searchApi.search(query);
        subscription = observable.subscribe(observer);
    }
}
