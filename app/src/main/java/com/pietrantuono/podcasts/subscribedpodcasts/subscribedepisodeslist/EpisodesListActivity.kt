package com.pietrantuono.podcasts.addpodcast.singlepodcast.view


import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.apis.PodcastEpisode
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayActivity
import com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.di.SingleSubscribedModule
import com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.presenter.EpisodesListPresenter
import com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.views.EpisodedListRecycler
import com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.views.EpisodesListView
import com.pietrantuono.podcasts.utils.ARTWORK
import com.pietrantuono.podcasts.utils.SINGLE_PODCAST_TRACK_ID
import com.pietrantuono.podcasts.utils.STARTED_WITH_TRANSITION
import javax.inject.Inject

class EpisodesListActivity : DetailActivtyBase(), EpisodesListView {
    @BindView(R.id.recycler) lateinit var recycler: EpisodedListRecycler
    @Inject lateinit var presenter: EpisodesListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        initViews()
        loadImage()
    }

    private fun inject() {
        (application as App).applicationComponent?.with(SingleSubscribedModule(this@EpisodesListActivity))?.inject(this@EpisodesListActivity)
    }

    private fun initViews() {
        setContentView(R.layout.single_subscribed_podcast_activity)
        ButterKnife.bind(this@EpisodesListActivity)
        setUpActionBar()
        recycler.setOnItemClickListener(presenter)
        progressBar.visibility = View.GONE
    }

    override fun getImageUrl(): String? {
        return intent.getStringExtra(ARTWORK)
    }

    override fun setEpisodes(episodes: List<PodcastEpisode>) {
        recycler.setItems(episodes)
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun startDetailActivityWithTransition(podcast: Podcast?, imageView: ImageView?, titleContainer: LinearLayout?) {
        val intent = Intent(this@EpisodesListActivity, FullscreenPlayActivity::class.java)
        intent.putExtra(SINGLE_PODCAST_TRACK_ID, podcast?.trackId)
        intent.putExtra(ARTWORK, podcast?.artworkUrl600)
        intent.putExtra(STARTED_WITH_TRANSITION, true)
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this@EpisodesListActivity, *getPairs(imageView, titleContainer)).toBundle())
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun startDetailActivityWithoutTransition(podcast: Podcast?) {
        val intent = Intent(this@EpisodesListActivity, FullscreenPlayActivity::class.java)
        intent.putExtra(SINGLE_PODCAST_TRACK_ID, podcast?.trackId)
        intent.putExtra(ARTWORK, podcast?.artworkUrl600)
        startActivity(intent)
    }

    private fun getPairs(imageView: ImageView?, titleContainer: LinearLayout?): Array<Pair<View, String>> {
        return transitionsFramework.getPairs(imageView, this@EpisodesListActivity, titleContainer)
    }

    override fun isPartiallyHidden(position: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}

