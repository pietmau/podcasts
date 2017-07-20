package com.pietrantuono.podcasts.addpodcast.singlepodcast.model;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PodcastSchedulers {
    
    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    public Scheduler newThread() {
        return Schedulers.newThread();
    }
}
