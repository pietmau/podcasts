package com.pietrantuono.podcasts.addpodcast.presenter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsAdapter;
import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel;
import com.pietrantuono.podcasts.addpodcast.model.SearchResult;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastFragmentMemento;
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastView;
import com.pietrantuono.podcasts.GenericPresenter;
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker;

import rx.Observer;

public class AddPodcastPresenter implements GenericPresenter, PodcastsAdapter.OnSunscribeClickedListener, PodcastsAdapter.OnItemClickedClickedListener {
    public static final String TAG = (AddPodcastPresenter.class).getSimpleName();
    private final AddPodcastsModel addPodcastsModel;
    private final Observer<SearchResult> observer;
    private final ApiLevelChecker apiLevelChecker;
    @Nullable private AddPodcastView addPodcastView;
    private SearchResult cachedResult;


    public AddPodcastPresenter(final AddPodcastsModel addPodcastsModel, ApiLevelChecker apiLevelChecker) {
        this.addPodcastsModel = addPodcastsModel;
        this.apiLevelChecker = apiLevelChecker;
        observer = new Observer<SearchResult>() {

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
            public void onNext(SearchResult result) {
                if (addPodcastView != null && !result.equals(cachedResult)) {
                    cachedResult = result;
                    addPodcastView.updateSearchResults(cachedResult.getList());
                }
            }
        };
    }

    public void bindView(AddPodcastView addPodcastView, AddPodcastFragmentMemento memento) {
        this.addPodcastView = addPodcastView;
        addPodcastView.showProgressBar(memento.isProgressShowing());
        if (cachedResult != null) {
            addPodcastView.updateSearchResults(cachedResult.getList());
        }
    }

    @Override
    public void onDestroy() {
        addPodcastView = null;
    }

    @Override
    public void onPause() {
        addPodcastsModel.unsubscribeFromSearch();
    }

    @Override
    public void onResume() {
        addPodcastsModel.subscribeToSearch(observer);
    }

    public boolean onQueryTextSubmit(String query) {
        launchQuery(query);
        return true;
    }

    private void launchQuery(String query) {
        addPodcastsModel.searchPodcasts(query);
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
    public void onSubscribeClicked(SinglePodcast singlePodcast) {
    }

    @Override
    public void onItemClicked(SinglePodcast singlePodcast, ImageView imageView, int position) {
        if (apiLevelChecker.isLollipopOrHigher() && !addPodcastView.isPartiallyHidden(position)) {
            addPodcastView.startDetailActivityWithTransition(singlePodcast, imageView);
        } else {
            addPodcastView.startDetailActivityWithoutTransition(singlePodcast);
        }
    }
}
