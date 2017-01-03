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

    Date getPubDate();

    String getTitle();

    String getDescription();

}
