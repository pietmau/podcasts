package com.pietrantuono.podcasts.addpodcast.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.addpodcast.model.retrofitconverters.PodcastSearchResultConverterFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    public Observable<List<SinglePodcast>> search(@retrofit2.http.QueryMap Map<String, String> query) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Observable<List<SinglePodcast>> search(String query) {
        QueryMap map = new QueryMap(query);
        return api.search(map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).cache();
    }

    private static class QueryMap extends HashMap<String, String> {
        public QueryMap(String query) {
            put("media", "podcast");
            put("limit", "100");
            put("term", query);
        }
    }
}
