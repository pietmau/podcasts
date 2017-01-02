package com.pietrantuono.podcasts.apis;

import com.pietrantuono.CrashlyticsWrapper;
import com.pietrantuono.podcasts.ROMEPodcastFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class PodcastFeedConverterFactory extends Converter.Factory {
    private final SyndFeedInput input = new SyndFeedInput();
    private final CrashlyticsWrapper crashlyticsWrapper;

    public PodcastFeedConverterFactory(CrashlyticsWrapper crashlyticsWrapper) {
        this.crashlyticsWrapper = crashlyticsWrapper;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        return new Converter<ResponseBody, PodcastFeed>() {
            @Override
            public PodcastFeed convert(ResponseBody value) throws IOException {
                try {
                    return new ROMEPodcastFeed(input.build(new XmlReader(value.byteStream())), crashlyticsWrapper);
                } catch (FeedException e) {
                    throw new IOException(e);
                }
            }
        };
    }

}
