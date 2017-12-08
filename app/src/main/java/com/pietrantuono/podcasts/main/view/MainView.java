package com.pietrantuono.podcasts.main.view;

public interface MainView {

    void navigateToAddPodcast();

    void navigateToSubscribedPodcasts();

    void navigateToSettings();

    void navigateToDownloads();

    void finish();

    void startKillSwitchActivity(int title, int messagge);

    void navigateToPlaylist();
}
