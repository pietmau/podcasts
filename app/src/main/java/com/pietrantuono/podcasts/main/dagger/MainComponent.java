package com.pietrantuono.podcasts.main.dagger;

import com.pietrantuono.podcasts.main.view.MainActivity;

import dagger.Component;

@Component (modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity mainActivity);
}
