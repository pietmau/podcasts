package com.pietrantuono.podcasts.addpodcast.customviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;
import com.pietrantuono.podcasts.main.dagger.DaggerImageLoaderComponent;
import com.pietrantuono.podcasts.main.dagger.ImageLoaderModule;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PodcastHolder extends RecyclerView.ViewHolder {
    private final SimpleImageLoader imageLoader;
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

    public PodcastHolder(View itemView, SimpleImageLoader imageLoader) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.imageLoader = imageLoader;
        DaggerImageLoaderComponent.builder().imageLoaderModule(new ImageLoaderModule(itemView.getContext())).build().inject(PodcastHolder.this);
    }

    public void onBindViewHolder(final SinglePodcast singlePodcast, PodcastsAdapter.OnSunscribeClickedListener onSunscribeClickedListener, final PodcastsAdapter.OnItemClickedClickedListener onItemClickedClickedListener, int position) {
        this.onSunscribeClickedListener = onSunscribeClickedListener;
        this.onItemClickedClickedListener = onItemClickedClickedListener;
        this.singlePodcast = singlePodcast;
        this.position = position;
        imageLoader.displayImage(singlePodcast.getArtworkUrl600(), imageView);
        title.setText(singlePodcast.getCollectionName());
        author.setText(singlePodcast.getArtistName());
        episodes_number.setText(getTrackCount(singlePodcast));
        release_date.setText(getReleaseDate());
        setUpMenu();
        populateGenres();
        setUpOnClickListener();
    }

    private String getReleaseDate() {
        //2012-04-12T16:59:00Z
        SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        Date date = null;
        try {
            date = in.parse(singlePodcast.getReleaseDate());
        } catch (Exception e) {
            return null;
        }
        SimpleDateFormat out = new SimpleDateFormat("MM yyyy");
        return out.format(date);
    }

    private String getTrackCount(SinglePodcast singlePodcast) {
        return singlePodcast.getTrackCount() + " " + itemView.getContext().getString(R.string.episodes);
    }

    private void setUpMenu() {
        popupMenu = new SimplePopUpMenu(overfow, singlePodcast, singlePodcast1 -> onSunscribeClickedListener.onSubscribeClicked(singlePodcast1));
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
