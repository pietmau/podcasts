package com.pietrantuono.podcasts.apis;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.Result;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;

public class RxJavaCallAdapterPodcastFeedConverterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {

        return new CallAdapter<Object>() {
            @Override
            public Type responseType() {
                return PodcastFeed.class;
            }

            @Override
            public <R> Object adapt(Call<R> call) {
//                Observable<Result<R>> observable = Observable.create(new RxJavaCallAdapterFactory.CallOnSubscribe<>(call)) //
//                        .map(new Func1<Response<R>, Result<R>>() {
//                            @Override
//                            public Result<R> call(Response<R> response) {
//                                return Result.response(response);
//                            }
//                        }).onErrorReturn(new Func1<Throwable, Result<R>>() {
//                            @Override
//                            public Result<R> call(Throwable throwable) {
//                                return Result.error(throwable);
//                            }
//                        });
//                return observable;
                return null;
            }
        };
    }
}
