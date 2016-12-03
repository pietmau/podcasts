package com.pietrantuono.podcasts.main.presenter;

import com.pietrantuono.podcasts.main.view.MainView;
import com.pietrantuono.podcasts.main.model.MainModel;

/**
 * Created by Maurizio Pietrantuono, maurizio.pietrantuono@gmail.com
 */

public class MainPresenter {
    private MainView view;
    private MainModel mainModel;

    public void onCreate(MainView view) {
        this.view = view;
    }

    public void onModelConnected(MainModel mainModel) {
        this.mainModel = mainModel;
    }

    public void onDestroy() {
    }

    public void onModelDisconnected() {
    }

    public void onAddPodcastSelected() {
        view.navigateToAddPodcast();
    }
}
