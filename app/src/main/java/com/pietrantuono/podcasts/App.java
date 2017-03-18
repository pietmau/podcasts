package com.pietrantuono.podcasts;


import android.app.Application;

import io.realm.Realm;

public class App extends Application {

    @Override
    public void onCreate() {
        Realm.init(this);
    }
}
