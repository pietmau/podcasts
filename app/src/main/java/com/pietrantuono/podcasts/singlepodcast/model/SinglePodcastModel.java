package com.pietrantuono.podcasts.singlepodcast.model;


import com.pietrantuono.podcasts.addpodcast.model.SearchResult;

import rx.Observer;

public interface SinglePodcastModel {

    void subscribe(Observer<SearchResult> observer);

    void unsubscribe();
}
