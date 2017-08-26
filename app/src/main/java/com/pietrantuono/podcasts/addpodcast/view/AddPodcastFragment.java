package com.pietrantuono.podcasts.addpodcast.view;

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
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.SimpleOnQueryTextListener;
import com.pietrantuono.podcasts.addpodcast.customviews.CustomProgressBar;
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsRecycler;
import com.pietrantuono.podcasts.addpodcast.dagger.AddPodcastModule;
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast;
import com.pietrantuono.podcasts.addpodcast.presenter.AddPodcastPresenter;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.AddSinglePodcastActivity;
import com.pietrantuono.podcasts.main.view.MainActivity;
import com.pietrantuono.podcasts.main.view.Transitions;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

public class AddPodcastFragment extends Fragment implements AddPodcastView {
    private static final String TAG = (AddPodcastFragment.class).getSimpleName();
    @Inject AddPodcastPresenter addPodcastPresenter;
    @Inject Transitions transitions;
    @BindView(R.id.search_view) SearchView searchView;
    @BindView(R.id.search_results) PodcastsRecycler podcastsRecycler;
    @BindView(R.id.progress) CustomProgressBar progressBar;

    public static void navigateTo(FragmentManager fragmentManager) {
        AddPodcastFragment frag = (AddPodcastFragment) fragmentManager.findFragmentByTag(AddPodcastFragment.TAG);
        if (frag == null) {
            frag = new AddPodcastFragment();
        }
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, frag, AddPodcastFragment.TAG).commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).getMainComponent().with(new AddPodcastModule(getActivity()))
                .inject(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        addPodcastPresenter.onStart();
    }

    @DebugLog
    @Override
    public void onPause() {
        super.onPause();
        addPodcastPresenter.onStop();
    }

    @DebugLog
    @Override
    public void onDestroy() {
        super.onDestroy();
        addPodcastPresenter.onDestroy();
    }

    @DebugLog
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        ButterKnife.bind(this, view);
        addPodcastPresenter.bindView(AddPodcastFragment.this, new AddPodcastFragmentMemento(savedInstanceState));
        podcastsRecycler.setListeners(addPodcastPresenter);
        searchView.setOnQueryTextListener(new SimpleOnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(@Nullable String query) {
                return addPodcastPresenter.onQueryTextSubmit(query);
            }
        });
        return view;
    }

    @DebugLog
    @Override
    public void onSaveInstanceState(Bundle outState) {
        addPodcastPresenter.onSaveInstanceState(new AddPodcastFragmentMemento(outState));
    }

    @DebugLog
    @Override
    public void onError(Throwable e) {
    }

    @DebugLog
    @Override
    public void updateSearchResults(List<Podcast> items) {
        podcastsRecycler.setItems(items);
    }

    @Override
    public void showProgressBar(boolean show) {
        progressBar.showProgressBar(show);
    }

    @Override
    public boolean isProgressShowing() {
        return progressBar.getVisibility() == View.VISIBLE;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void startDetailActivityWithTransition(Podcast podcast, ImageView imageView, LinearLayout titleContainer) {
        Intent intent = new Intent(getActivity(), AddSinglePodcastActivity.class);
        intent.putExtra(AddSinglePodcastActivity.Companion.getSINGLE_PODCAST_TRACK_ID(), podcast);
        intent.putExtra(AddSinglePodcastActivity.Companion.getSTARTED_WITH_TRANSITION(), true);
        getActivity().startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), getPairs(imageView, titleContainer)).toBundle());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void startDetailActivityWithoutTransition(Podcast podcast) {
        Intent intent = new Intent(getActivity(), AddSinglePodcastActivity.class);
        intent.putExtra(AddSinglePodcastActivity.Companion.getSINGLE_PODCAST_TRACK_ID(), podcast);
        getActivity().startActivity(intent);
    }

    @NonNull
    private Pair[] getPairs(ImageView imageView, LinearLayout titleContainer) {
        return transitions.getPairs(imageView, getActivity(), titleContainer);
    }

    @Override
    public boolean isPartiallyHidden(int position) {
        return podcastsRecycler.isPartiallyHidden(position);
    }
}
