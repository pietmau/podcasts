package com.pietrantuono.podcasts.addpodcast.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.AddPodcastPresenter;

import javax.inject.Inject;

public class AddPodcastFragment extends Fragment implements AddPodcastView {
    public static final String TAG = "AddPodcastFragment";
    @Inject AddPodcastPresenter addPodcastPresenter;

    public static AddPodcastFragment newInstance() {
        return new AddPodcastFragment();
    }

    public static void navigateTo(FragmentManager fragmentManager){
        AddPodcastFragment frag = (AddPodcastFragment) fragmentManager.findFragmentByTag(AddPodcastFragment.TAG);
        if (frag == null) {
            frag = AddPodcastFragment.newInstance();
        }
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, frag, AddPodcastFragment.TAG).commit();
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
