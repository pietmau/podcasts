package com.pietrantuono.podcasts.addpodcast.customviews;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pietrantuono.podcasts.BR;
import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.databinding.PodcastItemBinding;
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;
import com.pietrantuono.podcasts.main.dagger.DaggerImageLoaderComponent;
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule;
import com.pietrantuono.podcasts.singlepodcast.viewmodel.PodcastEpisodeViewModel;
import com.pietrantuono.podcasts.singlepodcast.viewmodel.ResourcesProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PodcastHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding dataBinding;
    private final ResourcesProvider resourcesProvider;
    private PodcastsAdapter.OnSunscribeClickedListener onSunscribeClickedListener;
    private PodcastsAdapter.OnItemClickedClickedListener onItemClickedClickedListener;
    @BindView(R.id.podcast_image) ImageView imageView;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.author) TextView author;
    @BindView(R.id.overflow) ImageView overfow;
    @BindView(R.id.genre) TextView genres;
    @BindView(R.id.episodes_number) TextView episodes_number;
    @BindView(R.id.release_date) TextView release_date;
    private SinglePodcast singlePodcast;
    private int position;
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
    }

    private void setUpMenu() {
        popupMenu = new SimplePopUpMenu(((PodcastItemBinding) dataBinding).overflow, singlePodcast, singlePodcast1 -> onSunscribeClickedListener.onSubscribeClicked(singlePodcast1));
    }

    private void populateGenres() {
        genres.setText(singlePodcast.getPrimaryGenreName());
    }

    private void setUpOnClickListener() {
        itemView.setOnClickListener(view -> onItemClickedClickedListener.onItemClicked(singlePodcast, imageView, position));
    }

    @OnClick(R.id.overflow)
    public void showMenu() {
        popupMenu.show();
    }


}
