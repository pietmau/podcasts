package com.pietrantuono.podcasts.addpodcast.customviews;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader;

import hugo.weaving.DebugLog;

public class ColorableLinearLayout extends LinearLayout {
    public ColorableLinearLayout(Context context) {
        super(context);
    }

    public ColorableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
