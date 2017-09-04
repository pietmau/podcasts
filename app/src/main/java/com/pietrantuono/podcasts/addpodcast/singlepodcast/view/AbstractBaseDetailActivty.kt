package com.pietrantuono.podcasts.addpodcast.singlepodcast.view

import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.ImageView
import butterknife.BindView
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.custom.SimpleProgressBar
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.main.view.TransitionsHelper
import com.pietrantuono.podcasts.utils.BACKGROUND_COLOR
import com.pietrantuono.podcasts.utils.isInValidState
import javax.inject.Inject


abstract class AbstractBaseDetailActivty : AbstractPlaybackControlsActivity(), BitmapColorExtractor.Callback {
    var progressBar: SimpleProgressBar? = null
    var toolbar: Toolbar? = null
    @BindView(R.id.main_image) lateinit var imageView: ImageView
    @Inject lateinit var transitionsHelper: TransitionsHelper
    @Inject lateinit var colorExtractor: BitmapColorExtractor
    @Inject lateinit var imageLoader: SimpleImageLoader

    var title: String?
        get() = toolbar?.title?.toString()
        set(string) {
            toolbar?.let { it.setTitle(string) }
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
        startTransitionPostponed()
    }

    abstract fun getImageUrl(): String?

    open fun enterWithTransition() {
        transitionsHelper.initDetailTransitions(this)
    }

    open fun enterWithoutTransition() {
        overridePendingTransition(R.anim.pop_in, R.anim.pop_out)
    }

    fun startTransitionPostponed() {
        if (isInValidState()) {
            transitionsHelper.startPostponedEnterTransition(this)
        }
    }

    protected fun setToolbarColor(backgroundColor: Int) {
        toolbar?.setBackgroundColor(backgroundColor)
    }

    fun getBackgroundColor() = intent?.getIntExtra(BACKGROUND_COLOR, resources.getColor(R.color.colorPrimary)) ?: resources.getColor(R.color.colorPrimary)

}