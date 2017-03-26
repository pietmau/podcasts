package com.pietrantuono.podcasts.addpodcast.customviews;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pietrantuono.podcasts.BR;
import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.databinding.FindPodcastItemBinding;
import com.pietrantuono.podcasts.singlepodcast.viewmodel.ResourcesProvider;

import butterknife.OnClick;

public class PodcastHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding dataBinding;
    private final ResourcesProvider resourcesProvider;
    private SimplePopUpMenu popupMenu;

    public PodcastHolder(View itemView, ResourcesProvider resourcesProvider) {
        super(itemView);
        dataBinding = DataBindingUtil.bind(itemView);
        this.resourcesProvider = resourcesProvider;
    }

    public void onBindViewHolder(final SinglePodcast singlePodcast, PodcastsAdapter.OnSunscribeClickedListener onSunscribeClickedListener, final PodcastsAdapter.OnItemClickedClickedListener onItemClickedClickedListener, int position) {
        SinlglePodcastViewModel podcastEpisodeViewModel = new SinlglePodcastViewModel(singlePodcast, resourcesProvider, onItemClickedClickedListener, onSunscribeClickedListener, position);
        dataBinding.setVariable(BR.sinlglePodcastViewModel, podcastEpisodeViewModel);
        dataBinding.executePendingBindings();
        setUpOnClickListener(singlePodcast, position, onItemClickedClickedListener);
        setUpMenu(singlePodcast, onSunscribeClickedListener);
        setOverflowClickListener();
    }

    private void setOverflowClickListener() {
        //((FindPodcastItemBinding) dataBinding).overflow.setOnClickListener(view -> showMenu());
    }

    private void setUpMenu(SinglePodcast singlePodcast, PodcastsAdapter.OnSunscribeClickedListener onSunscribeClickedListener) {
        //popupMenu = new SimplePopUpMenu(((FindPodcastItemBinding) dataBinding).overflow, singlePodcast, singlePodcast1 -> onSunscribeClickedListener.onSubscribeClicked(singlePodcast1));
    }

    private void setUpOnClickListener(SinglePodcast singlePodcast, int position, PodcastsAdapter.OnItemClickedClickedListener onItemClickedClickedListener) {
       ((FindPodcastItemBinding) dataBinding).podcastImage.setOnClickListener(view -> onItemClickedClickedListener.onItemClicked(singlePodcast, ((FindPodcastItemBinding) dataBinding).podcastImage, position, ((FindPodcastItemBinding) dataBinding).titleContainer));
    }

    @OnClick(R.id.overflow)
    public void showMenu() {
        popupMenu.show();
    }


}
