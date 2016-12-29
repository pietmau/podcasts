package com.pietrantuono.podcasts.apis;

import com.rometools.modules.itunes.types.Duration;
import com.rometools.rome.feed.WireFeed;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndCategory;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndImage;
import com.rometools.rome.feed.synd.SyndLink;
import com.rometools.rome.feed.synd.SyndPerson;

import org.jdom2.Element;

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

}
