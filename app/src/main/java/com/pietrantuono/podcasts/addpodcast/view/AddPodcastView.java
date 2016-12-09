package com.pietrantuono.podcasts.addpodcast.view;

import com.pietrantuono.podcasts.addpodcast.model.SearchPodcastItem;

import java.util.List;

public interface AddPodcastView {
    void onError(Throwable e);

    void updateSearchResults(List<SearchPodcastItem> items);
}
