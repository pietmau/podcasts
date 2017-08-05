package com.pietrantuono.podcasts.apis

import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.interfaces.PodcastEpisodeParser
import dagger.Module
import dagger.Provides

@Module
class ApiModule {

    @Provides
    internal fun provideSinglePodcastApi(crashlyticsWrapper: CrashlyticsWrapper, episodeparser: PodcastEpisodeParser): SinglePodcastApi {
        return SinglePodcastApiRetrofit(crashlyticsWrapper, episodeparser)
    }

}