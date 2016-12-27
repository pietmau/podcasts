package com.pietrantuono.podcasts.singlepodcast;

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
        URL url = this.getClass().getResource("/assets/AAA_podcast");
        File testWsdl = new File(url.getFile());

        SyndFeedInput input = new SyndFeedInput();
        //SyndFeed feed = input.build(new XmlReader(feedUrl));
        /*
        * GIVEN
        */

        /*
        * WHEN
        */

        /*
        * THEN
        */
    }
}