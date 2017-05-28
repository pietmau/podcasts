package com.pietrantuono.podcasts.application;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;

@Singleton
@Module
public class AppModule {
    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }


}
