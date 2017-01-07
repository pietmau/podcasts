package com.pietrantuono.podcasts.singlepodcast.customviews;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pietrantuono.podcasts.BR;
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;
import com.pietrantuono.podcasts.singlepodcast.viewmodel.PodcastEpisodeViewModel;
import com.pietrantuono.podcasts.singlepodcast.viewmodel.ResourcesProvider;

public class EpisodeHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding dataBinding;
    private PodcastEpisodeViewModel podcastEpisodeViewModel;
    private final SimpleImageLoader simpleImageLoader;
    private final ResourcesProvider resourcesProvider;

    public EpisodeHolder(View itemView, SimpleImageLoader simpleImageLoader, ResourcesProvider resourcesProvider) {
        super(itemView);
        dataBinding = DataBindingUtil.bind(itemView);
        this.simpleImageLoader = simpleImageLoader;
        this.resourcesProvider = resourcesProvider;
    }

    public void bind(PodcastEpisodeModel podcastEpisodeModel) {
        this.podcastEpisodeViewModel = new PodcastEpisodeViewModel(podcastEpisodeModel, simpleImageLoader, resourcesProvider);
        dataBinding.setVariable(BR.viewModel, podcastEpisodeViewModel);
        dataBinding.executePendingBindings();
    }
}
