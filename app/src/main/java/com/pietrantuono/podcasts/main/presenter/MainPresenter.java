package com.pietrantuono.podcasts.main.presenter;

import com.pietrantuono.podcasts.GenericPresenter;
import com.pietrantuono.podcasts.main.view.MainView;

public class MainPresenter implements GenericPresenter {
    private MainView view;

    public void bindView(MainView view) {
        this.view = view;
        view.navigateToSubscribedPodcasts();
    }

    @Override
    public void onDestroy() {
    }

    public void onAddPodcastSelected() {
        view.navigateToAddPodcast();
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onResume() {

    }
}
