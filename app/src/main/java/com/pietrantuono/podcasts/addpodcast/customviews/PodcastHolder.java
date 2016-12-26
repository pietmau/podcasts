package com.pietrantuono.podcasts.addpodcast.customviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;
import com.pietrantuono.podcasts.main.dagger.DaggerImageLoaderComponent;
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PodcastHolder extends RecyclerView.ViewHolder {
    private final SimpleImageLoader imageLoader;
    private PodcastsAdapter.OnSunscribeClickedListener onSunscribeClickedListener;
    private PodcastsAdapter.OnItemClickedClickedListener onItemClickedClickedListener;
    @BindView(R.id.image) ImageView imageView;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.author) TextView author;
    @BindView(R.id.overflow) ImageView overfow;
    @BindView(R.id.genres) TextView genres;
    private SinglePodcast singlePodcast;
    private SimplePopUpMenu popupMenu;

    public PodcastHolder(View itemView, SimpleImageLoader imageLoader) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.imageLoader = imageLoader;
        DaggerImageLoaderComponent.builder().imageLoaderModule(new ImageLoaderModule(itemView.getContext())).build().inject(PodcastHolder.this);
    }

    public void onBindViewHolder(final SinglePodcast singlePodcast, PodcastsAdapter.OnSunscribeClickedListener onSunscribeClickedListener, final PodcastsAdapter.OnItemClickedClickedListener onItemClickedClickedListener) {
        this.onSunscribeClickedListener = onSunscribeClickedListener;
        this.onItemClickedClickedListener = onItemClickedClickedListener;
        this.singlePodcast = singlePodcast;
        singlePodcast.getGenres().remove("Podcasts");
        imageLoader.displayImage(singlePodcast.getArtworkUrl600(), imageView);
        title.setText(singlePodcast.getCollectionName());
        author.setText(singlePodcast.getArtistName());

        setUpMenu();
        populateGenres();
        setUpOnClickListener();
    }


    private void setUpMenu() {
        popupMenu = new SimplePopUpMenu(overfow, singlePodcast, new PodcastsAdapter.OnSunscribeClickedListener() {
            @Override
            public void onSubscribeClicked(SinglePodcast singlePodcast) {
                onSunscribeClickedListener.onSubscribeClicked(singlePodcast);
            }
        });
    }

    private void populateGenres() {
        genres.setText(singlePodcast.getGenresAsString());
    }

    private void setUpOnClickListener() {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickedClickedListener.onItemClicked(singlePodcast, imageView);
            }
        });
    }


    @OnClick(R.id.overflow)
    public void showMenu() {
        popupMenu.show();
    }
}
