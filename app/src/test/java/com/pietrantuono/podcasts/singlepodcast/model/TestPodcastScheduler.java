package com.pietrantuono.podcasts.singlepodcast.model;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class TestPodcastScheduler extends PodcastSchedulers {

    @Override
    public Scheduler mainThread() {
        return Schedulers.immediate();
    }

    @Override
    public Scheduler newThread() {
        return Schedulers.immediate();
    }
}
