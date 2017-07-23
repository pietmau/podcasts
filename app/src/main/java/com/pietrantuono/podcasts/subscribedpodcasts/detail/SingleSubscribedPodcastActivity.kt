package com.pietrantuono.podcasts.addpodcast.singlepodcast.view


import android.os.Bundle
import android.view.MenuItem
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.customviews.SimpleContolView
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.custom.CoordinatorWithBottomMargin
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.providers.RealmPodcastEpisodeModel
import com.pietrantuono.podcasts.subscribedpodcasts.detail.di.SingleSubscribedModule
import com.pietrantuono.podcasts.subscribedpodcasts.detail.presenter.SingleSubscribedPodcastPresenter
import com.pietrantuono.podcasts.subscribedpodcasts.detail.views.SingleSubscribedPodcastView
import com.pietrantuono.podcasts.subscribedpodcasts.detail.views.SingleSubscribedPodcastsRecycler
import javax.inject.Inject

class SingleSubscribedPodcastActivity : DetailActivtyBase(), SingleSubscribedPodcastView {
    private var isSubscribed: Boolean = false

    companion object {
        val SINGLE_PODCAST = "single_podcast"
        val STARTED_WITH_TRANSITION = "with_transition"
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
    }

    private fun setUpPlayerControls() {
        coordinator.setUpPlayerControls(playbackControls)
    }

    private fun startPresenter() {
        presenter.startPresenter(intent
                .getParcelableExtra<SinglePodcast>(SINGLE_PODCAST), intent
                .getBooleanExtra(STARTED_WITH_TRANSITION, false))
    }

    override fun getImageUrl(): String? {
        return intent.getParcelableExtra<SinglePodcast>(SINGLE_PODCAST)?.getArtworkUrl600()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return presenter.onOptionsItemSelected(item.itemId)
    }

    override fun setEpisodes(episodes: List<RealmPodcastEpisodeModel>?) {
        recyclerView.setItems(episodes)
    }


    override fun setTitle(collectionName: String?) {
        supportActionBar?.title = collectionName
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart(this)
        startPresenter()
    }
}
