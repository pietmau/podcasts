package com.pietrantuono.podcasts.subscribedpodcasts.view;

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;

import java.util.List;

public interface SubscribedPodcastView {
    void onError(Throwable throwable);

    void setPodcasts(List<SinglePodcast> list);
}
