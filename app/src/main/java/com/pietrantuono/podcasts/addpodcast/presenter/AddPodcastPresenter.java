package com.pietrantuono.podcasts.addpodcast.presenter;

import android.support.annotation.Nullable;

import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsAdapter;
import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel;
import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastFragmentMemento;
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastView;
import com.pietrantuono.podcasts.GenericPresenter;

import java.util.List;

import rx.Observer;

public class AddPodcastPresenter implements GenericPresenter, PodcastsAdapter.OnSunscribeClickedListener, PodcastsAdapter.OnItemClickedClickedListener {
    private AddPodcastsModel addPodcastsModel;
    private final Observer<List<PodcastSearchResult>> observer;
    @Nullable private AddPodcastView addPodcastView;

    public AddPodcastPresenter() {
        observer = new Observer<List<PodcastSearchResult>>() {
            @Override
            public void onCompleted() {
                showProgressBar(false);
            }

            @Override
            public void onError(Throwable e) {
                if (addPodcastView != null) {
                    addPodcastView.onError(e);
                }
                showProgressBar(false);
            }

            @Override
            public void onNext(List<PodcastSearchResult> items) {
                if (addPodcastView != null) {
                    addPodcastView.updateSearchResults(items);
                }
            }
        };
    }

    public void bindView(AddPodcastView addPodcastView, AddPodcastFragmentMemento memento) {
        this.addPodcastView = addPodcastView;
        addPodcastView.showProgressBar(memento.isProgressShowing());
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
    public void onResume() {
    }

    public boolean onQueryTextSubmit(String query) {
        launchQuery(query);
        return true;
    }

    private void launchQuery(String query) {
        if (addPodcastsModel != null) {
            addPodcastsModel.searchPodcasts(query);
        }
        showProgressBar(true);
    }

    public boolean onQueryTextChange(String newText) {
        addPodcastView.onQueryTextChange(newText);
        return true;
    }

    public void onSaveInstanceState(AddPodcastFragmentMemento memento) {
        memento.setProgressShowing(addPodcastView.isProgressShowing());
    }

    private void showProgressBar(boolean show) {
        if (addPodcastView != null) {
            addPodcastView.showProgressBar(show);
        }
    }

    @Override
    public void onSunscribeClicked(PodcastSearchResult podcastSearchResult) {

    }

    @Override
    public void onItemClicked(PodcastSearchResult podcastSearchResult) {
        if(addPodcastView.isLollipop()){
            addPodcastView.startDetailActivityWithTransition(podcastSearchResult);
        }
        else{addPodcastView.startDetailActivityWithOutTransition(podcastSearchResult);}
    }
}
