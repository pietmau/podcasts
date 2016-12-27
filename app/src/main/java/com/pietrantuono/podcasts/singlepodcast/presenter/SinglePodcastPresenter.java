package com.pietrantuono.podcasts.singlepodcast.presenter;


import com.pietrantuono.podcasts.GenericPresenter;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.singlepodcast.view.SinglePodcastView;

public class SinglePodcastPresenter implements GenericPresenter {
    public static final String TAG = (SinglePodcastPresenter.class).getSimpleName();
    private SinglePodcastView singlePodcastView;
    private SinglePodcastApi api;

    public SinglePodcastPresenter(SinglePodcastApi api) {
        this.api = api;
    }

    @Override
    public void onDestroy() {
        singlePodcastView = null;
    }

    @Override
    public void onPause() {
        api.unsubscribe();
    }

    @Override
    public void onResume() {

    }

    public void bindView(SinglePodcastView view) {
        singlePodcastView = view;
    }

    public void setPodcast(SinglePodcast podcast) {

    }
}
