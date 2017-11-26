package com.example.android.uamp

import android.app.Service.START_STICKY
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import com.example.android.uamp.playback.PlaybackManager
import junit.framework.Assert.fail
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Matchers.isA
import org.mockito.Matchers.isNull
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MusicServicePresenterTest {
    private val STOP_DELAY = 30000
    @Mock lateinit var mSession: MediaSessionCompat
    @Mock lateinit var mPlaybackManager: PlaybackManager
    @Mock lateinit var mDelayedStopHandler: DelayedStopHandler
    @InjectMocks lateinit var presenter: MusicServicePresenter
    @Mock lateinit var service: CustomMediaService

    @Before
    fun setUp() {
        presenter.setService(service)
    }

    @Test
    fun when_onCreate() {
        //WHEN
        try {
            presenter.onCreate()
        } catch (e: ClassCastException) {
        }
        //THEN
        verify(service).setSessionToken(isNull(MediaSessionCompat.Token::class.java))
        verify(mSession).setCallback(isNull(MediaSessionCompat.Callback::class.java))
        verify(mSession).setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        verify(mSession).setExtras(isA(Bundle::class.java));
        verify(mPlaybackManager).updatePlaybackState(isNull(String::class.java))
    }

    @Test
    fun when_onStartCommand() {
        // WHEN
        val intent = getIntent()
        val returnValue = presenter.onStartCommand(intent, 0)
        Assert.assertEquals(START_STICKY, returnValue)
        // THEN
        verify(mPlaybackManager).handlePauseRequest()
        verify(mDelayedStopHandler).removeCallbacksAndMessages(null)
        verify(mDelayedStopHandler).sendEmptyMessageDelayed(0, STOP_DELAY.toLong())
    }

    @Test
    fun when_onDestroy() {
        //WHEN
        try {
            presenter.onDestroy()
            fail()
        } catch (e: NullPointerException) {
        }
        //THEN
        verify(mPlaybackManager).handleStopRequest(null)
    }

    @Test
    fun when_onPlaybackStop() {
        // WHEN
        presenter.onPlaybackStop()
        // THEN
        verify(mSession).setActive(false)
        verify(mDelayedStopHandler).removeCallbacksAndMessages(null)
        verify(mDelayedStopHandler).sendEmptyMessageDelayed(0, STOP_DELAY.toLong())
        verify(service).stopForeground(true)
    }

    @Test
    fun when_onPlayBackStart() {
        // WHEN
        presenter.onPlayBackStart()
        // THEN
        verify(mSession).setActive(true)
        mDelayedStopHandler.removeCallbacksAndMessages(null)
        service.startService(isA(Intent::class.java))
    }



    @Test
    fun when_bar_then_fobar() {
        // WHEN
        presenter.onPlaybackStateUpdated(null)
        // THEN
        verify(mSession).setPlaybackState(null)
    }

    private fun getIntent(): Intent {
        val intent = mock(Intent::class.java)
        `when`(intent.action).thenReturn(Constants.ACTION_CMD)
        `when`(intent.getStringExtra(Constants.CMD_NAME)).thenReturn(Constants.CMD_PAUSE)
        return intent
    }
}