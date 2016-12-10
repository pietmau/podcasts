package com.pietrantuono.podcasts.addpodcast.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;

import org.mockito.Mock;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface SearchApi {

    @GET("search")
    Call<List<PodcastSearchResult>> search(@QueryMap Map<String, String> query);

}
