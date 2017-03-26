package com.pietrantuono.podcasts.addpodcast.customviews;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.pietrantuono.podcasts.BR;
import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;
import com.pietrantuono.podcasts.singlepodcast.viewmodel.ResourcesProvider;

import butterknife.OnClick;

public class PodcastHolder extends RecyclerView.ViewHolder {
    private final com.pietrantuono.podcasts.databinding.FindPodcastItemBinding dataBinding;
    private final ResourcesProvider resourcesProvider;
    private SimplePopUpMenu popupMenu;
    private SimpleImageLoader loader;

    public PodcastHolder(View itemView, ResourcesProvider resourcesProvider, SimpleImageLoader loader) {
        super(itemView);
        dataBinding = DataBindingUtil.bind(itemView);
        this.resourcesProvider = resourcesProvider;
        this.loader = loader;
    }

    public void onBindViewHolder(final SinglePodcast singlePodcast, PodcastsAdapter.OnSunscribeClickedListener onSunscribeClickedListener, final PodcastsAdapter.OnItemClickedClickedListener onItemClickedClickedListener, int position) {
        SinlglePodcastViewModel podcastEpisodeViewModel = new SinlglePodcastViewModel(singlePodcast, resourcesProvider, onItemClickedClickedListener, onSunscribeClickedListener, position);
        dataBinding.setVariable(BR.sinlglePodcastViewModel, podcastEpisodeViewModel);
        dataBinding.executePendingBindings();
        setUpOnClickListener(singlePodcast, position, onItemClickedClickedListener);
        setUpMenu(singlePodcast, onSunscribeClickedListener);
        setOverflowClickListener();
        loadImage();
    }

    private void loadImage() {
        loader.displayImage(dataBinding.getSinlglePodcastViewModel().getImageUrl(), dataBinding.podcastImage, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                Palette.generateAsync(loadedImage, new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        Palette.Swatch vibrant = palette.getDarkVibrantSwatch();
                        if (vibrant != null) {
                            dataBinding.titleContainer.setBackgroundColor(vibrant.getRgb());
                            dataBinding.title.setTextColor(vibrant.getBodyTextColor());
                        }
                    }
                });
            }
        });
    }

    private void setOverflowClickListener() {
        //((com.pietrantuono.podcasts.databinding.FindPodcastItemBinding) dataBinding).overflow.setOnClickListener(view -> showMenu());
    }

    private void setUpMenu(SinglePodcast singlePodcast, PodcastsAdapter.OnSunscribeClickedListener onSunscribeClickedListener) {
        //popupMenu = new SimplePopUpMenu(((FindPodcastItemBinding) dataBinding).overflow, singlePodcast, singlePodcast1 -> onSunscribeClickedListener.onSubscribeClicked(singlePodcast1));
    }

    private void setUpOnClickListener(SinglePodcast singlePodcast, int position, PodcastsAdapter.OnItemClickedClickedListener onItemClickedClickedListener) {
        dataBinding.podcastImage.setOnClickListener(view -> onItemClickedClickedListener.onItemClicked(singlePodcast, dataBinding.podcastImage, position, dataBinding.titleContainer));
    }

    @OnClick(R.id.overflow)
    public void showMenu() {
        popupMenu.show();
    }


}
