package com.pietrantuono.podcasts.addpodcast.singlepodcast.view


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.customviews.SimpleContolView
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.custom.CoordinatorWithBottomMargin
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.subscribedpodcasts.detail.di.SingleSubscribedModule
import com.pietrantuono.podcasts.subscribedpodcasts.detail.presenter.SingleSubscribedPodcastPresenter
import com.pietrantuono.podcasts.subscribedpodcasts.detail.views.SingleSubscribedPodcastsRecycler
import javax.inject.Inject

class SingleSubscribedPodcastActivity : DetailActivtyBase() {
    private var isSubscribed: Boolean = false

    companion object {
        val SINGLE_PODCAST = "single_podcast"
        val STARTED_WITH_TRANSITION = "with_transition"
    }

    @BindView(R.id.recycler) lateinit var recyclerView: SingleSubscribedPodcastsRecycler
    @BindView(R.id.playbackcontrols) lateinit var playbackControls: SimpleContolView
    @BindView(R.id.coordinator) lateinit var coordinator: CoordinatorWithBottomMargin
    @Inject  lateinit var presenter: SingleSubscribedPodcastPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        inject()
        startPresenter()
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
    }

    private fun setUpPlayerControls() {
        coordinator.setUpPlayerControls(playbackControls)
    }

    private fun startPresenter() {
        presenter.bindView(this@SingleSubscribedPodcastActivity)
        presenter.startPresenter(intent
                .getParcelableExtra<SinglePodcast>(SINGLE_PODCAST), intent
                .getBooleanExtra(STARTED_WITH_TRANSITION, false))
    }

    override fun getImageUrl(): String? {
        return intent.getParcelableExtra<SinglePodcast>(SINGLE_PODCAST)?.getArtworkUrl600()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return presenter.onOptionsItemSelected(item.itemId)
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return presenter
    }

    override fun setEpisodes(episodes: List<PodcastEpisodeModel>?) {
        recyclerView.setItems(episodes)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun setSubscribedToPodcast(isSubscribed: Boolean) {
        this.isSubscribed = isSubscribed
        supportInvalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item = menu?.findItem(R.id.subscribe_unsubscribe)!!
        if (isSubscribed) {
            item.setTitle(R.string.unsubscribe)
        } else {
            item.setTitle(R.string.subscribe)
        }
        return true
    }

    override fun setTitle(collectionName: String?) {
        supportActionBar?.title = collectionName
    }
}

