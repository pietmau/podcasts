package com.pietrantuono.podcasts.apis;

import com.pietrantuono.podcasts.CrashlyticsWrapper;
import com.pietrantuono.podcasts.interfaces.PodcastEpisodeParser;
import com.pietrantuono.podcasts.interfaces.ROMEPodcastFeed;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

class PodcastFeedConverterFactory extends Converter.Factory {
    private final SyndFeedInput input = new SyndFeedInput();
    private final CrashlyticsWrapper crashlyticsWrapper;
    private final PodcastEpisodeParser episodeparser;

    public PodcastFeedConverterFactory(CrashlyticsWrapper crashlyticsWrapper, PodcastEpisodeParser episodeparser) {
        this.crashlyticsWrapper = crashlyticsWrapper;
        this.episodeparser = episodeparser;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        return new Converter<ResponseBody, PodcastFeed>() {
            @Override
            public PodcastFeed convert(ResponseBody value) throws IOException {
                try {
                    SyndFeed syndFeed = input.build(new XmlReader(value.byteStream()));
                    return parseFeed(syndFeed);
                } catch (Exception e) {
                    crashlyticsWrapper.logException(e);
                    throw new IOException(e);
                }
            }
        };
    }

    private PodcastFeed parseFeed(SyndFeed syndFeed) {
        List<PodcastEpisodeModel> podcastEpisodeModels = episodeparser.parseFeed(syndFeed);
        return new ROMEPodcastFeed(podcastEpisodeModels);
    }

}