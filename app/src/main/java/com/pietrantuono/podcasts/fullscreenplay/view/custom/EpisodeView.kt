package com.pietrantuono.podcasts.fullscreenplay.view.custom

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.drawable.GradientDrawable
import android.support.graphics.drawable.VectorDrawableCompat
import android.support.v4.graphics.ColorUtils
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.ColorForBackgroundAndText
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.EpisodeViewModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.databinding.EpisodeViewBinding


class EpisodeView : RelativeLayout {
    private val binding: EpisodeViewBinding

    companion object {
        private val TRANSPARENCY: Float = 80f
        private val RADIUS = 32f
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        binding = DataBindingUtil
                .inflate(context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                        R.layout.episode_view, this@EpisodeView, true)
    }

    fun setEpisode(episode: Episode?) {
        episode?.let {
            binding.viewModel = EpisodeViewModel(episode, ResourcesProvider(context))
            decideWhatToShow(episode)
        }
    }

    fun setColors(colorForBackgroundAndText: ColorForBackgroundAndText?) {
        colorForBackgroundAndText?.let {
            setBackgroundColor(it)
            setTextColor(it)
        }
    }

    private fun setTextColor(it: ColorForBackgroundAndText) {
        it.bodyTextColor?.let {
            binding.author.setTextColor(it)
            binding.summary.setTextColor(it)
            binding.date.setTextColor(it)
            binding.duration.setTextColor(it)
            binding.description.setTextColor(it)
        }
        it.bodyTextColor?.let {
            tintVector(it)
        }
        it.titleTextColor?.let {
            binding.title.setTextColor(it)
        }
    }

    private fun tintVector(color: Int) {
        DrawableCompat.setTint(DrawableCompat.wrap(VectorDrawableCompat
                .create(getResources(), R.drawable.ic_access_time_black_24dp, null)!!), color)
        binding.timeImage.setImageDrawable(DrawableCompat.wrap(VectorDrawableCompat
                .create(getResources(), R.drawable.ic_access_time_black_24dp, null)!!))
    }

    private fun setBackgroundColor(it: ColorForBackgroundAndText) {
        it.backgroundColor?.let {
            setBackgroundDrawable(ColorUtils.setAlphaComponent(it, ((TRANSPARENCY / 100) * 255).toInt()))
        }
    }

    private fun decideWhatToShow(episode: Episode) {
        if (episode.summary == null) {
            binding.summary.visibility = View.GONE
        }
        if (episode.description == null) {
            binding.description.visibility = View.GONE
        }
        if (episode.description != null && episode.summary != null) {
            if (episode.description.equals(episode.summary, true)) {
                binding.description.visibility = View.GONE
            }
        }
    }

    fun setBackgroundDrawable(backgroundColor: Int) {
        binding.container.setBackgroundDrawable(GradientDrawable().apply {
            this.shape = GradientDrawable.RECTANGLE
            cornerRadii = floatArrayOf(RADIUS, RADIUS, RADIUS, RADIUS, RADIUS, RADIUS, RADIUS, RADIUS)
            setColor(backgroundColor)
        })
    }
}