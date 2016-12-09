package com.pietrantuono.podcasts;

import com.pietrantuono.podcasts.main.model.MainModel;

public interface GenericPresenter {

    void onDestroy();

    void onModelDisconnected();

    void onPause();

    void onResume();
}
