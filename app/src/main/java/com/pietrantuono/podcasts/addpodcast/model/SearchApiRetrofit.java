package com.pietrantuono.podcasts.addpodcast.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast;
import com.pietrantuono.podcasts.addpodcast.model.retrofitconverters.PodcastSearchResultConverterFactory;

import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchApiRetrofit implements SearchApi {
    private static final String ITUNES = "https://itunes.apple.com";
    private final SearchApi api;

    public SearchApiRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ITUNES)
                .addConverterFactory(new PodcastSearchResultConverterFactory(GsonConverterFactory.create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        api = retrofit.create(SearchApi.class);
    }

    @Override
    public Observable<List<Podcast>> search(@retrofit2.http.QueryMap Map<String, String> query) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Observable<SearchResult> search(String query) {
        QueryMap map = new QueryMap(query);
        return api.search(map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map(list->new SearchResult(list, query)).cache();
    }

}
