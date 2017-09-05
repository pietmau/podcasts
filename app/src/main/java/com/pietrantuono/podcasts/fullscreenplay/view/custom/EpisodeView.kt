package com.pietrantuono.podcasts.fullscreenplay.view.custom

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.graphics.ColorUtils
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
        private val TRANSPARENCY: Float = 90f
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
            it.backgroundColor?.let {
                val transprency = ((TRANSPARENCY / 100) * 255).toInt()
                val color = ColorUtils.setAlphaComponent(it, transprency)
                binding.author.setBackgroundColor(color)
                binding.summary.setBackgroundColor(color)
                binding.title.setBackgroundColor(color)
                binding.date.setBackgroundColor(color)
                binding.duration.setBackgroundColor(color)
                binding.description.setBackgroundColor(color)
            }
            it.bodyTextColor?.let {
                binding.author.setTextColor(it)
                binding.summary.setTextColor(it)
                binding.date.setTextColor(it)
                binding.duration.setTextColor(it)
                binding.description.setTextColor(it)
            }
            it.titleTextColor?.let {
                binding.title.setTextColor(it)
            }
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