package com.pietrantuono.podcasts.addpodcast.singlepodcast;

import android.support.annotation.NonNull;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RomeTest {

    @Before
    public void setUp() {
    }

    @Test
    public void given_foo_when_bar_then_fobar() {
        File file = getPodcastFile();
        SyndFeed feed = getFeed(file);
        assertEquals(15, feed.getEntries().size());
    }

    private SyndFeed getFeed(File file) {
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = null;
        try {
            feed = input.build(new XmlReader(file));
        } catch (Exception e) {
            fail();
        }
        return feed;
    }

    @NonNull
    private File getPodcastFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("AAA_podcast");
        return new File(resource.getPath());
    }

    private void foo() {

    }
}