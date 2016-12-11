package com.pietrantuono.podcasts.addpodcast.customviews;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.pietrantuono.podcasts.R;

public class PodcastDecoration extends RecyclerView.ItemDecoration {
    int normalMargin;
    int halfMargin;

    public PodcastDecoration(Context context) {
        normalMargin = (int) context.getResources().getDimension(R.dimen.item_margin);
        halfMargin = (int) context.getResources().getDimension(R.dimen.half_item_margin);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        setLeftAndRightMargins(outRect, view);
        setUpperAndLowerMargins(outRect, view);
    }

    private void setUpperAndLowerMargins(Rect outRect, View view) {
        outRect.top = normalMargin;
    }

    private void setLeftAndRightMargins(Rect outRect, View view) {
        int spanIndex = ((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
        if (spanIndex == 0) {
            outRect.right = halfMargin;
        }
        if (spanIndex == 1) {
            outRect.left = halfMargin;
        }
    }
}
