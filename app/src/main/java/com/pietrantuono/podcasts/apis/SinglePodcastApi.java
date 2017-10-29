package com.pietrantuono.podcasts.apis;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface SinglePodcastApi {

    @GET
    Observable<PodcastFeed> getFeed(@Url String url);

    @GET
    Call<PodcastFeed> getFeedSync(@Url String url);
}
