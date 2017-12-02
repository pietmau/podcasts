package player.model

import models.pojos.Episode


class SourceExtractorImpl : SourceExtractor {

    override fun getSource(episode: Episode): String? {
        if (episode.downloaded) {
            return getSourceFromFile(episode)
        }
        return return getSourceFromUrl(episode)
    }

    private fun getSourceFromFile(episode: Episode): String? = episode.filePathIncludingFileName

    private fun getSourceFromUrl(episode: Episode): String? {
        val enclosures = episode.enclosures
        if (enclosures != null && enclosures.size > 0) {
            return enclosures[0].url
        }
        return null
    }
}

interface SourceExtractor {
    fun getSource(episode: Episode): String?
}
