package com.pietrantuono.podcasts.fullscreenplay.view.custom

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.graphics.ColorUtils
import android.text.Html
import android.util.AttributeSet
import android.view.LayoutInflater
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
        private val TRANSPARENCY = 100
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val inflater: LayoutInflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.episode_view, this@EpisodeView, true)
    }

    fun setEpisode(episode: Episode?) {
        episode?.let { binding.episode = EpisodeViewModel(episode, ResourcesProvider(context)) }
    }

    fun setColors(colorForBackgroundAndText: ColorForBackgroundAndText?) {
        colorForBackgroundAndText?.let {
            it.backgroundColor?.let {
                val color = ColorUtils.setAlphaComponent(it, (TRANSPARENCY / 100) * 255)
                binding.author.setBackgroundColor(color)
                binding.summary.setBackgroundColor(color)
                binding.title.setBackgroundColor(color)
            }
            it.bodyTextColor?.let {
                binding.author.setTextColor(it)
                binding.summary.setTextColor(it)
            }
            it.titleTextColor?.let {
                binding.title.setTextColor(it)
            }
        }
    }

}