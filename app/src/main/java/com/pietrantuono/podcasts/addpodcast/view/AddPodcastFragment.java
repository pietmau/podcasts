package com.pietrantuono.podcasts.addpodcast.view;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.customviews.CustomProgressBar;
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsRecycler;
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast;
import com.pietrantuono.podcasts.addpodcast.presenter.AddPodcastPresenter;
import com.pietrantuono.podcasts.main.view.MainActivity;
import com.pietrantuono.podcasts.singlepodcast.view.SinglePodcastActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

public class AddPodcastFragment extends Fragment implements AddPodcastView {
    public static final String TAG = (AddPodcastFragment.class).getSimpleName();
    @Inject AddPodcastPresenter addPodcastPresenter;
    @Inject ApiLevelChecker apiLevelChecker;
    @BindView(R.id.search_view) SearchView searchView;
    @BindView(R.id.search_results) PodcastsRecycler podcastsRecycler;
    @BindView(R.id.progress) CustomProgressBar progressBar;

    public static AddPodcastFragment newInstance() {
        return new AddPodcastFragment();
    }

    public static void navigateTo(FragmentManager fragmentManager) {
        AddPodcastFragment frag = (AddPodcastFragment) fragmentManager.findFragmentByTag(AddPodcastFragment.TAG);
        if (frag == null) {
            frag = AddPodcastFragment.newInstance();
        }
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, frag, AddPodcastFragment.TAG).commit();
    }

    @DebugLog
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).getComponent().newAddPodcastComponent().inject(AddPodcastFragment.this);
    }

    @DebugLog
    @Override
    public void onResume() {
        super.onResume();
        addPodcastPresenter.onResume();
    }

    @DebugLog
    @Override
    public void onPause() {
        super.onPause();
        addPodcastPresenter.onPause();
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
        initSearchView();
        return view;
    }

    @DebugLog
    @Override
    public void onSaveInstanceState(Bundle outState) {
        addPodcastPresenter.onSaveInstanceState(new AddPodcastFragmentMemento(outState));
    }

    @DebugLog
    private void initSearchView() {
        searchView.setOnQueryTextListener(new PodcastOnQueryTextListener(addPodcastPresenter));
    }

    @DebugLog
    @Override
    public void onError(Throwable e) {
    }

    @DebugLog
    @Override
    public void updateSearchResults(List<SinglePodcast> items) {
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

    @Override
    public void onQueryTextChange(String newText) {
        podcastsRecycler.onQueryTextChange(newText);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void startDetailActivityWithTransition(SinglePodcast singlePodcast, ImageView imageView) {
        Intent intent = new Intent(getActivity(), SinglePodcastActivity.class);
        intent.putExtra(SinglePodcastActivity.SINGLE_PODCAST, singlePodcast);
        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair(imageView, getString(R.string.detail_transition));
        pairs[1] = new Pair(searchView, );
        getActivity().startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairs).toBundle());
    }

    @Override
    public boolean isLollipop() {
        return apiLevelChecker.isLollipopOrHigher();
    }

    @Override
    public void startDetailActivityWithOutTransition(SinglePodcast singlePodcast) {

    }
}
