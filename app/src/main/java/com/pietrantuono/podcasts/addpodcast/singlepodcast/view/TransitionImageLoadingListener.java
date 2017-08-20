package com.pietrantuono.podcasts.addpodcast.singlepodcast.view;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.pietrantuono.podcasts.main.view.TransitionsFramework;

public class TransitionImageLoadingListener extends SimpleImageLoadingListener {
    private AppCompatActivity activity;
    private final TransitionsFramework transitionsFramework;
    private ExtractedColors colors;

    public TransitionImageLoadingListener(AppCompatActivity activity, TransitionsFramework transitionsFramework) {
        this.activity = activity;
        this.transitionsFramework = transitionsFramework;
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        onNotLoaded();
    }

    @Override
    public void onLoadingCancelled(String imageUri, View view) {
        onNotLoaded();
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        Palette.from(((BitmapDrawable) ((ImageView) view).getDrawable()).getBitmap()).generate(palette -> {
            if (!isActivityInValidState(activity)) {
                return;
            }
            Palette.Swatch vibrant = palette.getVibrantSwatch();
            if (vibrant != null) {
                int backgroundColor = vibrant.getRgb();
                activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(backgroundColor));
                this.colors = new ExtractedColors(backgroundColor, vibrant.getTitleTextColor());
            }
            transitionsFramework.startPostponedEnterTransition(activity);
        });

    }

    private void onNotLoaded() {
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

    public ExtractedColors getColors() {
        return colors;
    }

    static class ExtractedColors implements Parcelable {
        private final int backgroundColor;
        private final int textColor;

        ExtractedColors(int backgroundColor, int textColor) {
            this.backgroundColor = backgroundColor;
            this.textColor = textColor;
        }

        public int getBackgroundColor() {
            return backgroundColor;
        }

        public int getTextColor() {
            return textColor;
        }

        protected ExtractedColors(Parcel in) {
            backgroundColor = in.readInt();
            textColor = in.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(backgroundColor);
            dest.writeInt(textColor);
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator<ExtractedColors> CREATOR = new Parcelable.Creator<ExtractedColors>() {
            @Override
            public ExtractedColors createFromParcel(Parcel in) {
                return new ExtractedColors(in);
            }

            @Override
            public ExtractedColors[] newArray(int size) {
                return new ExtractedColors[size];
            }
        };
    }
}

