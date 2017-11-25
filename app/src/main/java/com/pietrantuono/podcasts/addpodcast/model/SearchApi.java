package com.pietrantuono.podcasts.addpodcast.model;

import java.util.List;
import java.util.Map;

import pojos.Podcast;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface SearchApi {

    @GET("search")
    Observable<List<Podcast>> search(@QueryMap Map<String, String> query);

    Observable<SearchResult> search(String query);
}
