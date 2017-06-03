package com.pietrantuono.podcasts.application;

import android.content.Context;

import com.pietrantuono.podcasts.player.MediaSessionCompatWrapper;
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

    @Provides
    MediaSessionCompatWrapper provideMediaSessionCompatWrapper(Context context){
        return new MediaSessionCompatWrapper(context);
    }
}
