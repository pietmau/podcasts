package com.pietrantuono.podcasts.subscribedpodcasts.list.di

import java.lang.annotation.Documented
import java.lang.annotation.Retention

import javax.inject.Scope

import java.lang.annotation.RetentionPolicy.RUNTIME

@Scope
@Documented
@Retention(RUNTIME)
annotation class SubscribedPodcastScope
