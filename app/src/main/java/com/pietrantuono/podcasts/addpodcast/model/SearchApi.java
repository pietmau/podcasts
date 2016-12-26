package com.pietrantuono.podcasts.addpodcast.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface SearchApi {

    @GET("search")
    Observable<List<SinglePodcast>> search(@QueryMap Map<String, String> query);

    Observable<List<SinglePodcast>> search(String query);
}
