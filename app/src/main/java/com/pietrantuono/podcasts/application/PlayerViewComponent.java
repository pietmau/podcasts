package com.pietrantuono.podcasts.application;

import android.support.annotation.NonNull;

import com.pietrantuono.podcasts.player.MediaPlaybackService;
import com.pietrantuono.podcasts.playerview.BottomPlayerView;

import org.jetbrains.annotations.NotNull;

import dagger.Subcomponent;

@Subcomponent(modules = PlayerViewModule.class)
public interface PlayerViewComponent {

    void inject(@NonNull BottomPlayerView bottomPlayerView);

    void inject(@NotNull MediaPlaybackService mediaPlaybackService);
}
