package com.pietrantuono.podcasts.subscribedpodcasts.presenter;

import com.pietrantuono.podcasts.subscribedpodcasts.model.SubscribedPodcastModel;
import com.pietrantuono.podcasts.subscribedpodcasts.view.SubscribedPodcastView;

public class SubscribedPodcastPresenter {
    private SubscribedPodcastView view;
    private final SubscribedPodcastModel model;

    public SubscribedPodcastPresenter(SubscribedPodcastModel model) {
        this.model = model;
    }

    public void bindView(SubscribedPodcastView view) {
        this.view = view;
    }

    public void onResume() {
       // model.subscribeToSubscribedPodcasts();
    }
}
