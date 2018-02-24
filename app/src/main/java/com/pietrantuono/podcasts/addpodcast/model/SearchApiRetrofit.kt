package com.pietrantuono.podcasts.addpodcast.model


import com.pietrantuono.podcasts.addpodcast.model.retrofitconverters.PodcastSearchResultConverterFactory

import models.pojos.Podcast
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SearchApiRetrofit : SearchApi {
    private val api: SearchApi

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(ITUNES)
                .addConverterFactory(PodcastSearchResultConverterFactory(GsonConverterFactory.create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        api = retrofit.create(SearchApi::class.java)
    }

    override fun search(@retrofit2.http.QueryMap query: Map<String, String>): Observable<List<Podcast>> {
        throw UnsupportedOperationException("Not implemented")
    }

    override fun search(query: String): Observable<SearchResult> {
        val map = QueryMap(query)
        return api.search(map).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).map { list -> SearchResult(list, query) }.cache()
    }

    companion object {
        private val ITUNES = "https://itunes.apple.com"
    }

}
