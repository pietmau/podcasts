package com.pietrantuono.podcasts.singlepodcast.view;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.pietrantuono.podcasts.main.view.TransitionsFramework;

public class TransitionImageLoadingListener implements ImageLoadingListener {
    private AppCompatActivity activity;
    private final TransitionsFramework transitionsFramework;

    public TransitionImageLoadingListener(AppCompatActivity activity, TransitionsFramework transitionsFramework) {
        this.activity = activity;
        this.transitionsFramework = transitionsFramework;
    }

    @Override
    public void onLoadingStarted(String imageUri, View view) {

    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        if (isActivityInValidState(activity)) {
            transitionsFramework.startPostponedEnterTransition(activity);
        }
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        Bitmap bitmap = ((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap();
        Palette.from(bitmap).generate(palette -> {
            if (!isActivityInValidState(activity)) {
                return;
            }
            Palette.Swatch vibrant = palette.getVibrantSwatch();
            if (vibrant != null) {
                activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(vibrant.getRgb()));
            }
            transitionsFramework.startPostponedEnterTransition(activity);
        });

    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {
        if (isActivityInValidState(activity)) {
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

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }
}

