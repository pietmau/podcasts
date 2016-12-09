package com.pietrantuono.podcasts.addpodcast.presenter;

import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel;
import com.pietrantuono.podcasts.addpodcast.model.SearchPodcastItem;
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastView;
import com.pietrantuono.podcasts.main.model.MainModel;
import com.pietrantuono.podcasts.GenericPresenter;

import java.util.List;

import rx.Observer;

public class AddPodcastPresenter implements GenericPresenter {
    private AddPodcastsModel mainModel;
    private Observer<List<SearchPodcastItem>> observer;
    private AddPodcastView addPodcastView;

    public AddPodcastPresenter() {
        observer = new Observer<List<SearchPodcastItem>>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) {
                addPodcastView.onError(e);
            }

            @Override
            public void onNext(List<SearchPodcastItem> items) {
                addPodcastView.updateSearchResults(items);
            }
        };
    }

    public void bindView(AddPodcastView addPodcastView){
        this.addPodcastView = addPodcastView;
    }

    public void onModelConnected(AddPodcastsModel mainModel) {
        this.mainModel = mainModel;
        mainModel.subscribeToSearch(observer);
    }

    @Override
    public void onDestroy() { }

    @Override
    public void onModelDisconnected() {
        mainModel = null;
    }

    @Override
    public void onPause() {
        mainModel.unsubscribeFromSearch();
    }

    @Override
    public void onResume() { }

    public boolean onQueryTextSubmit(String query) {
        launchQuery(query);
        return true;
    }

    private void launchQuery(String query) {
        mainModel.searchPodcasts(query);
    }

    public boolean onQueryTextChange(String newText) {
        return true;
    }
}
