package com.pietrantuono.podcasts.addpodcast.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;


public interface SearchApi {

    @GET("search")
    Observable<List<Podcast>> search(@QueryMap Map<String, String> query);

    Observable<SearchResult> search(String query);
}
