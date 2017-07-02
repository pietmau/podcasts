package com.pietrantuono.podcasts.singlepodcast.customviews;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pietrantuono.podcasts.BR;
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;
import com.pietrantuono.podcasts.singlepodcast.viewmodel.PodcastEpisodeViewModel;
import com.pietrantuono.podcasts.singlepodcast.viewmodel.ResourcesProvider;

public class EpisodeHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding dataBinding;
    private final ResourcesProvider resourcesProvider;

    public EpisodeHolder(View itemView, ResourcesProvider resourcesProvider) {
        super(itemView);
        dataBinding = DataBindingUtil.bind(itemView);
        this.resourcesProvider = resourcesProvider;
    }

    public void bind(PodcastEpisodeModel podcastEpisodeModel) {
        PodcastEpisodeViewModel podcastEpisodeViewModel = new PodcastEpisodeViewModel(podcastEpisodeModel, resourcesProvider);
        dataBinding.setVariable(BR.viewModel, podcastEpisodeViewModel);
        dataBinding.executePendingBindings();
    }
}
