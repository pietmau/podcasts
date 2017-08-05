package com.pietrantuono.podcasts.apis;


import com.rometools.rome.feed.synd.SyndEnclosure;

import java.util.Date;
import java.util.List;

public interface PodcastEpisode {

    String getDuration();

    String getAuthor();

    boolean isExplicit();

    String getImageUrl();

    List<String> getKeywords();

    String getSubtitle();

    String getSummary();

    Date getPubDate();

    String getTitle();

    String getDescription();

    List<? extends SyndEnclosure> getEnclosures();
}