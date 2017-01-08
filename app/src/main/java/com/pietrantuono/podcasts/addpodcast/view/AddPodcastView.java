package com.pietrantuono.podcasts.addpodcast.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.widget.ImageView;

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;

import java.util.List;

public interface AddPodcastView {
    void onError(Throwable e);

    void updateSearchResults(List<SinglePodcast> items);

    void showProgressBar(boolean show);

    boolean isProgressShowing();

    void onQueryTextChange(String newText);

    void startDetailActivityWithTransition(SinglePodcast singlePodcast, ImageView imageView);

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void startDetailActivityWithoutTransition(SinglePodcast singlePodcast);


    boolean isPartiallyHidden(int imageView);
}
