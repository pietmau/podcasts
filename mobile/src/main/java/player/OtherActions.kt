package player

import android.content.Intent
import android.content.Intent.getIntent

class OtherActions {

    fun downloadAndAddToQueue(uri: String, service: CustomMediaService?) {
        val intent = getIntentWitUri(uri)
        intent.putExtra(PLAY_WHEN_READY, true)
        service?.startService(intent)
    }

}