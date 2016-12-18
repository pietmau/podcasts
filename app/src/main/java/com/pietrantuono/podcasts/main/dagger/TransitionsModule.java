package com.pietrantuono.podcasts.main.dagger;

import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker;
import com.pietrantuono.podcasts.main.view.TransitionsFramework;
import com.pietrantuono.podcasts.main.view.TransitionsFrameworkImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class TransitionsModule {

    @Provides
    TransitionsFramework provideTransitionsFramework(ApiLevelChecker apiLevelChecker){
        return new TransitionsFrameworkImpl(apiLevelChecker);
    }

}
