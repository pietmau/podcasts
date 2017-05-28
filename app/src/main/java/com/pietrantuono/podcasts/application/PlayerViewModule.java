package com.pietrantuono.podcasts.application;

import com.pietrantuono.podcasts.playerview.BottomPlayerViewPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class PlayerViewModule {

    @Provides
    BottomPlayerViewPresenter provideBottomPlayerViewPresenter(){
        return new BottomPlayerViewPresenter();
    }
}
