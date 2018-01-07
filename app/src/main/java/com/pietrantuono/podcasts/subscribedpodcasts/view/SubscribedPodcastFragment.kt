package com.pietrantuono.podcasts.subscribedpodcasts.view

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsRecycler
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.EpisodesListActivity
import com.pietrantuono.podcasts.main.view.MainActivity
import com.pietrantuono.podcasts.main.view.TransitionsHelper
import com.pietrantuono.podcasts.subscribedpodcasts.di.SubscribedPodcastModule
import com.pietrantuono.podcasts.subscribedpodcasts.presenter.SubscribedPodcastPresenter
import com.pietrantuono.podcasts.utils.ARTWORK
import com.pietrantuono.podcasts.utils.SINGLE_PODCAST_TRACK_ID
import com.pietrantuono.podcasts.utils.STARTED_WITH_TRANSITION
import models.pojos.Podcast
import javax.inject.Inject

class SubscribedPodcastFragment : Fragment(), SubscribedPodcastView {
    @Inject lateinit var presenter: SubscribedPodcastPresenter
    @Inject lateinit var transitions: TransitionsHelper
    @BindView(R.id.recycler) lateinit var recycler: PodcastsRecycler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).mainComponent?.with(SubscribedPodcastModule(activity))?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_subscribed, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onError(throwable: Throwable) {}

    override fun setPodcasts(list: List<Podcast>) {
        if (recycler.adapter.itemCount != list.size) {
            recycler.setItems(list)
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.bindView(this)
        recycler.setListeners(presenter)
        presenter.onResume()
    }

    override fun onStop() {
        super.onStop()
        presenter.onPause()
        presenter.bindView(null)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun startDetailActivityWithTransition(podcast: Podcast, imageView: ImageView, titleContainer: LinearLayout) {
        val intent = getDetailActivityIntent(podcast)
        intent.putExtra(STARTED_WITH_TRANSITION, true)
        activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *getPairs(imageView, titleContainer)).toBundle())
    }

    override fun startDetailActivityWithoutTransition(podcast: Podcast) {
        activity.startActivity(getDetailActivityIntent(podcast))
    }

    private fun getDetailActivityIntent(podcast: Podcast): Intent {
        val intent = Intent(activity, EpisodesListActivity::class.java)
        intent.putExtra(SINGLE_PODCAST_TRACK_ID, podcast.trackId)
        intent.putExtra(ARTWORK, podcast.artworkUrl600)
        return intent
    }

    private fun getPairs(imageView: ImageView, titleContainer: LinearLayout): Array<Pair<View, String>?> {
        return transitions.getPairs(imageView, activity, titleContainer)
    }

    override fun isPartiallyHidden(position: Int): Boolean {
        return recycler.isPartiallyHidden(position)
    }

    companion object {
        private val TAG = SubscribedPodcastFragment::class.java.simpleName

        fun navigateTo(fragmentManager: FragmentManager) {
            var frag: SubscribedPodcastFragment? = fragmentManager.findFragmentByTag(SubscribedPodcastFragment.TAG) as? SubscribedPodcastFragment
            if (frag == null) {
                frag = SubscribedPodcastFragment()
            }
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, frag, SubscribedPodcastFragment.TAG).commit()
        }
    }
}
