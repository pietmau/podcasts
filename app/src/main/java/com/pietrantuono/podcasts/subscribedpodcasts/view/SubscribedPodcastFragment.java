package com.pietrantuono.podcasts.subscribedpodcasts.view;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsRecycler;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.main.view.MainActivity;
import com.pietrantuono.podcasts.main.view.TransitionsFramework;
import com.pietrantuono.podcasts.singlepodcast.view.SinglePodcastActivity;
import com.pietrantuono.podcasts.subscribedpodcasts.presenter.SubscribedPodcastPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscribedPodcastFragment extends Fragment implements SubscribedPodcastView {
    private static final String TAG = (SubscribedPodcastFragment.class).getSimpleName();
    @Inject SubscribedPodcastPresenter presenter;
    @Inject TransitionsFramework transitionsFramework;
    @BindView(R.id.recycler) PodcastsRecycler recycler;

    public SubscribedPodcastFragment() {
    }

    public static void navigateTo(FragmentManager fragmentManager) {
        SubscribedPodcastFragment frag = (SubscribedPodcastFragment) fragmentManager.findFragmentByTag(SubscribedPodcastFragment.TAG);
        if (frag == null) {
            frag = new SubscribedPodcastFragment();
        }
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, frag, SubscribedPodcastFragment.TAG).commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).getComponent().subscribedPodcastComponent().inject(SubscribedPodcastFragment.this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscribed, container, false);
        ButterKnife.bind(this, view);
        presenter.bindView(this);
        recycler.setListeners(presenter);
        return view;
    }


    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void setPodcasts(List<SinglePodcast> list) {
        if (recycler.getAdapter().getItemCount() != list.size()) {
            recycler.setItems(list);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void startDetailActivityWithTransition(SinglePodcast singlePodcast, ImageView
            imageView, LinearLayout titleContainer) {
        Intent intent = new Intent(getActivity(), SinglePodcastActivity.class);
        intent.putExtra(SinglePodcastActivity.SINGLE_PODCAST, singlePodcast);
        intent.putExtra(SinglePodcastActivity.STARTED_WITH_TRANSITION, true);
        getActivity().startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), getPairs(imageView, titleContainer)).toBundle());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void startDetailActivityWithoutTransition(SinglePodcast singlePodcast) {
        Intent intent = new Intent(getActivity(), SinglePodcastActivity.class);
        intent.putExtra(SinglePodcastActivity.SINGLE_PODCAST, singlePodcast);
        getActivity().startActivity(intent);
    }

    @NonNull
    private Pair[] getPairs(ImageView imageView, LinearLayout titleContainer) {
        return transitionsFramework.getPairs(imageView, getActivity(), titleContainer);
    }

    @Override
    public boolean isPartiallyHidden(int position) {
        return recycler.isPartiallyHidden(position);
    }
}
