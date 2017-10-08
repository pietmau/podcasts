package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.SystemClock
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.fullscreenplay.custom.ColorizedPlaybackControlView
import com.pietrantuono.podcasts.fullscreenplay.di.FullscreenModule
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CustomControlsImpl(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs), CustomControls {
    private val pauseDrawable: Drawable
    private val playDrawable: Drawable
    @BindView(R.id.prev) lateinit var skipPrev: ImageView
    @BindView(R.id.next) lateinit var skipNext: ImageView
    @BindView(R.id.play_pause) lateinit var playPause: ImageView
    @BindView(R.id.startText) lateinit var start: TextView
    @BindView(R.id.endText) lateinit var end: TextView
    @BindView(R.id.seekBar1) lateinit var seekbar: SeekBar
    @BindView(R.id.line1) lateinit var line1: TextView
    @BindView(R.id.line2) lateinit var line2: TextView
    @BindView(R.id.line3) lateinit var line3: TextView
    @BindView(R.id.progressBar1) lateinit var loading: ProgressBar
    @BindView(R.id.controllers) lateinit var controllers: View
    @BindView(R.id.background_image) lateinit var backgroundImage: ImageView
    private val aHandler = Handler()
    var callback: ColorizedPlaybackControlView.Callback? = null
    @Inject lateinit var presenter: CustomControlsPresenter

    companion object {
        private val PROGRESS_UPDATE_INTERNAL: Long = 1000
        private val PROGRESS_UPDATE_INITIAL_INTERVAL: Long = 100
    }

    private val executorService = Executors.newSingleThreadScheduledExecutor()
    private var scheduleFuture: ScheduledFuture<*>? = null
    private var lastPlaybackState: PlaybackStateCompat? = null

    init {
        (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.custom_player, this)
        ButterKnife.bind(this)
        (context.applicationContext as App).applicationComponent?.with(FullscreenModule(context as FragmentActivity))?.inject(this)
        pauseDrawable = ContextCompat.getDrawable(context, R.drawable.uamp_ic_pause_white_48dp)
        playDrawable = ContextCompat.getDrawable(context, R.drawable.uamp_ic_play_arrow_white_48dp)
        skipNext.setOnClickListener {
            presenter.skipToNext()
        }
        skipPrev.setOnClickListener {
            presenter.skipToPrevious()
        }
        playPause.setOnClickListener {
            presenter.onPlayClicked()
        }
        seekbar.setOnSeekBarChangeListener(presenter)
        presenter.bindView(this)
    }

    override fun setPlaybackState(playbackStateCompat: PlaybackStateCompat) {
        lastPlaybackState = playbackStateCompat
    }

    override fun scheduleSeekbarUpdate() {
        stopSeekbarUpdate()
        if (!executorService.isShutdown) {
            scheduleFuture = executorService.scheduleAtFixedRate({ aHandler.post({ updateProgress() }) }, PROGRESS_UPDATE_INITIAL_INTERVAL,
                    PROGRESS_UPDATE_INTERNAL, TimeUnit.MILLISECONDS)
        }
    }

    override fun stopSeekbarUpdate() {
        scheduleFuture?.cancel(false)
    }

    fun onStart() {
        presenter.onStart()
    }

    fun onStop() {
        presenter.onStop()
    }

    fun onDestroy() {
        stopSeekbarUpdate()
        executorService.shutdown()
    }

    override fun updateMediaDescription(description: MediaDescriptionCompat) {
        line1.text = description.title
        line2.text = description.subtitle
    }

    override fun updateDuration(duration: Int) {
        seekbar.max = duration
        end.text = DateUtils.formatElapsedTime((duration / 1000).toLong())
    }

    override fun onError(state: PlaybackStateCompat) {
        callback?.onPlayerError(state.errorMessage)
    }

    override fun updateProgress() {
        lastPlaybackState?.let {
            var currentPosition = it.position
            if (it.state == PlaybackStateCompat.STATE_PLAYING) {
                currentPosition += ((SystemClock.elapsedRealtime() - it.lastPositionUpdateTime).toInt() * it.playbackSpeed).toLong()
            }
            seekbar.progress = currentPosition.toInt()
        }
    }

    fun setEpisode(episode: Episode?) {
        presenter.setEpisode(episode)
    }

    override fun setStartText(text: String?) {
        start.text = text
    }

    override fun onStatePlaying() {
        loading.visibility = View.INVISIBLE
        playPause.visibility = View.VISIBLE
        playPause.setImageDrawable(pauseDrawable)
        controllers.visibility = View.VISIBLE
        scheduleSeekbarUpdate()
    }

    override fun onStateNone() {
        loading.visibility = View.INVISIBLE
        playPause.visibility = View.VISIBLE
        playPause.setImageDrawable(playDrawable)
        stopSeekbarUpdate()
    }

    override fun onStatePaused() {
        controllers.visibility = View.VISIBLE
        onStateNone()
    }

    override fun onStateBuffering() {
        playPause.visibility = View.INVISIBLE
        loading.visibility = View.VISIBLE
        line3.setText(R.string.loading)
        stopSeekbarUpdate()
    }
}

