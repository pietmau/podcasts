package pojos

import com.rometools.rome.feed.CopyFrom
import com.rometools.rome.feed.synd.SyndEnclosure
import io.realm.RealmObject

open class SimpleEnclosure : RealmObject, SyndEnclosure {
    private var url: String? = null
    private var length: Long = 0
    private var type: String? = null

    constructor(syndEnclosure: SyndEnclosure) {
        setLength(syndEnclosure.length)
        setUrl(syndEnclosure.url)
        setType(syndEnclosure.type)
    }

    constructor() {}

    override fun getUrl(): String? {
        return url
    }

    override fun setUrl(url: String) {
        this.url = url
    }

    override fun getLength(): Long {
        return length
    }

    override fun setLength(length: Long) {
        this.length = length
    }

    override fun getType(): String? {
        return type
    }

    override fun setType(type: String) {
        this.type = type
    }

    override fun getInterface(): Class<out CopyFrom>? {
        return null
    }

    override fun copyFrom(obj: CopyFrom) {

    }
}