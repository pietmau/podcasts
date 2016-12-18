package com.pietrantuono.podcasts.addpodcast.dagger;

import android.content.Context;

import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker;
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelCheckerImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Provides
    ApiLevelChecker providesApiLevelChecker(Context context){
        return new ApiLevelCheckerImpl(context);
    }

}
