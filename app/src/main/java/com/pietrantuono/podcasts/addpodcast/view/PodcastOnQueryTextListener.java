package com.pietrantuono.podcasts.addpodcast.view;

import android.support.v7.widget.SearchView;

import com.pietrantuono.podcasts.addpodcast.presenter.AddPodcastPresenter;

class PodcastOnQueryTextListener implements SearchView.OnQueryTextListener{
    private AddPodcastPresenter addPodcastPresenter;

    PodcastOnQueryTextListener(AddPodcastPresenter addPodcastPresenter) {
        this.addPodcastPresenter = addPodcastPresenter;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return addPodcastPresenter.onQueryTextSubmit(query);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return addPodcastPresenter.onQueryTextChange(newText);
    }
}
