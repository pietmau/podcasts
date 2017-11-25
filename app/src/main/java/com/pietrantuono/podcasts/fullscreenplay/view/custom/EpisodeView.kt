package com.pietrantuono.podcasts.fullscreenplay.view.custom

import android.content.Context
import android.databinding.DataBindingUtil
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.ColorForBackgroundAndText
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.EpisodeViewModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.databinding.EpisodeViewBinding
import pojos.Episode

class EpisodeView : RelativeLayout {
    private val binding: EpisodeViewBinding
    private val drawableHelper: DrawableHelper
    private val colorHelper: ColorHelper = ColorHelper()

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        binding = DataBindingUtil
                .inflate(context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                        R.layout.episode_view, this@EpisodeView, true)
        drawableHelper = DrawableHelper(context.resources)
    }

    fun setEpisode(episode: Episode?) {
        drawableHelper.episode = episode
        episode?.let {
            binding.viewModel = EpisodeViewModel(episode, ResourcesProvider(context))
            decideWhatToShow(episode)
            drawableHelper.tintDrawables(binding.dowloadedImage, binding.timeImage)
        }
    }

    fun setColors(colorForBackgroundAndText: ColorForBackgroundAndText?) {
        colorForBackgroundAndText?.let {
            drawableHelper.setBackgroundColor(binding.container, it, binding.dowloadedImage, binding.timeImage)
            colorHelper.setTextColors(binding, it)
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
}

