package com.pietrantuono.podcasts.addpodcast.singlepodcast.view

import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.ImageView
import butterknife.BindView
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.custom.SimpleProgressBar
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.main.view.Transitions
import com.pietrantuono.podcasts.utils.isInValidState
import javax.inject.Inject


abstract class AbstractBaseDetailActivty : AbstractPlaybackControlsActivity(), BitmapColorExtractor.Callback {
    var progressBar: SimpleProgressBar? = null
    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.main_image) lateinit var imageView: ImageView
    @Inject lateinit var transitions: Transitions
    @Inject lateinit var colorExtractor: BitmapColorExtractor
    @Inject lateinit var imageLoader: SimpleImageLoader

    var title: String?
        get() = toolbar?.title?.toString()
        set(string) {
            toolbar?.let { it.setTitle(string) }
        }

    protected fun setUpActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = null
    }

    fun initProgress() {
        progressBar = findViewById(R.id.progress) as SimpleProgressBar?
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.other_actions, menu)
        return true
    }

    fun showProgress(show: Boolean) {
        progressBar?.showProgress = show
    }

    fun loadImage() {
        imageLoader.displayImage(getImageUrl(), imageView,
                colorExtractor)
    }

    fun loadImage(url: String?) {
        imageLoader.displayImage(url, imageView,
                colorExtractor)
    }

    override fun onStart() {
        super.onStart()
        colorExtractor.callback = this
    }

    override fun onStop() {
        super.onStop()
        colorExtractor.callback = null
    }

    override fun onColorExtractionCompleted() {
        colorExtractor.backgroundColor?.let { getSupportActionBar()?.setBackgroundDrawable(ColorDrawable(it)); }
        transitions.startPostponedEnterTransition(this)
    }

    abstract fun getImageUrl(): String?

    fun enterWithTransition() {
        transitions.initDetailTransitions(this)
    }

    fun enterWithoutTransition() {
        overridePendingTransition(R.anim.pop_in, R.anim.pop_out)
    }

    fun startTransitionPostponed() {
        if (isInValidState()) {
            transitions.startPostponedEnterTransition(this)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.pop_in, R.anim.pop_out)
    }

    protected fun setToolbarColor(backgroundColor: Int) {
        toolbar.setBackgroundColor(backgroundColor)
    }

}