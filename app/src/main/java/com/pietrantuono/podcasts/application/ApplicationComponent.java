package com.pietrantuono.podcasts.application;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface ApplicationComponent {

    void inject(App app);

    PlayerViewComponent with(PlayerViewModule playerViewComponent);

}
