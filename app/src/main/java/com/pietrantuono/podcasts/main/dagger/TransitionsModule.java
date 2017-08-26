package com.pietrantuono.podcasts.main.dagger;

import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker;
import com.pietrantuono.podcasts.main.view.TransitionsFramework;

import dagger.Module;
import dagger.Provides;

@Module
public class TransitionsModule {

    @Provides
    TransitionsFramework provideTransitionsFramework(ApiLevelChecker apiLevelChecker){
        return new TransitionsFramework(apiLevelChecker);
    }

}
