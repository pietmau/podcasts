package com.pietrantuono.podcasts.addpodcast.model.retrofitconverters;

import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResultEnvelope;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import models.pojos.PodcastImpl;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PodcastSearchResultConverterFactory extends Converter.Factory {

    private final GsonConverterFactory factory;

    public PodcastSearchResultConverterFactory(GsonConverterFactory factory) {
        this.factory = factory;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(final Type type, Annotation[] annotations, Retrofit retrofit) {

        ParameterizedType envelope = new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{type};
            }

            @Override
            public Type getRawType() {
                return PodcastSearchResultEnvelope.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };

        Converter<ResponseBody, ?> gsonConverter = factory
                .responseBodyConverter(envelope, annotations, retrofit);

        return new PodcastSearchResultConverter(gsonConverter);
    }


    private class PodcastSearchResultConverter<T> implements Converter<ResponseBody, List<PodcastImpl>> {

        private final Converter<ResponseBody, PodcastSearchResultEnvelope> gsonConverter;

        public PodcastSearchResultConverter(Converter<ResponseBody, PodcastSearchResultEnvelope> gsonConverter) {
            this.gsonConverter = gsonConverter;
        }

        @Override
        public List<PodcastImpl> convert(ResponseBody value) throws IOException {
            PodcastSearchResultEnvelope envelope = gsonConverter.convert(value);
            return envelope.getResults();
        }
    }
}


