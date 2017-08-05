package com.pietrantuono.podcasts.apis;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

public interface SinglePodcastApi {

    @GET
    Observable<PodcastFeed> getFeed(@Url String url);

    @GET
    Call<PodcastFeed> getFeedSync(@Url String url);
}
