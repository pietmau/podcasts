package com.pietrantuono.podcasts.singlepodcast.presenter;


import com.pietrantuono.CrashlyticsWrapper;
import com.pietrantuono.podcasts.GenericPresenter;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.apis.PodcastFeed;
import com.pietrantuono.podcasts.singlepodcast.model.SinglePodcastModel;
import com.pietrantuono.podcasts.singlepodcast.view.SinglePodcastView;

import rx.Observer;

public class SinglePodcastPresenter implements GenericPresenter {
    public static final String TAG = (SinglePodcastPresenter.class).getSimpleName();
    private SinglePodcastView singlePodcastView;
    private final SinglePodcastModel model;
    private PodcastFeed podcastFeed;
    private final CrashlyticsWrapper crashlyticsWrapper;
    private boolean startedWithTransition;

    public SinglePodcastPresenter(SinglePodcastModel model, CrashlyticsWrapper crashlyticsWrapper) {
        this.model = model;
        this.crashlyticsWrapper = crashlyticsWrapper;
    }

    @Override
    public void onDestroy() {
        singlePodcastView = null;
    }

    @Override
    public void onPause() {
        model.unsubscribe();
    }

    @Override
    public void onResume() {
        model.subscribe(new Observer<PodcastFeed>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                crashlyticsWrapper.logException(e);
            }

            @Override
            public void onNext(PodcastFeed podcastFeed) {
                if (SinglePodcastPresenter.this.podcastFeed == null) {
                    SinglePodcastPresenter.this.podcastFeed = podcastFeed;
                    setEpisodes();
                }
            }
        });
    }

    public void bindView(SinglePodcastView view) {
        singlePodcastView = view;
        setEpisodes();
    }

    public void init(SinglePodcast podcast, boolean startedWithTransition) {
        this.startedWithTransition = startedWithTransition;
        if (podcast != null) {
            model.getFeed(podcast.getFeedUrl());
        }
        if (startedWithTransition) {
            singlePodcastView.enterWithTransition();
        } else {
            singlePodcastView.enterWithoutTransition();
        }
    }

    private void setEpisodes() {
        if (singlePodcastView == null || podcastFeed == null) {
            return;
        }
        singlePodcastView.setEpisodes(podcastFeed.getEpisodes());
    }

    public void onBackPressed() {
        if(startedWithTransition){
            singlePodcastView.exitWithSharedTrsnsition();
        }
        else {
            singlePodcastView.exitWithoutSharedTransition();
        }
    }
}
