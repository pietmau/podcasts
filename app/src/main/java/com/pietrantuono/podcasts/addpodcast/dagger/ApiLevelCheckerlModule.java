package com.pietrantuono.podcasts.addpodcast.dagger;

import android.content.Context;

import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker;
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelCheckerImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiLevelCheckerlModule {

    @Provides
    ApiLevelChecker providesApiLevelChecker(){
        return new ApiLevelCheckerImpl();
    }

}
