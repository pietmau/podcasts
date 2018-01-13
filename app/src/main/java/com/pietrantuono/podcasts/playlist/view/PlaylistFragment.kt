package com.pietrantuono.podcasts.playlist.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayActivity
import com.pietrantuono.podcasts.playlist.di.PlaylistModule
import com.pietrantuono.podcasts.playlist.presenter.PlaylistPresenter
import com.pietrantuono.podcasts.playlist.view.custom.PlaylistRecycler
import com.pietrantuono.podcasts.utils.ARTWORK
import com.pietrantuono.podcasts.utils.EPISODE_URI
import models.pojos.Episode
import org.jetbrains.anko.intentFor
import javax.inject.Inject

class PlaylistFragment : Fragment(), PlayListView {

    override var currentlyPlayingMediaId: String? = null
        set(uri) {
            recycler.currentlyPlayingMediaId = uri
        }

    @Inject lateinit var presenter: PlaylistPresenter
    @BindView(R.id.recycler) lateinit var recycler: PlaylistRecycler

    companion object {
        const val TAG = "PlaylistFragment"
        fun navigateToPlaylist(fragmentManager: FragmentManager) {
            var frag = fragmentManager.findFragmentByTag(TAG) ?: PlaylistFragment()
            fragmentManager.beginTransaction()?.replace(R.id.fragmentContainer, frag, TAG)?.commit()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.playlist_fragment, container, false)
        ButterKnife.bind(this, view!!)
        recycler.callback = { episode ->
            activity.startActivity(activity.intentFor<FullscreenPlayActivity>(EPISODE_URI to episode.uri, ARTWORK to episode.imageUrl, FullscreenPlayActivity.SHOULD_STREAM_AUDIO to true))
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.applicationContext as? App)?.appComponent?.with(PlaylistModule())?.inject(this)
    }

    override fun onPlaylistRetrieved(playlist: List<Episode>) {}

    override fun onEpisodeRetrieved(episode: Episode) {
        recycler.addEpisode(episode)
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbind()
    }

}