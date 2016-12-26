package com.pietrantuono.podcasts.main.presenter;

import com.pietrantuono.podcasts.GenericPresenter;
import com.pietrantuono.podcasts.main.view.MainView;
import com.pietrantuono.podcasts.main.model.MainModel;

/**
 * Created by Maurizio Pietrantuono, maurizio.pietrantuono@gmail.com
 */

public class MainPresenter implements GenericPresenter {
    private MainView view;
    private MainModel mainModel;

    public void bindView(MainView view) {
        this.view = view;
    }

    public void onModelConnected(MainModel mainModel) {
        this.mainModel = mainModel;
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
