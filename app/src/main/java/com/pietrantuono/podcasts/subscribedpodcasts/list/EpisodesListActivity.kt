package com.pietrantuono.podcasts.addpodcast.singlepodcast.view


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayActivity
import com.pietrantuono.podcasts.subscribedpodcasts.list.di.SingleSubscribedModule
import com.pietrantuono.podcasts.subscribedpodcasts.list.presenter.EpisodesListPresenter
import com.pietrantuono.podcasts.subscribedpodcasts.list.views.EpisodedListRecycler
import com.pietrantuono.podcasts.subscribedpodcasts.list.views.EpisodesListView
import com.pietrantuono.podcasts.utils.*
import models.pojos.Episode
import javax.inject.Inject

class EpisodesListActivity : AbstractBaseDetailActivty(), EpisodesListView {
    @BindView(R.id.recycler) lateinit var recycler: EpisodedListRecycler
    @Inject lateinit var presenter: EpisodesListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        initViews()
        loadImage()
        presenter.onCreate(savedInstanceState!=null)
    }

    private fun inject() {
        (application as App).applicationComponent?.with(SingleSubscribedModule(this@EpisodesListActivity))?.inject(this@EpisodesListActivity)
    }

    private fun initViews() {
        setContentView(R.layout.single_subscribed_podcast_activity)
        initPlaybackControls()
        ButterKnife.bind(this@EpisodesListActivity)
        setUpActionBar()
        recycler.setOnItemClickListener(presenter)
    }

    override fun getImageUrl(): String? {
        return intent.getStringExtra(ARTWORK)
    }

    override fun setEpisodes(episodes: List<Episode>) {
        recycler.setItems(episodes)
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

    override fun startDetailActivityWithoutTransition(episode: Episode) {
        startActivity(getIntent(episode))
    }

    private fun getIntent(episode: Episode): Intent {
        val intent = Intent(this@EpisodesListActivity, FullscreenPlayActivity::class.java)
        intent.putExtra(EPISODE_URI, episode.uri)
        intent.putExtra(ARTWORK, episode.imageUrl)
        colorExtractor.backgroundColor?.let { intent.putExtra(BACKGROUND_COLOR, it) }
        return intent
    }

    override fun isPartiallyHidden(position: Int): Boolean {
        return recycler.isPartiallyHidden(position)
    }

    override fun exitWithSharedTrsnsition() {
        super.onBackPressed()
    }

    override fun exitWithoutSharedTransition() {
        super.finish()
        overridePendingTransition(R.anim.pop_in, R.anim.pop_out)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

}

