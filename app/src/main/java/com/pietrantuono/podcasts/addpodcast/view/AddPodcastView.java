package com.pietrantuono.podcasts.addpodcast.view;

import android.annotation.TargetApi;
import android.os.Build;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast;

import java.util.List;

public interface AddPodcastView {
    void onError(Throwable €e);

    void updateSearchResults(List<Podcast> items);

    void showProgressBar(boolean show);

    boolean isProgressShowing();

    void onQueryTextChange(String newText);

    void startDetailActivityWithTransition(Podcast podcast, ImageView imageView, LinearLayout titleContainer);

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void startDetailActivityWithoutTransition(Podcast podcast);


    boolean isPartiallyHidden(int imageView);
}
