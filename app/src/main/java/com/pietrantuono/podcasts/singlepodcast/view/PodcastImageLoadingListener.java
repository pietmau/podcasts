package com.pietrantuono.podcasts.singlepodcast.view;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.pietrantuono.podcasts.main.view.TransitionsFramework;

public class PodcastImageLoadingListener implements ImageLoadingListener {
    private AppCompatActivity activity;
    private TransitionsFramework transitionsFramework;

    public PodcastImageLoadingListener(AppCompatActivity activity, TransitionsFramework transitionsFramework) {
        this.activity = activity;
        this.transitionsFramework = transitionsFramework;
    }

    @Override
    public void onLoadingStarted(String imageUri, View view) {

    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        if(isActivityInValidState(activity)){
            transitionsFramework.startPostponedEnterTransition(activity);
        }
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        if(isActivityInValidState(activity)){
            transitionsFramework.startPostponedEnterTransition(activity);
        }
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {
        if(isActivityInValidState(activity)){
            transitionsFramework.startPostponedEnterTransition(activity);
        }
    }

    private boolean isActivityInValidState(AppCompatActivity activity) {
        if (activity == null) {
            return false;
        }
        boolean destroyed = activity.isDestroyed();
        boolean finishing = activity.isFinishing();
        boolean changingConfigurations = activity.isChangingConfigurations();
        return (!destroyed && !finishing && !changingConfigurations);
    }
}
