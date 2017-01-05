package com.pietrantuono.podcasts.apis;

import com.rometools.rome.feed.synd.SyndEnclosure;

import java.net.URL;
import java.util.Date;
import java.util.List;

public interface PodcastEpisodeModel {

    String getDuration();

    String getAuthor();

    boolean isExplicit();

    URL getImage();

    List<String> getKeywords();

    String getSubtitle();

    String getSummary();

    Date getPubDate();

    String getTitle();

    String getDescription();

    List<SyndEnclosure> getEnclosures();
}
