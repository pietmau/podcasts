package com.pietrantuono.podcasts.subscribedpodcasts.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;

import java.util.List;

public interface SubscribedPodcastView {
    void onError(Throwable throwable);

    void setPodcasts(List<SinglePodcast> list);

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void startDetailActivityWithTransition(SinglePodcast singlePodcast, ImageView imageView, LinearLayout titleContainer);

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void startDetailActivityWithoutTransition(SinglePodcast singlePodcast);

    boolean isPartiallyHidden(int position);
}
