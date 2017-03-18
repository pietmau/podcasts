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
    private SinglePodcastView view;
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
        view = null;
    }

    @Override
    public void onPause() {
        model.unsubscribe();
    }

    @Override
    public void onResume() {
        model.subscribeToFeed(new Observer<PodcastFeed>() {
            @Override
            public void onCompleted() {
                view.showProgress(false);
            }

            @Override
            public void onError(Throwable e) {
                crashlyticsWrapper.logException(e);
                view.showProgress(false);
            }

            @Override
            public void onNext(PodcastFeed podcastFeed) {
                if (SinglePodcastPresenter.this.podcastFeed == null) {
                    SinglePodcastPresenter.this.podcastFeed = podcastFeed;
                    setEpisodes();
                }
            }
        });
        model.subscribeToIsSubscribedToPodcast(new SimpleObserver<Boolean>() {
            @Override
            public void onNext(Boolean isSubscribedToPodcast) {
                view.setSubscribedToPodcast(isSubscribedToPodcast);
                model.setSubscribedToPodcast(true);
            }
        });
    }

    public void bindView(SinglePodcastView view) {
        this.view = view;
        setEpisodes();
    }

    public void init(SinglePodcast podcast, boolean startedWithTransition) {
        this.startedWithTransition = startedWithTransition;
        model.init(podcast);
        if (startedWithTransition) {
            view.enterWithTransition();
        } else {
            view.enterWithoutTransition();
        }
    }

    private void setEpisodes() {
        if (view == null || podcastFeed == null) {
            return;
        }
        view.setEpisodes(podcastFeed.getEpisodes());
    }

    public void onBackPressed() {
        if (startedWithTransition) {
            view.exitWithSharedTrsnsition();
        } else {
            view.exitWithoutSharedTransition();
        }
    }

    public void onSubscribeUnsubscribeToPodcastClicked() {
        if (model.isSubscribedToPodcasat()) {
        } else {
        }
    }
}
