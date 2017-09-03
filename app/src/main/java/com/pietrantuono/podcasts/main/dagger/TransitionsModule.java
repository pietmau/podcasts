package com.pietrantuono.podcasts.main.dagger;

import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker;
import com.pietrantuono.podcasts.main.view.TransitionsHelper;

import dagger.Module;
import dagger.Provides;

@Module
public class TransitionsModule {

    @Provides
    TransitionsHelper provideTransitionsFramework(ApiLevelChecker apiLevelChecker){
        return new TransitionsHelper(apiLevelChecker);
    }

}
