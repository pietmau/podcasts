package com.pietrantuono.podcasts.addpodcast.view;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.pietrantuono.podcasts.R;
import com.pietrantuono.podcasts.addpodcast.AddPodcastPresenter;
import com.pietrantuono.podcasts.addpodcast.dagger.AddPodcastComponent;
import com.pietrantuono.podcasts.addpodcast.dagger.AddPodcastModule;
import com.pietrantuono.podcasts.main.dagger.DaggerMainComponent;
import com.pietrantuono.podcasts.main.dagger.MainComponent;
import com.pietrantuono.podcasts.main.dagger.MainModule;
import com.pietrantuono.podcasts.main.view.MainActivity;

import javax.inject.Inject;
import javax.inject.Named;

import static android.content.Context.BIND_AUTO_CREATE;

public class AddPodcastFragment extends Fragment implements AddPodcastView {
    public static final String TAG = "AddPodcastFragment";
    @Inject AddPodcastPresenter addPodcastPresenter;
    @Inject Intent modelServiceIntent;
    @Inject @Named("Add") ServiceConnection modelServiceConnection;

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
}
