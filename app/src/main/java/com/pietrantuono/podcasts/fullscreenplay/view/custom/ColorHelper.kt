package com.pietrantuono.podcasts.fullscreenplay.view.custom

import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.ColorForBackgroundAndText
import com.pietrantuono.podcasts.databinding.EpisodeViewBinding

class ColorHelper {

    fun setTextColors(binding: EpisodeViewBinding, color: ColorForBackgroundAndText) {
        color.bodyTextColor?.let {
            binding.author.setTextColor(it)
            binding.summary.setTextColor(it)
            binding.date.setTextColor(it)
            binding.duration.setTextColor(it)
            binding.description.setTextColor(it)
            binding.dowloadedText.setTextColor(it)
            binding.title.setTextColor(it)
            binding.playedText.setTextColor(it)
        }
    }

}