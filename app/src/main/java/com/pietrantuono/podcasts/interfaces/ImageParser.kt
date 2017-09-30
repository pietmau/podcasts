package com.pietrantuono.podcasts.interfaces

import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.rometools.modules.itunes.EntryInformation
import com.rometools.modules.mediarss.MediaEntryModule

import javax.inject.Inject

class ImageParser @Inject
constructor(private val crashlyticsWrapper: CrashlyticsWrapper) {

    fun parseImage(itunesEntryInformation: EntryInformation?, mediaEntryModule: MediaEntryModule?): String? {
        if (itunesEntryInformation == null && mediaEntryModule == null) {
            return null
        }
        if (itunesEntryInformation != null && itunesEntryInformation.image != null) {
            return itunesEntryInformation.image.toString()
        }
        try {
            return mediaEntryModule!!.metadata.thumbnail[0].url.toString()
        } catch (e: NullPointerException) {
        } catch (e: IndexOutOfBoundsException) {
        }
        return null
    }
}
