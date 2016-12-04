package com.pietrantuono.podcasts.main.presenter;

import com.pietrantuono.podcasts.main.view.MainView;
import com.pietrantuono.podcasts.main.model.MainModel;

/**
 * Created by Maurizio Pietrantuono, maurizio.pietrantuono@gmail.com
 */

public class MainPresenter implements Presenter {
    private MainView view;
    private MainModel mainModel;

    public void onCreate(MainView view) {
        this.view = view;
    }

    @Override
    public void onModelConnected(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onModelDisconnected() {
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
