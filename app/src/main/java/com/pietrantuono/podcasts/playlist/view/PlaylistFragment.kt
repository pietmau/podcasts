package com.pietrantuono.podcasts.playlist.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.playlist.di.PlaylistModule
import com.pietrantuono.podcasts.playlist.presenter.PlaylistPresenter
import javax.inject.Inject


class PlaylistFragment : Fragment() {
    @Inject lateinit var presenter: PlaylistPresenter

    companion object {
        const val TAG = "PlaylistFragment"

        fun navigateToPlaylist(fragmentManager: FragmentManager) {
            var frag = fragmentManager.findFragmentByTag(TAG) ?: PlaylistFragment()
            fragmentManager.beginTransaction()?.replace(R.id.fragmentContainer, frag, TAG)?.commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.applicationContext as? App)?.applicationComponent?.with(PlaylistModule())?.inject(this)
    }
}