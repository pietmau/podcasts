package com.pietrantuono.podcasts.subscribedpodcasts.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsRecycler;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.main.view.MainActivity;
import com.pietrantuono.podcasts.subscribedpodcasts.presenter.SubscribedPodcastPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscribedPodcastFragment extends Fragment implements SubscribedPodcastView {
    private static final String TAG = (SubscribedPodcastFragment.class).getSimpleName();
    @Inject SubscribedPodcastPresenter presenter;
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
        return view;
    }


    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void setPodcasts(List<SinglePodcast> list) {
        recycler.setItems(list);
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
}
