package com.pietrantuono.podcasts.main.presenter;

import com.pietrantuono.podcasts.main.model.MainModel;

public interface Presenter {
    void onModelConnected(MainModel mainModel);

    void onDestroy();

    void onModelDisconnected();

    void onPause();

    void onResume();
}
