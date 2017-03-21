package com.pietrantuono.podcasts.subscribedpodcasts.presenter;

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.subscribedpodcasts.model.SubscribedPodcastModel;
import com.pietrantuono.podcasts.subscribedpodcasts.view.SubscribedPodcastView;

import java.util.List;

import rx.Observer;

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
       model.subscribeToSubscribedPodcasts(new Observer<List<SinglePodcast>>() {
           @Override
           public void onCompleted() {

           }

           @Override
           public void onError(Throwable throwable) {
               view.onError(throwable);
           }

           @Override
           public void onNext(List<SinglePodcast> list) {
            view.setPodcasts(list);
           }
       });
    }
}
