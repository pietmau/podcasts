package com.pietrantuono.podcasts.singlepodcast.customviews;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pietrantuono.podcasts.BR;
import com.pietrantuono.podcasts.apis.PodcastEpisode;

public class EpisodeHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding dataBinding;
    private PodcastEpisode podcastEpisode;

    public EpisodeHolder(View itemView) {
        super(itemView);
        dataBinding = DataBindingUtil.bind(itemView);
    }

    public void bind(PodcastEpisode podcastEpisode) {
        this.podcastEpisode = podcastEpisode;
        dataBinding.setVariable(BR.episode, podcastEpisode);
        dataBinding.executePendingBindings();
    }
}
