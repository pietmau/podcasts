package com.pietrantuono.podcasts.main.presenter

import android.content.pm.PackageManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.nhaarman.mockito_kotlin.*
import com.pietrantuono.podcasts.main.view.MainView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.verification.VerificationMode
import rx.schedulers.Schedulers

@RunWith(MockitoJUnitRunner::class)
class KillSwitchFirebaseTest {
    @Mock lateinit var packageManager: PackageManager
    @Mock lateinit var databaseReference: DatabaseReference
    val appVersionCode: Int = 10
    val ioScheduler = Schedulers.trampoline()
    val mainThread = Schedulers.trampoline()
    @Mock lateinit var buildTypeChecker: BuildTypeChecker
    @Mock lateinit var view: MainView
    @Mock lateinit var subscriptionsManager: SubscriptionsManager
    lateinit var unitUnderTest: KillSwitchFirebase
    private val GARBAGE: String? = "garbage"
    lateinit var supportedVersionEventListenerCaptor: KArgumentCaptor<ValueEventListener>
    lateinit var appIsSupportedEventListenerCaptor: KArgumentCaptor<ValueEventListener>
    @Mock lateinit var supportedVersionsSnapshot: DataSnapshot
    @Mock lateinit var appIsSupportedSnapshot: DataSnapshot
    @Mock lateinit var supportedVersionsQuery: DatabaseReference
    @Mock lateinit var appIsSupportedQuery: DatabaseReference
    private val SOME_LONG: Long = 6

    @Before
    fun setUp() {
        unitUnderTest = KillSwitchFirebase(packageManager, databaseReference, appVersionCode, ioScheduler, mainThread, buildTypeChecker, subscriptionsManager)
        whenever(buildTypeChecker.isDebug()).thenReturn(false)
        whenever(packageManager.getInstallerPackageName(anyString())).thenReturn(KillSwitchFirebase.GOOGLE_PLAY)
        whenever(databaseReference.child(KillSwitchFirebase.SUPPORTED_VERSIONS)).thenReturn(supportedVersionsQuery)
        supportedVersionEventListenerCaptor = argumentCaptor()
        whenever(supportedVersionsSnapshot.value).thenReturn(listOf(appVersionCode.toLong()))
        whenever(databaseReference.child(KillSwitchFirebase.APP_IS_SUPPORTED)).thenReturn(appIsSupportedQuery)
        appIsSupportedEventListenerCaptor = argumentCaptor()
        whenever(appIsSupportedSnapshot.value).thenReturn(true)
    }

    private fun verifyWarnUser(verificationMode: VerificationMode) {
        verify(view, verificationMode).startKillSwitchActivity(anyInt(), anyInt())
        verify(view, verificationMode).finish()
    }

    @Test
    fun when_debug_nothing_happens() {
        //GIVEN
        whenever(buildTypeChecker.isDebug()).thenReturn(true)
        //WHEN
        unitUnderTest.checkIfNeedsToBeKilled(view)
        //THEN
        verify(databaseReference, never()).child(anyString())
    }

    @Test
    fun when_notDebug_queriesDb() {
        //WHEN
        unitUnderTest.checkIfNeedsToBeKilled(view)
        //THEN
        verify(databaseReference, atLeastOnce()).child(anyString())
    }

    @Test
    fun when_notGooglePlay_then_warnsUser() {
        //GIVEN
        whenever(packageManager.getInstallerPackageName(anyString())).thenReturn(GARBAGE)
        //WHEN
        unitUnderTest.checkIfNeedsToBeKilled(view)
        //THEN
        verifyWarnUser(times(1))
    }

    @Test
    fun when_appVersionIsSupported_then_userIsNotWarned() {
        //WHEN
        unitUnderTest.checkIfNeedsToBeKilled(view)
        verify(supportedVersionsQuery).addListenerForSingleValueEvent(supportedVersionEventListenerCaptor.capture())
        supportedVersionEventListenerCaptor.firstValue.onDataChange(supportedVersionsSnapshot)
        //THEN
        verifyWarnUser(never())
    }

    @Test
    fun when_appVersionIsNotSupported_then_userIsWarned() {
        // GIVEN
        whenever(supportedVersionsSnapshot.value).thenReturn(listOf(SOME_LONG))
        //WHEN
        unitUnderTest.checkIfNeedsToBeKilled(view)
        verify(supportedVersionsQuery).addListenerForSingleValueEvent(supportedVersionEventListenerCaptor.capture())
        supportedVersionEventListenerCaptor.firstValue.onDataChange(supportedVersionsSnapshot)
        //THEN
        verifyWarnUser(times(1))
    }

    @Test
    fun when_appIsNotSupported_then_userIsWarned() {
        //GIVEN
        whenever(appIsSupportedSnapshot.value).thenReturn(false)
        //WHEN
        unitUnderTest.checkIfNeedsToBeKilled(view)
        verify(appIsSupportedQuery).addListenerForSingleValueEvent(appIsSupportedEventListenerCaptor.capture())
        appIsSupportedEventListenerCaptor.firstValue.onDataChange(appIsSupportedSnapshot)
        //THEN
        verifyWarnUser(times(1))
    }

    @Test
    fun when_appIsSupported_then_userIsNotWarned() {
        //WHEN
        unitUnderTest.checkIfNeedsToBeKilled(view)
        verify(appIsSupportedQuery).addListenerForSingleValueEvent(appIsSupportedEventListenerCaptor.capture())
        appIsSupportedEventListenerCaptor.firstValue.onDataChange(appIsSupportedSnapshot)
        //THEN
        verifyWarnUser(never())
    }

}