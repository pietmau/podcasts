package com.pietrantuono.podcasts.addpodcast.singlepodcast.view

import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.ImageView
import butterknife.BindView
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.custom.SimpleProgressBar
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import javax.inject.Inject


open abstract class DetailActivtyBase : BaseActivity(), BitmapColorExtractor.Callback {
    @Inject lateinit var imageLoader: SimpleImageLoader
    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.progress) lateinit var progressBar: SimpleProgressBar
    @BindView(R.id.main_image) lateinit var imageView: ImageView
    @Inject lateinit var transitionsFramework: TransitionsFramework
    @Inject lateinit var colorExtractor: BitmapColorExtractor

    protected fun setUpActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.other_actions, menu)
        return true
    }

    fun showProgress(show: Boolean) {
        progressBar.showProgress = show
    }

    fun enterWithTransition() {
        transitionsFramework.initDetailTransitions(this)
    }

    fun enterWithoutTransition() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    fun exitWithoutSharedTransition() {
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left)
    }

    fun exitWithSharedTrsnsition() {
        super.onBackPressed()
    }

    open fun loadImage() {
        imageLoader.displayImage(getImageUrl(), imageView,
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
        transitionsFramework.startPostponedEnterTransition(this)
    }

    abstract fun getImageUrl(): String?

}