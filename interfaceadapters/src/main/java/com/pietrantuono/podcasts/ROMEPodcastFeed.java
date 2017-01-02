package com.pietrantuono.podcasts;

import android.content.Context;

import com.pietrantuono.CrashlyticsWrapper;
import com.pietrantuono.podcasts.apis.PodcastEpisode;
import com.pietrantuono.podcasts.apis.PodcastFeed;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

import java.util.ArrayList;
import java.util.List;

public class ROMEPodcastFeed implements PodcastFeed {
    private final List<PodcastEpisode> episodes;
    private final CrashlyticsWrapper crashlyticsWrapper;
    private final Context context;

    public ROMEPodcastFeed(SyndFeed feed, CrashlyticsWrapper crashlyticsWrapper, Context context) {
        this.crashlyticsWrapper = crashlyticsWrapper;
        this.context = context;
        episodes = parseEpisodes(feed);
    }

    @Override
    public List<PodcastEpisode> getEpisodes() {
        return episodes;
    }

    private List<PodcastEpisode> parseEpisodes(SyndFeed feed) {
        List<PodcastEpisode> podcastEpisodes = new ArrayList<>();
        for (SyndEntry entry : feed.getEntries()) {
            podcastEpisodes.add(new ROMEPodcastEpisode(entry, crashlyticsWrapper, context));
        }
        return podcastEpisodes;
    }
}
