package com.pietrantuono.podcasts.addpodcast.view;

import android.widget.ImageView;

import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;

import java.util.List;

public interface AddPodcastView {
    void onError(Throwable e);

    void updateSearchResults(List<PodcastSearchResult> items);

    void showProgressBar(boolean show);

    boolean isProgressShowing();

    void onQueryTextChange(String newText);

    void startDetailActivityWithTransition(PodcastSearchResult podcastSearchResult, ImageView imageView);

    boolean isLollipop();

    void startDetailActivityWithOutTransition(PodcastSearchResult podcastSearchResult);
}
