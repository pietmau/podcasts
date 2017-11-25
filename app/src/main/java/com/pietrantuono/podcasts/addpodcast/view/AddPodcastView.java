package com.pietrantuono.podcasts.addpodcast.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import pojos.Podcast;

public interface AddPodcastView {
    void onError(Throwable €e);

    void updateSearchResults(List<Podcast> items);

    void showProgressBar(boolean show);

    boolean isProgressShowing();

    void startDetailActivityWithTransition(Podcast podcast, ImageView imageView, LinearLayout titleContainer);

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void startDetailActivityWithoutTransition(Podcast podcast);


    boolean isPartiallyHidden(int imageView);
}
