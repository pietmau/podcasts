package com.pietrantuono.podcasts.apis;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class PodcastFeedConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {


        return new Converter<ResponseBody, PodcastFeed>() {
            @Override
            public PodcastFeed convert(ResponseBody value) throws IOException {
                return null;
            }
        };
    }
}
