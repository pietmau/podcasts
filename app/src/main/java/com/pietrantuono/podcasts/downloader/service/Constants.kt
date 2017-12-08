package com.pietrantuono.podcasts.downloader.service

const val COMMAND_DOWNLOAD_EPISODE: String = "download_episode"
const val COMMAND_DOWNLOAD_ALL_EPISODES: String = "download_all_episodes"
const val COMMAND_DELETE_EPISODE: String = "delete_episode"
const val COMMAND_DELETE_ALL_EPISODES: String = "delete_episode_all_episodes"
const val EXTRA_COMMAND: String = "command"
const val EXTRA_TRACK: String = "track_id"
const val PLAY_WHEN_READY: String = "play_when_ready"
const val EXTRA_TRACK_LIST: String = "track_list"
const val EXTRA_DOWNLOAD_REQUEST_ID: String = "download_request_id"
const val EXTRA_DOWNLOAD_REQUEST_ID_LIST: String = "download_request_id_list"
const val DOWNLOAD_COMPLETED: Int = 100
const val TAG = "DownloaderService"
const val STATUS_REMOVED = 905
const val STATUS_DOWNLOADING = 901
const val STATUS_QUEUED = 900