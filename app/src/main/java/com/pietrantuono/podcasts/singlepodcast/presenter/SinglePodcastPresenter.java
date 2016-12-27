package com.pietrantuono.podcasts.singlepodcast.presenter;


import com.pietrantuono.podcasts.GenericPresenter;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.apis.SinglePodcastApi;
import com.pietrantuono.podcasts.singlepodcast.model.SinglePodcastModel;
import com.pietrantuono.podcasts.singlepodcast.view.SinglePodcastView;

public class SinglePodcastPresenter implements GenericPresenter {
    public static final String TAG = (SinglePodcastPresenter.class).getSimpleName();
    private SinglePodcastView singlePodcastView;
    private SinglePodcastModel model;

    public SinglePodcastPresenter(SinglePodcastModel model) {
        this.model = model;
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
        model.subscribe(null);
    }

    public void bindView(SinglePodcastView view) {
        singlePodcastView = view;
    }

    public void setPodcast(SinglePodcast podcast) {

    }
}
