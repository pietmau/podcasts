package com.pietrantuono.podcasts.apis;

import java.util.Date;
import java.util.List;

public interface PodcastEpisode {

    String getDuration();

    String getAuthor();

    boolean explicit();

    String getImage();

    List<String> getKeywords();

    String getSubtitle();

    String getSummary();

    String getPubDate();

    String getTitle();

    String getDescription();

    int getMediaTypeImage();

    int getMediaTypeText();
}
