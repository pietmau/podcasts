package com.pietrantuono.podcasts.addpodcast.customviews;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pietrantuono.podcasts.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenderRecycler extends RecyclerView {
    private GenresAdapter adapter;

    public GenderRecycler(Context context) {
        super(context);
        init();
    }

    public GenderRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GenderRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new GenresAdapter();
        setAdapter(adapter);
    }

    public void setItems(List<String> items){
        adapter.setItems(items);
    }

    private class GenresAdapter extends RecyclerView.Adapter<GengresHolder> {
        private List<String> items;

        public GenresAdapter() {
            items = new ArrayList<>();
        }

        @Override
        public GengresHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.genres_item, parent, false);
            return new GengresHolder(v);
        }

        @Override
        public void onBindViewHolder(GengresHolder holder, int position) {
            holder.bind(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void setItems(List<String> items) {
            this.items.clear();
            this.items.addAll(items);
        }
    }

    class GengresHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.genre) TextView genre;

        public GengresHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(String s) {
            genre.setText(s);
        }
    }

}
