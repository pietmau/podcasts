package com.pietrantuono.podcasts.addpodcast.singlepodcast.view


import android.os.Bundle
import android.view.Menu
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.customviews.SimpleContolView
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.custom.CoordinatorWithBottomMargin
import com.pietrantuono.podcasts.apis.PodcastEpisode
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.subscribedpodcasts.detail.di.SingleSubscribedModule
import com.pietrantuono.podcasts.subscribedpodcasts.detail.presenter.SingleSubscribedPodcastPresenter
import com.pietrantuono.podcasts.subscribedpodcasts.detail.views.SingleSubscribedPodcastView
import com.pietrantuono.podcasts.subscribedpodcasts.detail.views.SingleSubscribedPodcastsRecycler
import javax.inject.Inject

class SingleSubscribedPodcastActivity : DetailActivtyBase(), SingleSubscribedPodcastView {
    companion object {
        val STARTED_WITH_TRANSITION = "with_transition"
        val SINGLE_PODCAST_TRACK_ID: String? = "track_id"
        val ARTWORK: String? = "artwork"
    }

    @BindView(R.id.recycler) lateinit var recyclerView: SingleSubscribedPodcastsRecycler
    @BindView(R.id.playbackcontrols) lateinit var playbackControls: SimpleContolView
    @BindView(R.id.coordinator) lateinit var coordinator: CoordinatorWithBottomMargin
    @Inject lateinit var presenter: SingleSubscribedPodcastPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        inject()
        loadImage()
    }

    private fun inject() {
        (application as App).applicationComponent?.with(SingleSubscribedModule(this@SingleSubscribedPodcastActivity))?.inject(this@SingleSubscribedPodcastActivity)
    }

    private fun initViews() {
        setContentView(R.layout.single_subscribed_podcast_activity)
        ButterKnife.bind(this@SingleSubscribedPodcastActivity)
        setUpActionBar()
        setUpPlayerControls()
        progressBar.visibility = View.GONE
    }

    private fun setUpPlayerControls() {
        coordinator.setUpPlayerControls(playbackControls)
    }

    override fun getImageUrl(): String? {
        return intent.getStringExtra(ARTWORK)
    }

    override fun setEpisodes(episodes: List<PodcastEpisode>?) {
        recyclerView.setItems(episodes)
    }

    override fun setTitle(collectionName: String?) {
        supportActionBar?.title = collectionName
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart(this, intent
                .getIntExtra(SINGLE_PODCAST_TRACK_ID, -1), intent
                .getBooleanExtra(STARTED_WITH_TRANSITION, false))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return presenter.onCreateOptionsMenu(menu!!)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return presenter.onPrepareOptionsMenu(menu!!)
    }
}

