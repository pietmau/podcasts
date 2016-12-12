package com.pietrantuono.podcasts.addpodcast.view;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.BIND_AUTO_CREATE;

public class AddPodcastFragment extends Fragment implements AddPodcastView {
    public static final String TAG = "AddPodcastFragment";
    @Inject AddPodcastPresenter addPodcastPresenter;
    @Inject Intent modelServiceIntent;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMainComponent.builder().mainModule(new MainModule(getActivity())).build().newAddPodcastComponent().inject(AddPodcastFragment.this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().bindService(modelServiceIntent, modelServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unbindService(modelServiceConnection);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        ButterKnife.bind(this, view);
        initViews();
        addPodcastPresenter.bindView(AddPodcastFragment.this, new AddPodcastFragmentMemento(savedInstanceState));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        addPodcastPresenter.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        addPodcastPresenter.onSaveInstanceState(new AddPodcastFragmentMemento(outState));
    }

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

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void updateSearchResults(List<PodcastSearchResult> items) {
        podcastsRecycler.setItems(items);
    }

    @Override
    public void showProgressBar(boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean isProgressShowing() {
        return progressBar.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onQueryTextChange(String newText) {
        podcastsRecycler.onQueryTextChange(newText);
    }

}
