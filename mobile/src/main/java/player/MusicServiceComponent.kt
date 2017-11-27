package player

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(MusicServiceModule::class))
interface MusicServiceComponent {

    fun inject(service: MusicService)
}