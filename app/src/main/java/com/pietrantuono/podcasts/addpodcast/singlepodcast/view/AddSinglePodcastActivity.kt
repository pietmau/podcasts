package com.pietrantuono.podcasts.addpodcast.singlepodcast.view

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.customviews.EpisodesAdapter
import com.pietrantuono.podcasts.addpodcast.singlepodcast.customviews.EpisodesRecycler
import com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger.SinglePodcastModule
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SinglePodcastPresenter
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayActivity
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayActivity.Companion.SHOULD_STREAM_AUDIO
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.main.view.TransitionsHelper
import com.pietrantuono.podcasts.utils.ARTWORK
import com.pietrantuono.podcasts.utils.EPISODE_URI
import com.pietrantuono.podcasts.utils.isInValidState
import models.pojos.Episode
import models.pojos.Podcast
import org.jetbrains.anko.intentFor
import javax.inject.Inject

open class AddSinglePodcastActivity : AbstractPlaybackControlsActivity(), BitmapColorExtractor.Callback,
        SinglePodcastView, EpisodesAdapter.OnItemClickListener {

    companion object {
        val SINGLE_PODCAST_TRACK_ID = "single_podcast_track_id"
        val STARTED_WITH_TRANSITION = "with_transition"
        val TAG = "AddSinglePodcastActivity"
    }

    private var toolbar: Toolbar? = null
    private var isSubscribed: Boolean? = false

    @BindView(R.id.recycler) lateinit var recyclerView: EpisodesRecycler
    @BindView(R.id.main_image) lateinit var imageView: ImageView
    @Inject lateinit var transitionsHelper: TransitionsHelper
    @Inject lateinit var colorExtractor: BitmapColorExtractor
    @Inject lateinit var imageLoader: SimpleImageLoader
    @Inject lateinit var presenter: SinglePodcastPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        initViews()
        startPresenter(savedInstanceState)
        loadImage()
    }

    private fun inject() {
        (applicationContext as App).appComponent?.with(SinglePodcastModule(this@AddSinglePodcastActivity))?.inject(this)
    }

    private fun initViews() {
        setContentView(R.layout.add_single_podcast_activity)
        ButterKnife.bind(this@AddSinglePodcastActivity)
        setUpActionBar()
        initPlaybackControls()
        recyclerView.setOnItemClickListener(this)
    }

    private fun startPresenter(savedInstanceState: Bundle?) {
        presenter.bindView(this@AddSinglePodcastActivity)
        presenter.startPresenter(intent?.getParcelableExtra<Podcast>(SINGLE_PODCAST_TRACK_ID),
                intent?.getBooleanExtra(STARTED_WITH_TRANSITION, false),
                savedInstanceState != null)
    }

    fun getImageUrl(): String? {
        return intent.getParcelableExtra<Podcast>(SINGLE_PODCAST_TRACK_ID)?.artworkUrl600
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return presenter.onOptionsItemSelected(item.itemId)
    }

    override fun setEpisodes(episodes: List<Episode>) {
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

    override fun exitWithoutSharedTransition() {}

    override fun exitWithSharedTrsnsition() {}

    override fun onItemClick(episode: Episode) {
        startActivity(intentFor<FullscreenPlayActivity>(EPISODE_URI to episode.uri, ARTWORK to episode.imageUrl, SHOULD_STREAM_AUDIO to true))
    }

    override fun showProgress(show: Boolean) {

    }

    override var title: String?
        get() = toolbar?.title?.toString()
        set(string) {
            toolbar?.let { it.title = string }
        }

    protected fun setUpActionBar() {
        toolbar = (findViewById(R.id.toolbar) as? Toolbar)
        toolbar?.let {
            setSupportActionBar(it)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.title = null
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.other_actions, menu)
        return true
    }

    fun loadImage() {
        imageLoader.displayImage(getImageUrl(), imageView,
                colorExtractor)
    }

    override fun onStart() {
        super.onStart()
        colorExtractor.callback = this
        presenter.onResume()
    }

    override fun onStop() {
        super.onStop()
        colorExtractor.callback = null
        presenter.onPause()
    }

    override fun onColorExtractionCompleted() {
        colorExtractor.backgroundColor?.let { supportActionBar?.setBackgroundDrawable(ColorDrawable(it)); }
        startTransitionPostponed()
    }

    override fun enterWithTransition() {
        transitionsHelper.initDetailTransitions(this)
    }

    override fun enterWithoutTransition() {
        overridePendingTransition(R.anim.pop_in, R.anim.pop_out)
    }

    fun startTransitionPostponed() {
        if (isInValidState()) {
            transitionsHelper.startPostponedEnterTransition(this)
        }
    }

}
