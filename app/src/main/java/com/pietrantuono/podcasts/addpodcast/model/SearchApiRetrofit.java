package com.pietrantuono.podcasts.addpodcast.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;
import com.pietrantuono.podcasts.addpodcast.model.retrofitconverters.PodcastSearchResultConverterFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.QueryMap;
import rx.Observable;
import rx.Observer;

public class SearchApiRetrofit implements SearchApi {
    public static final String ITUNES = "https://itunes.apple.com";
    private final SearchApi api;

    public SearchApiRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ITUNES)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(new PodcastSearchResultConverterFactory(GsonConverterFactory.create()))
                .build();

        api = retrofit.create(SearchApi.class);
    }

    @Override
    public Observable<List<PodcastSearchResult>> search(@QueryMap Map<String, String> query) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Observable<List<PodcastSearchResult>> search(String query) {
        Map<String, String> map = new HashMap<>();
        map.put("media","podcast");
        map.put("limit","100");
        map.put("term",query);
        return api.search(map);
    }
}
