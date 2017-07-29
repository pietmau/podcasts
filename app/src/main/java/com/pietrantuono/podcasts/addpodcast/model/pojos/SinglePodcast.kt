package com.pietrantuono.podcasts.addpodcast.model.pojos

import android.os.Parcelable

interface SinglePodcast : Parcelable {
    var wrapperType: String

    var kind: String

    var collectionId: Int?

    var trackId: Int?

    var artistName: String

    var collectionName: String

    var trackName: String

    var collectionCensoredName: String

    var trackCensoredName: String

    var collectionViewUrl: String

    var feedUrl: String

    var trackViewUrl: String

    var artworkUrl30: String

    var artworkUrl60: String

    var artworkUrl100: String

    var collectionPrice: Double?

    var trackPrice: Double?

    var trackRentalPrice: Int?

    var collectionHdPrice: Int?

    var trackHdPrice: Int?

    var trackHdRentalPrice: Int?

    var releaseDate: String

    var collectionExplicitness: String

    var trackExplicitness: String

    var trackCount: Int?

    val country: String

    val currency: String

    val primaryGenreName: String

    val contentAdvisoryRating: String

    val artworkUrl600: String

    val genreIds: List<String>

    val genres: List<String>
}
