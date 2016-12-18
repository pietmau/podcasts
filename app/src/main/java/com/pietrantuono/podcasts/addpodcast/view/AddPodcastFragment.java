package com.pietrantuono.podcasts.addpodcast.view;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsRecycler;
import com.pietrantuono.podcasts.addpodcast.model.pojos.PodcastSearchResult;
import com.pietrantuono.podcasts.addpodcast.presenter.AddPodcastPresenter;
import com.pietrantuono.podcasts.main.dagger.DaggerMainComponent;
import com.pietrantuono.podcasts.main.dagger.MainModule;
import com.pietrantuono.podcasts.singlepodcast.SinglePodcastActivity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;

import static android.content.Context.BIND_AUTO_CREATE;

public class AddPodcastFragment extends Fragment implements AddPodcastView {
    public static final String TAG = "AddPodcastFragment";
    @Inject AddPodcastPresenter addPodcastPresenter;
    @Inject Intent modelServiceIntent;
    @Inject ApiLevelChecker apiLevelChecker;
    @Inject @Named("Add") ServiceConnection modelServiceConnection;
    @BindView(R.id.search_view) SearchView searchView;
    @BindView(R.id.search_results) PodcastsRecycler podcastsRecycler;
    @BindView(R.id.progress) ProgressBar progressBar;

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
        DaggerMainComponent.builder().mainModule(new MainModule(getActivity())).build().newAddPodcastComponent().inject(AddPodcastFragment.this);
    }

    @DebugLog
    @Override
    public void onResume() {
        super.onResume();
        getActivity().bindService(modelServiceIntent, modelServiceConnection, BIND_AUTO_CREATE);
    }

    @DebugLog
    @Override
    public void onPause() {
        super.onPause();
        getActivity().unbindService(modelServiceConnection);
    }

    @DebugLog
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        ButterKnife.bind(this, view);
        initViews();
        addPodcastPresenter.bindView(AddPodcastFragment.this, new AddPodcastFragmentMemento(savedInstanceState));
        podcastsRecycler.setOnSubscribeListener(addPodcastPresenter);
        podcastsRecycler.setOnItemClickListener(addPodcastPresenter);
        return view;
    }

    @DebugLog
    @Override
    public void onDestroy() {
        super.onDestroy();
        addPodcastPresenter.onDestroy();
    }

    @DebugLog
    @Override
    public void onSaveInstanceState(Bundle outState) {
        addPodcastPresenter.onSaveInstanceState(new AddPodcastFragmentMemento(outState));
    }

    @DebugLog
    private void initViews() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return addPodcastPresenter.onQueryTextSubmit(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return addPodcastPresenter.onQueryTextChange(newText);
            }
        });
    }

    @DebugLog
    @Override
    public void onError(Throwable e) {
    }

    @DebugLog
    @Override
    public void updateSearchResults(List<PodcastSearchResult> items) {
        podcastsRecycler.setItems(items);
    }

    @DebugLog
    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @DebugLog
    @Override
    public boolean isProgressShowing() {
        return progressBar.getVisibility() == View.VISIBLE;
    }

    @DebugLog
    @Override
    public void onQueryTextChange(String newText) {
        podcastsRecycler.onQueryTextChange(newText);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void startDetailActivityWithTransition(PodcastSearchResult podcastSearchResult) {
        Intent intent = new Intent(getActivity(), SinglePodcastActivity.class);
        getActivity().startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle());
    }

    @Override
    public boolean isLollipop() {
        return apiLevelChecker.isLollipopOrHigher();
    }

    @Override
    public void startDetailActivityWithOutTransition(PodcastSearchResult podcastSearchResult) {

    }

}
