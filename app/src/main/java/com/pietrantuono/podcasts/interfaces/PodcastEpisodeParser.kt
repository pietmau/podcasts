package com.pietrantuono.podcasts.interfaces

import com.pietrantuono.podcasts.Constants

import com.rometools.modules.itunes.EntryInformation
import com.rometools.modules.mediarss.MediaEntryModule
import com.rometools.modules.mediarss.MediaModule
import com.rometools.rome.feed.synd.SyndEntry
import com.rometools.rome.feed.synd.SyndFeed
import diocan.pojos.Episode
import java.util.*
import javax.inject.Inject

class PodcastEpisodeParser @Inject constructor(private val imageParser: ImageParser) {

    fun parseFeed(feed: SyndFeed): List<Episode> {
        val episodes = ArrayList<Episode>()
        for (entry in feed.entries) {
            episodes.add(parseEntry(entry))
        }
        return episodes
    }

    private fun parseEntry(syndEntry: SyndEntry): Episode {
        val entryInformation = syndEntry.getModule(Constants.ITUNES_URI) as? EntryInformation
        val mediaEntryModule = syndEntry.getModule(MediaModule.URI) as? MediaEntryModule
        val romePodcastEpisodeBuilder = getRomePodcastEpisodeBuilder(syndEntry, entryInformation, mediaEntryModule)
        return romePodcastEpisodeBuilder.createROMEPodcastEpisode()
    }

    private fun getRomePodcastEpisodeBuilder(syndEntry: SyndEntry?, itunesEntryInformation: EntryInformation?, mediaEntryModule: MediaEntryModule?): ROMEPodcastEpisodeBuilder {
        var romePodcastEpisodeBuilder = ROMEPodcastEpisodeBuilder()
        romePodcastEpisodeBuilder = parseSyndEntry(syndEntry, parseItunesEntryInformation(itunesEntryInformation, romePodcastEpisodeBuilder))
        romePodcastEpisodeBuilder = parseImage(itunesEntryInformation, mediaEntryModule, romePodcastEpisodeBuilder)
        return romePodcastEpisodeBuilder
    }

    private fun parseSyndEntry(syndEntry: SyndEntry?, romePodcastEpisodeBuilder: ROMEPodcastEpisodeBuilder): ROMEPodcastEpisodeBuilder {
        romePodcastEpisodeBuilder.setPubDate(syndEntry?.publishedDate)
                .setDescription(syndEntry?.description?.value).setTitle(syndEntry?.title)
                .setEnclosures(syndEntry?.enclosures)
        romePodcastEpisodeBuilder.setLink(getLink(syndEntry))
        return romePodcastEpisodeBuilder
    }

    private fun getLink(syndEntry: SyndEntry?): String? = syndEntry?.let { it.link ?: getLinkFromEnclosures(it) }

    private fun getLinkFromEnclosures(syndEntry: SyndEntry): String? = syndEntry.enclosures?.let { if (it.isEmpty()) null else it[0].url }

    private fun parseItunesEntryInformation(itunesEntryInformation: EntryInformation?,
                                            romePodcastEpisodeBuilder: ROMEPodcastEpisodeBuilder): ROMEPodcastEpisodeBuilder {
        romePodcastEpisodeBuilder.setAuthor(itunesEntryInformation?.author)
                .setDuration(itunesEntryInformation?.duration?.toString()).setIsExplicit(itunesEntryInformation?.explicit)
                .setKeywords(Arrays.asList(*itunesEntryInformation?.keywords)).setSubtitle(itunesEntryInformation?.subtitle)
                .setSummary(itunesEntryInformation?.summary)
        return romePodcastEpisodeBuilder
    }

    private fun parseImage(itunesEntryInformation: EntryInformation?,
                           mediaEntryModule: MediaEntryModule?,
                           romePodcastEpisodeBuilder: ROMEPodcastEpisodeBuilder): ROMEPodcastEpisodeBuilder {
        romePodcastEpisodeBuilder.setImageUrl(imageParser.parseImage(itunesEntryInformation, mediaEntryModule))
        return romePodcastEpisodeBuilder
    }
}
