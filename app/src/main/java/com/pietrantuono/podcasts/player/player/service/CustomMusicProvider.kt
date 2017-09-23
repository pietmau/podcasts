package com.pietrantuono.podcasts.player.player.service

import com.example.android.uamp.model.MusicProvider
import com.pietrantuono.podcasts.repository.EpisodesRepository


class CustomMusicProvider(private val repo: EpisodesRepository) : MusicProvider() {
}