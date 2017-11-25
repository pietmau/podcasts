package com.pietrantuono.podcasts.subscribedpodcasts.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import pojos.Podcast;

public interface SubscribedPodcastView {
    void onError(Throwable throwable);

    void setPodcasts(List<Podcast> list);

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void startDetailActivityWithTransition(Podcast podcast, ImageView imageView, LinearLayout titleContainer);

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void startDetailActivityWithoutTransition(Podcast podcast);

    boolean isPartiallyHidden(int position);
}
