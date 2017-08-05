package com.pietrantuono.podcasts.addpodcast.repository.repository;

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import rx.Observable;
import rx.Observer;

public interface Repository {
    Observable<Boolean> getIfSubscribed(Podcast trackId);

    Observable<List<Podcast>> getSubscribedPodcasts(Observer<List<Podcast>> observer);

    void subscribeUnsubscribeToPodcast(Podcast podcast);

    @NotNull Observable<? extends Podcast> getPodcastById(int trackId);
}
