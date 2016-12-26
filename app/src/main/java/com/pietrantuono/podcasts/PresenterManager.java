package com.pietrantuono.podcasts;


import com.pietrantuono.podcasts.addpodcast.presenter.AddPodcastPresenter;

import java.util.HashMap;
import java.util.Map;

public class PresenterManager {
    private final Map<String, GenericPresenter> map;

    public PresenterManager() {
        map = new HashMap<>();
    }

    public GenericPresenter getPresenter(String tag) {
        return map.get(tag);
    }

    public void put(String tag, GenericPresenter presenter) {
        map.put(tag, presenter);
    }

}
