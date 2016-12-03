package com.pietrantuono.podcasts.main;

import com.pietrantuono.podcasts.main.model.MainModel;

/**
 * Created by Maurizio Pietrantuono, maurizio.pietrantuono@gmail.com
 */

public class MainPresenter {
    private MainView view;
    private MainModel mainModel;

    public void OnCreate(MainView view) {
        this.view = view;
    }

    public void onModelConnected(MainModel mainModel) {

    }

    public void onDestroy() {


    }

    public void onModelDisconnected() {

    }
}
