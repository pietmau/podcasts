package com.pietrantuono.podcasts.singlepodcast.customviews;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pietrantuono.podcasts.BR;
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel;
import com.pietrantuono.podcasts.singlepodcast.viewmodel.PodcastEpisodeViewModel;

public class EpisodeHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding dataBinding;
    private PodcastEpisodeViewModel podcastEpisodeViewModel;
    private final Context context;

    public EpisodeHolder(View itemView) {
        super(itemView);
        dataBinding = DataBindingUtil.bind(itemView);
        context = itemView.getContext();
    }

    public void bind(PodcastEpisodeModel podcastEpisodeModel) {
        this.podcastEpisodeViewModel = new PodcastEpisodeViewModel(podcastEpisodeModel, context);
        dataBinding.setVariable(BR.viewModel, podcastEpisodeViewModel);
        dataBinding.executePendingBindings();
    }
}
