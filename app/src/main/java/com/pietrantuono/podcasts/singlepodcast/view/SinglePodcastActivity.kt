package com.pietrantuono.podcasts.singlepodcast.view


import android.os.Bundle
import android.view.MenuItem
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.pietrantuono.podcasts.PresenterManager
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.singlepodcast.customviews.EpisodesRecycler
import com.pietrantuono.podcasts.singlepodcast.customviews.SimpleContolView
import com.pietrantuono.podcasts.singlepodcast.dagger.SinglePodcastModule
import com.pietrantuono.podcasts.singlepodcast.presenter.SinglePodcastPresenter
import com.pietrantuono.podcasts.singlepodcast.view.custom.CoordinatorWithBottomMargin
import com.pietrantuono.podcasts.singlepodcast.view.custom.SubscribedTextView
import javax.inject.Inject

class SinglePodcastActivity : DetailActivtyBase() {
    private var cachedHeight: Int = 0

    companion object {
        val SINGLE_PODCAST = "single_podcast"
        val STARTED_WITH_TRANSITION = "with_transition"
    }

    @BindView(R.id.recycler) lateinit var recyclerView: EpisodesRecycler
    @BindView(R.id.subscribeunsubscribe) lateinit var subscribedTextView: SubscribedTextView
    @BindView(R.id.playbackcontrols) lateinit var playbackControls: SimpleContolView
    @BindView(R.id.coordinator) lateinit var coordinator: CoordinatorWithBottomMargin
    @Inject lateinit var presenter: SinglePodcastPresenter
    @Inject lateinit var presenterManager: PresenterManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        inject()
        startPresenter()
        loadImage()
    }

    private fun inject() {
        (applicationContext as App).applicationComponent?.with(SinglePodcastModule(this@SinglePodcastActivity))?.inject(this)
    }

    private fun initViews() {
        setContentView(R.layout.activity_podcast)
        ButterKnife.bind(this@SinglePodcastActivity)
        setUpActionBar()
        setUpPlayerControls()
    }

    private fun setUpPlayerControls() {
        playbackControls.addOnGlobalLayoutListener {
            setBottomMargin()
        }
        playbackControls.setVisibilityListener {
            setBottomMargin()
        }
        setBottomMargin()
    }

    private fun setBottomMargin() {
        if (playbackControls.visibility == View.VISIBLE) {
            coordinator.setBottomMargin(cachedHeight)
        } else {
            coordinator.setBottomMargin(0)
        }
    }

    private fun startPresenter() {
        presenter.bindView(this@SinglePodcastActivity)
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

    @OnClick(R.id.subscribeunsubscribe)
    fun onSubscribeUnsubscribeClicked() {
        presenter.onSubscribeUnsubscribeToPodcastClicked()
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return presenterManager
    }

    override fun setEpisodes(episodes: List<PodcastEpisodeModel>?) {
        recyclerView.setItems(episodes)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun setSubscribedToPodcast(isSubscribed: Boolean) {
        subscribedTextView.isSubsribed = isSubscribed
    }
}
