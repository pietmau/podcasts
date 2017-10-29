package com.pietrantuono.podcasts.addpodcast.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast;
import com.pietrantuono.podcasts.addpodcast.model.retrofitconverters.PodcastSearchResultConverterFactory;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchApiRetrofit implements SearchApi {
    private static final String ITUNES = "https://itunes.apple.com";
    private final SearchApi api;

    public SearchApiRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ITUNES)
                .addConverterFactory(new PodcastSearchResultConverterFactory(GsonConverterFactory.create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
