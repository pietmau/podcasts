package com.pietrantuono.podcasts.addpodcast.customviews;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;
import com.pietrantuono.podcasts.main.dagger.DaggerImageLoaderComponent;
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PodcastHolder extends RecyclerView.ViewHolder {
    private final ImageLoader imageLoader;

    @Inject DisplayImageOptions displayImageOptions;

    @BindView(R.id.card) CardView cardView;
    @BindView(R.id.image) ImageView imageView;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.author) TextView author;
    private PodcastSearchResult podcastSearchResult;

    public PodcastHolder(View itemView, ImageLoader imageLoader) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.imageLoader = imageLoader;
        DaggerImageLoaderComponent.builder().imageLoaderModule(new ImageLoaderModule(itemView.getContext())).build().inject(PodcastHolder.this);
    }

    public void onBindViewHolder(PodcastSearchResult podcastSearchResult) {
        this.podcastSearchResult = podcastSearchResult;
        imageLoader.displayImage(podcastSearchResult.getArtworkUrl600(), imageView, displayImageOptions);
        title.setText(podcastSearchResult.getCollectionName());
        author.setText(podcastSearchResult.getArtistName());
    }
}
