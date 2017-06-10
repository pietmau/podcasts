package com.pietrantuono.podcasts.singlepodcast.view


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.pietrantuono.podcasts.PresenterManager
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.main.view.TransitionsFramework
import com.pietrantuono.podcasts.singlepodcast.customviews.EpisodesRecycler
import com.pietrantuono.podcasts.singlepodcast.dagger.SinglePodcastModule
import com.pietrantuono.podcasts.singlepodcast.presenter.SinglePodcastPresenter
import com.pietrantuono.podcasts.singlepodcast.view.custom.SimpleProgressBar
import com.pietrantuono.podcasts.singlepodcast.view.custom.SubscribedTextView
import javax.inject.Inject

class SinglePodcastActivity : AppCompatActivity(), SinglePodcastView {
    companion object {
        val SINGLE_PODCAST = "single_podcast"
        val STARTED_WITH_TRANSITION = "with_transition"
    }

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.main_image) lateinit var imageView: ImageView
    @BindView(R.id.recycler) lateinit var recyclerView: EpisodesRecycler
    @BindView(R.id.progress) lateinit var progressBar: SimpleProgressBar
    @BindView(R.id.subscribeunsubscribe) lateinit var subscribedTextView: SubscribedTextView
    @Inject lateinit var transitionsFramework: TransitionsFramework
    @Inject lateinit var imageLoader: SimpleImageLoader
    @Inject lateinit var presenter: SinglePodcastPresenter
    @Inject lateinit var presenterManager: PresenterManager
    @Inject lateinit var podcastImageLoadingListener: TransitionImageLoadingListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        inject()
        startPresenter()
        loadImage()
    }

    private fun inject() {
        (applicationContext as App).applicationComponent
                .with(SinglePodcastModule(this@SinglePodcastActivity)).inject(this)
    }

    override fun enterWithTransition() {
        transitionsFramework.initDetailTransitions(this@SinglePodcastActivity)
    }

    override fun enterWithoutTransition() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    private fun initViews() {
        setContentView(R.layout.activity_podcast)
        ButterKnife.bind(this@SinglePodcastActivity)
        setUpActionBar()
    }

    private fun startPresenter() {
        presenter.bindView(this@SinglePodcastActivity)
        presenter.startPresenter(intent
                .getParcelableExtra<SinglePodcast>(SINGLE_PODCAST), intent
                .getBooleanExtra(STARTED_WITH_TRANSITION, false))
    }

    private fun setUpActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = null

    }

    private fun loadImage() {
        imageLoader.displayImage(intent.getParcelableExtra<SinglePodcast>(SINGLE_PODCAST), imageView,
                podcastImageLoadingListener)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
        podcastImageLoadingListener.setActivity(null)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
        podcastImageLoadingListener.setActivity(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.other_actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return presenter.onOptionsItemSelected(item.itemId)
    }

    @OnClick(R.id.subscribeunsubscribe)
    fun onSubscribeUnsubscribeClicked() {
        presenter.onSubscribeUnsubscribeToPodcastClicked()
    }

    override fun showProgress(show: Boolean) {
        progressBar.showProgress = show
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

    override fun exitWithSharedTrsnsition() {
        super.onBackPressed()
    }

    override fun exitWithoutSharedTransition() {
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    override fun setSubscribedToPodcast(isSubscribed: Boolean) {
        subscribedTextView.isSubsribed = isSubscribed
    }
}
