package com.pietrantuono.podcasts.apis;

import com.pietrantuono.podcasts.CrashlyticsWrapper;
import com.pietrantuono.podcasts.interfaces.PodcastEpisodeParser;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


public class SinglePodcastApiRetrofit implements SinglePodcastApi {
    private static final String GOOGLE = "http://www.google.com";
    private final SinglePodcastApi api;

    public SinglePodcastApiRetrofit(CrashlyticsWrapper crashlyticsWrapper, PodcastEpisodeParser episodeparser) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GOOGLE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new PodcastFeedConverterFactory(crashlyticsWrapper, episodeparser))
                .build();
        api = retrofit.create(SinglePodcastApi.class);
    }

    @Override
    public Observable<PodcastFeed> getFeed(String url) {
        return api.getFeed(url);
    }

    @Override
    public Call<PodcastFeed> getFeedSync(String url) {
        return api.getFeedSync(url);
    }
}
