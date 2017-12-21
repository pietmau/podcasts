package player

import android.app.Service
import android.content.Intent

class OtherActions {

    companion object {
        const val URI_EXTRA: String = "com.pppp.podcasts.uri"
        const val ACTION_DOWNLOAD_AND_ENQUEUE: String = "com.pppp.podcasts.download_and_enqueue"
    }

    fun downloadAndAddToQueue(uri: String, service: CustomMediaService?) {
        val intent = Intent()
        intent.action = ACTION_DOWNLOAD_AND_ENQUEUE
        intent.putExtra(URI_EXTRA, uri)
        (service as? Service)?.sendBroadcast(intent)
    }

}