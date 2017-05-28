package com.pietrantuono.podcasts.application;

import com.pietrantuono.podcasts.playerview.BottomPlayerView;

import dagger.Subcomponent;

@Subcomponent(modules = PlayerViewModule.class)
public interface PlayerViewComponent {

    void inject(BottomPlayerView bottomPlayerView);
}
