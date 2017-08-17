package com.pietrantuono.podcasts.addpodcast.singlepodcast.view


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.apis.PodcastEpisode
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.subscribedpodcasts.detail.di.SingleSubscribedModule
import com.pietrantuono.podcasts.subscribedpodcasts.detail.presenter.SingleSubscribedPodcastPresenter
import com.pietrantuono.podcasts.subscribedpodcasts.detail.views.SingleSubscribedPodcastView
import com.pietrantuono.podcasts.subscribedpodcasts.detail.views.SingleSubscribedPodcastsRecycler
import com.pietrantuono.podcasts.utils.ARTWORK
import com.pietrantuono.podcasts.utils.SINGLE_PODCAST_TRACK_ID
import com.pietrantuono.podcasts.utils.STARTED_WITH_TRANSITION
import javax.inject.Inject

class SingleSubscribedPodcastActivity : DetailActivtyBase(), SingleSubscribedPodcastView {
    @BindView(R.id.recycler) lateinit var recyclerView: SingleSubscribedPodcastsRecycler
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
        progressBar.visibility = View.GONE
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

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return presenter.onCreateOptionsMenu(menu!!)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return presenter.onPrepareOptionsMenu(menu!!)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return presenter.onOptionsItemSelected(item)
    }
}

