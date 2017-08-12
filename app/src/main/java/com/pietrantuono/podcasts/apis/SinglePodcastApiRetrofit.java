package com.pietrantuono.podcasts.apis;

import com.pietrantuono.podcasts.CrashlyticsWrapper;
import com.pietrantuono.podcasts.interfaces.PodcastEpisodeParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;

public class SinglePodcastApiRetrofit implements SinglePodcastApi {
    private static final String GOOGLE = "http://www.google.com";
    private final SinglePodcastApi api;

    public SinglePodcastApiRetrofit(CrashlyticsWrapper crashlyticsWrapper, PodcastEpisodeParser episodeparser) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GOOGLE)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(new PodcastFeedConverterFactory(crashlyticsWrapper, episodeparser))
                .build();
        api = retrofit.create(SinglePodcastApi.class);
    }

    @Override
    public Observable<PodcastFeed> getFeed(String url) {
        api.getFeedSync(url).enqueue(new Callback<PodcastFeed>() {
            @Override
            public void onResponse(Call<PodcastFeed> call, Response<PodcastFeed> response) {

            }

            @Override
            public void onFailure(Call<PodcastFeed> call, Throwable t) {

            }
        });
        return api.getFeed(url);
    }

    @Override
    public Call<PodcastFeed> getFeedSync(String url) {
        return api.getFeedSync(url);
    }
}
