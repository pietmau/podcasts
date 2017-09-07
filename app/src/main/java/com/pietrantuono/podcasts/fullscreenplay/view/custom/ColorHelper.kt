package com.pietrantuono.podcasts.fullscreenplay.view.custom

import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.ColorForBackgroundAndText
import com.pietrantuono.podcasts.databinding.EpisodeViewBinding

class ColorHelper {
    private fun setBodyTextColor(binding: EpisodeViewBinding, it: ColorForBackgroundAndText) {
        it.bodyTextColor?.let {
            binding.author.setTextColor(it)
            binding.summary.setTextColor(it)
            binding.date.setTextColor(it)
            binding.duration.setTextColor(it)
            binding.description.setTextColor(it)
            binding.dowloadedText.setTextColor(it)
        }
    }

    private fun setTitleTextColor(binding: EpisodeViewBinding, color: ColorForBackgroundAndText) {
        color.titleTextColor?.let {
            binding.title.setTextColor(it)
        }
    }

    fun setTextColors(binding: EpisodeViewBinding, color: ColorForBackgroundAndText) {
        setBodyTextColor(binding, color)
        setTitleTextColor(binding, color)
    }

}