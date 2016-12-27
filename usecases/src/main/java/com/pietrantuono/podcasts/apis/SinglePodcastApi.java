package com.pietrantuono.podcasts.apis;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;
import rx.Observer;

public interface SinglePodcastApi {

    @GET
    Observable<PodcastFeed> getFeed(@Url String url);

}
