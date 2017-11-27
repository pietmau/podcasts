package com.pietrantuono.podcasts.apis

import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.interfaces.PodcastEpisodeParser
import com.pietrantuono.podcasts.interfaces.ROMEPodcastFeed
import com.rometools.rome.feed.synd.SyndFeed
import com.rometools.rome.io.SyndFeedInput
import com.rometools.rome.io.XmlReader
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type

internal class PodcastFeedConverterFactory(private val crashlyticsWrapper: CrashlyticsWrapper, private val episodeparser: PodcastEpisodeParser) : Converter.Factory() {
    private val input = SyndFeedInput()

    override fun responseBodyConverter(type: Type?, annotations: Array<Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *> {

        return Converter<ResponseBody, PodcastFeed> { value ->
            try {
                parseFeed(input.build(XmlReader(value.byteStream())))
            } catch (e: Throwable) {
                crashlyticsWrapper.logException(e)
                throw IOException(e)
            }
        }
    }

    private fun parseFeed(syndFeed: SyndFeed): PodcastFeed = ROMEPodcastFeed(episodeparser.parseFeed(syndFeed))

}
