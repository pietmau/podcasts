package com.pietrantuono.podcasts.addpodcast.model;

import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;

public class SearchPodcastInteractorImpl implements SearchPodcastInteractor {
    private SearchApi searchApi;

    public SearchPodcastInteractorImpl(SearchApi searchApi) {
        this.searchApi = searchApi;
    }

    @Override
    public void subscribeToSearch(Observer<List<SearchPodcastItem>> observer) {
        Map<String, String> map = new HashMap<>();
        map.put("media","podcast");
        map.put("limit","100");
        map.put("term","aaa");
        searchApi.search(map).enqueue(new Callback<List<PodcastSearchResult>>() {
            @Override
            public void onResponse(Call<List<PodcastSearchResult>> call, Response<List<PodcastSearchResult>> response) {
                foo();
            }

            @Override
            public void onFailure(Call<List<PodcastSearchResult>> call, Throwable t) {
                foo();
            }
        });
    }

    private void foo() {
    }

    @Override
    public void unsubscribeFromSearch() {

    }

    @Override
    public void searchPodcasts(String query) {

    }
}
