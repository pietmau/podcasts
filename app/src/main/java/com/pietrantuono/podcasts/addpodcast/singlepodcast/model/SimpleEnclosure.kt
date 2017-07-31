package com.pietrantuono.podcasts.addpodcast.singlepodcast.model

import com.rometools.rome.feed.synd.SyndEnclosure
import io.realm.RealmObject

open class SimpleEnclosure(enclosure: SyndEnclosure) : RealmObject(), SyndEnclosure by enclosure {

}
