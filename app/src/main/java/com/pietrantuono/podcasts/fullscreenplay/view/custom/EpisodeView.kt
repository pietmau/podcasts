package com.pietrantuono.podcasts.fullscreenplay.view.custom

import android.content.Context
import android.databinding.DataBindingUtil
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.ColorForBackgroundAndText
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.databinding.EpisodeViewBinding

class EpisodeView : RelativeLayout {
    private val binding: EpisodeViewBinding

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val inflater: LayoutInflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.episode_view, this@EpisodeView, true)
    }

    fun setEpisode(episode: Episode?) {
        binding.episode = episode

    }

    fun setColors(colorForBackgroundAndText: ColorForBackgroundAndText?) {
        colorForBackgroundAndText?.let {
            it.backgroundColor?.let { setBackgroundColor(it) }
            it.bodyTextColor?.let {
                binding.author.setTextColor(it)
                binding.subtitle.setTextColor(it)
                binding.summary.setTextColor(it)
            }
            it.titleTextColor?.let {
                binding.title.setTextColor(it)
            }
        }
    }

}