package com.pietrantuono.interfaceadapters.apis;

import com.pietrantuono.CrashlyticsWrapper;
import com.pietrantuono.interfaceadapters.apis.SinglePodcastApi;
import com.pietrantuono.podcasts.apis.PodcastFeed;
import com.pietrantuono.podcasts.apis.PodcastFeedConverterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;

public class SinglePodcastApiRetrofit implements SinglePodcastApi {
    private static final String GOOGLE = "http://www.google.com";
    private final SinglePodcastApi api;

    public SinglePodcastApiRetrofit(CrashlyticsWrapper crashlyticsWrapper) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GOOGLE)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(new PodcastFeedConverterFactory(crashlyticsWrapper))
                .build();
        api = retrofit.create(SinglePodcastApi.class);
    }

    @Override
    public Observable<PodcastFeed> getFeed(String url) {
        return api.getFeed(url);
    }
}
