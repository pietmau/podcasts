package com.pietrantuono.podcasts.application;


import android.app.Application;

import io.realm.Realm;

public class App extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        Realm.init(this);
        applicationComponent = DaggerApplicationComponent.builder().appModule(new AppModule(this)).build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
