package com.codelab.devbyteviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.codelab.devbyteviewer.database.VideosDatabase
import com.codelab.devbyteviewer.database.asDomainModel
import com.codelab.devbyteviewer.domain.DevByteVideo
import com.codelab.devbyteviewer.network.DevByteNetwork
import com.codelab.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for fetching devbyte videos from the network and storing them on disk
 */
// Implement the VideosRepository class
class VideosRepository(
	private val database: VideosDatabase
) {

	val videos: LiveData<List<DevByteVideo>> =
		Transformations.map(database.videoDao.getVideos()) {
			it.asDomainModel()
		}

	suspend fun refreshVideos() {
		withContext(Dispatchers.IO) {
			val playlist = DevByteNetwork.devbytes.getPlaylist()
			database.videoDao.insertAll(playlist.asDatabaseModel())
		}
	}
}