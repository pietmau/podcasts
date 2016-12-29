package com.pietrantuono.podcasts.singlepodcast.customviews;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.apis.PodcastEpisode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EpisodeHolder extends RecyclerView.ViewHolder {
    private PodcastEpisode podcastEpisode;

    @BindView(R.id.image) ImageView imageView;
    @BindView(R.id.pub_date) TextView pub_date;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.author) TextView author;
    @BindView(R.id.overflow) ImageView overflow;
    @BindView(R.id.subtitle) TextView subtitle;


    public EpisodeHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(PodcastEpisode podcastEpisode) {
        this.podcastEpisode = podcastEpisode;
    }
}
