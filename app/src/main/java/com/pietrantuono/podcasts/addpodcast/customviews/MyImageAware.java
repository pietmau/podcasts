package com.pietrantuono.podcasts.addpodcast.customviews;

import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.utils.L;
import com.pietrantuono.podcasts.databinding.FindPodcastItemBinding;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import hugo.weaving.DebugLog;

import static com.nostra13.universalimageloader.core.imageaware.ViewAware.WARN_CANT_SET_BITMAP;
import static com.nostra13.universalimageloader.core.imageaware.ViewAware.WARN_CANT_SET_DRAWABLE;


public class MyImageAware implements ImageAware {
    private final Reference<FindPodcastItemBinding> bindingReference;

    public MyImageAware(FindPodcastItemBinding dataBinding) {
        bindingReference = new WeakReference<>(dataBinding);
    }

    @Override
    public int getWidth() {
        if (bindingReference.get() != null) {
            return bindingReference.get().podcastImage.getWidth();
        } else {
            return 0;
        }
    }

    @Override
    public int getHeight() {
        if (bindingReference.get() != null) {
            return bindingReference.get().podcastImage.getHeight();
        } else {
            return 0;
        }
    }

    @Override
    public ViewScaleType getScaleType() {
        if (bindingReference.get() != null) {
            return ViewScaleType.fromImageView(bindingReference.get().podcastImage);
        } else {
            return ViewScaleType.CROP;
        }
    }

    @DebugLog
    @Override
    public View getWrappedView() {
        if (bindingReference.get() != null) {
            return bindingReference.get().podcastImage;
        } else {
            return null;
        }
    }

    @DebugLog
    @Override
    public boolean isCollected() {
        return bindingReference.get() == null;
    }

    @Override
    public int getId() {
        FindPodcastItemBinding itemBinding = bindingReference.get();
        return itemBinding == null ? super.hashCode() : itemBinding.podcastImage.hashCode();
    }

    @DebugLog
    @Override
    public boolean setImageDrawable(Drawable drawable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            View view = getWrappedView();
            if (view != null) {
                setImageDrawableInto(drawable, view);
                return true;
            }
        } else {
            L.w(WARN_CANT_SET_DRAWABLE);
        }
        return false;
    }

    private void setImageDrawableInto(Drawable drawable, View view) {
        ((ImageView) view).setImageDrawable(drawable);
        if (drawable instanceof AnimationDrawable) {
            ((AnimationDrawable) drawable).start();
        }
    }

    @DebugLog
    @Override
    public boolean setImageBitmap(Bitmap bitmap) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            View view = getWrappedView();
            if (view != null) {
                setPalette(bitmap);
                setImageBitmapInto(bitmap, view);
                return true;
            }
        } else {
            L.w(WARN_CANT_SET_BITMAP);
        }
        return false;
    }

    private void setPalette(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                if (vibrant != null && bindingReference.get() != null) {
                    bindingReference.get().titleContainer.setBackgroundColor(vibrant.getRgb());
                }
            }
        });
    }

    private void setImageBitmapInto(Bitmap bitmap, View view) {
        ((ImageView) view).setImageBitmap(bitmap);
    }
}