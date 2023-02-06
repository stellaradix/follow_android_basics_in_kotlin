package com.codelab.devbyteviewer.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.codelab.devbyteviewer.database.getDatabase
import com.codelab.devbyteviewer.repository.VideosRepository
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * DevByteViewModel designed to store and manage UI-related data in a lifecycle conscious way.
 * This allows data to survive configuration changes such as screen rotations.
 * In addition, background work such as fetching network results
 * can continue through configuration changes and deliver results
 * after the new Fragment or Activity is available.
 *
 * @param application The application that this viewmodel is attached to, it's safe to hold a
 * reference to applications across rotation since Application is never recreated during actiivty
 * or fragment lifecycle events.
 */
class DevByteViewModel(
	application: Application
) : AndroidViewModel(application) {

	/**
	 * The data source this ViewModel will fetch results from.
	 */
	// Add a reference to the VideosRepository class
	private val videosRepository = VideosRepository(getDatabase(application))

	/**
	 * A playlist of videos displayed on the screen.
	 */
	// Replace the MutableLiveData and backing property below
	// to a reference to the 'videos' from the VideosRepository
	val playlist = videosRepository.videos

	/**
	 * Event triggered for network error.
	 * This is private to avoid exposing a way to set this value to observers.
	 */
	private var _eventNetworkError = MutableLiveData<Boolean>(false)

	/**
	 * Event triggered for network error. Views should use this to get access to the data.
	 */
	val eventNetworkError: LiveData<Boolean>
		get() = _eventNetworkError

	/**
	 * Flag to display the error message.
	 * This is private to avoid exposing a way to set this value to observers.
	 */
	private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

	/**
	 * Flag to display the error message. Views should use this to get access to the data.
	 */
	val isNetworkErrorShown: LiveData<Boolean>
		get() = _isNetworkErrorShown

	/**
	 * init{} is called immediately when this ViewModel is created.
	 */
	init {
		// Replace with a call to the refreshDataFromRepository() method
		refreshDataFromRepository()
	}

	/**
	 * Refresh data from the repository. Use a coroutine launch to run in a background thread.
	 */
	// Replace with the refreshDataFromRepository() method
	private fun refreshDataFromRepository() = viewModelScope.launch {
		try {
			videosRepository.refreshVideos()

			_eventNetworkError.value = false
			_isNetworkErrorShown.value = false

		} catch (networkError: IOException) {
			// Show a Toast error message and hide the progress bar.
			if (playlist.value.isNullOrEmpty()) {
				_eventNetworkError.value = true
			}
		}
	}

	/**
	 * Resets the network error flag.
	 */
	fun onNetworkErrorShown() {
		_isNetworkErrorShown.value = true
	}

	/**
	 * Factory for constructing DevByteViewModel with parameter
	 */
	class Factory(val app: Application) : ViewModelProvider.Factory {
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			if (modelClass.isAssignableFrom(DevByteViewModel::class.java)) {
				@Suppress("UNCHECKED_CAST")
				return DevByteViewModel(app) as T
			}
			throw IllegalArgumentException("Unable to construct viewmodel")
		}
	}
}