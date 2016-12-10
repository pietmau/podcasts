package com.pietrantuono.podcasts.addpodcast.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;
import com.pietrantuono.podcasts.addpodcast.model.retrofitconverters.PodcastSearchResultConverterFactory;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.QueryMap;

public class SearchApiRetrofit implements SearchApi {
    public static final String ITUNES = "https://itunes.apple.com";
    private final SearchApi api;

    public SearchApiRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ITUNES)
                .addConverterFactory(new PodcastSearchResultConverterFactory(GsonConverterFactory.create()))
                .build();

        api = retrofit.create(SearchApi.class);
    }

    @Override
    public Call<List<PodcastSearchResult>> search(@QueryMap Map<String, String> query) {
        return api.search(query);
    }
}
