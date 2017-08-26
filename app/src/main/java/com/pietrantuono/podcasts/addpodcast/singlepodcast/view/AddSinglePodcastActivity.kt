package com.pietrantuono.podcasts.addpodcast.singlepodcast.view


import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.view.Menu
import android.view.MenuItem
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.customviews.EpisodesRecycler
import com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger.SinglePodcastModule
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SinglePodcastPresenter
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.application.App
import javax.inject.Inject

class AddSinglePodcastActivity : AbstractBaseDetailActivty(), SinglePodcastView {
    private var isSubscribed: Boolean? = false

    companion object {
        val SINGLE_PODCAST_TRACK_ID = "single_podcast_track_id"
        val STARTED_WITH_TRANSITION = "with_transition"
        val TAG = "AddSinglePodcastActivity"
    }

    @BindView(R.id.recycler) lateinit var recyclerView: EpisodesRecycler
    @BindView(R.id.coordinator) lateinit var coordinator: CoordinatorLayout
    @Inject lateinit var presenter: SinglePodcastPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        initViews()
        startPresenter()
        loadImage()
    }

    private fun inject() {
        (applicationContext as App).applicationComponent?.with(SinglePodcastModule(this@AddSinglePodcastActivity))?.inject(this)
    }

    private fun initViews() {
        setContentView(R.layout.add_single_podcast_activity)
        ButterKnife.bind(this@AddSinglePodcastActivity)
        setUpActionBar()
        initPlaybackControls()
        initProgress()
    }

    private fun startPresenter() {
        presenter.bindView(this@AddSinglePodcastActivity)
        presenter.startPresenter(intent
                .getParcelableExtra<Podcast>(SINGLE_PODCAST_TRACK_ID), intent
                .getBooleanExtra(STARTED_WITH_TRANSITION, false))
    }

    override fun getImageUrl(): String? {
        return intent.getParcelableExtra<Podcast>(SINGLE_PODCAST_TRACK_ID)?.artworkUrl600
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

    override fun setEpisodes(episodes: List<Episode>?) {
        recyclerView.setItems(episodes)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun setSubscribedToPodcast(isSubscribed: Boolean?) {
        this.isSubscribed = isSubscribed
        supportInvalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item = menu?.findItem(R.id.subscribe_unsubscribe)!!
        if (isSubscribed == true) {
            item.setTitle(R.string.unsubscribe)
        } else {
            item.setTitle(R.string.subscribe)
        }
        return true
    }

    override fun exitWithoutSharedTransition() {//TODO remove
        finish()
        overridePendingTransition(R.anim.pop_in, R.anim.pop_out)
    }

    override fun exitWithSharedTrsnsition() {//TODO remove
        super.onBackPressed()
    }
}
