package com.pietrantuono.podcasts.apis;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;

public class SinglePodcastApiRetrofit implements SinglePodcastApi {
    private static final String GOOGLE = "http://www.google.com";
    private final SinglePodcastApi api;

    public SinglePodcastApiRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GOOGLE)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(new PodcastFeedConverterFactory())
                .build();
        api = retrofit.create(SinglePodcastApi.class);
    }

    @Override
    public Observer<PodcastFeed> getFeed(String url) {
        return api.getFeed(url);
    }


}
