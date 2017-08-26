package com.pietrantuono.podcasts.main.dagger;

import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker;
import com.pietrantuono.podcasts.main.view.Transitions;

import dagger.Module;
import dagger.Provides;

@Module
public class TransitionsModule {

    @Provides
    Transitions provideTransitionsFramework(ApiLevelChecker apiLevelChecker){
        return new Transitions(apiLevelChecker);
    }

}
