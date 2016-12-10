package com.pietrantuono.podcasts.addpodcast.presenter;

import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel;
import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastView;
import com.pietrantuono.podcasts.GenericPresenter;

import java.util.List;

import rx.Observer;

public class AddPodcastPresenter implements GenericPresenter {
    private AddPodcastsModel addPodcastsModel;
    private Observer<List<PodcastSearchResult>> observer;
    private AddPodcastView addPodcastView;

    public AddPodcastPresenter() {
        observer = new Observer<List<PodcastSearchResult>>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                addPodcastView.onError(e);
            }

            @Override
            public void onNext(List<PodcastSearchResult> items) {
                addPodcastView.updateSearchResults(items);
            }
        };
    }

    public void bindView(AddPodcastView addPodcastView){
        this.addPodcastView = addPodcastView;
    }

    public void onModelConnected(AddPodcastsModel mainModel) {
        this.addPodcastsModel = mainModel;
        mainModel.subscribeToSearch(observer);
    }

    @Override
    public void onDestroy() {
        addPodcastView = null;
    }

    @Override
    public void onModelDisconnected() {
        addPodcastsModel = null;
    }

    @Override
    public void onPause() {
        addPodcastsModel.unsubscribeFromSearch();
    }

    @Override
    public void onResume() { }

    public boolean onQueryTextSubmit(String query) {
        launchQuery(query);
        return true;
    }

    private void launchQuery(String query) {
        addPodcastsModel.searchPodcasts(query);
    }

    public boolean onQueryTextChange(String newText) {
        return true;
    }
}
