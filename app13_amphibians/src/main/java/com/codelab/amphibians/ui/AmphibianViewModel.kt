package com.codelab.amphibians.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codelab.amphibians.network.Amphibian
import com.codelab.amphibians.network.AmphibianApi
import kotlinx.coroutines.launch

enum class AmphibianApiStatus { LOADING, ERROR, DONE }

class AmphibianViewModel : ViewModel() {

	// Create properties to represent MutableLiveData and LiveData for the API status
	private var _status = MutableLiveData<AmphibianApiStatus>()
	val status: LiveData<AmphibianApiStatus> = _status

	// Create properties to represent MutableLiveData and LiveData for a list of amphibian objects
	private var _amphibians = MutableLiveData<List<Amphibian>>()
	val amphibians: LiveData<List<Amphibian>> = _amphibians

	// Create properties to represent MutableLiveData and LiveData for a single amphibian object.
	// This will be used to display the details of an amphibian when a list item is clicked
	private val _amphibian = MutableLiveData<Amphibian>()
	val amphibian: LiveData<Amphibian> = _amphibian

	// Create a function that gets a list of amphibians from the api service and sets the status via a Coroutine
	fun getAmphibianList() {
		viewModelScope.launch {
			_status.value = AmphibianApiStatus.LOADING
			try {
				_amphibians.value = AmphibianApi.retrofitService.getAmphibians()
				_status.value = AmphibianApiStatus.DONE
			} catch (e: Exception) {
				_amphibians.value = listOf()
				_status.value = AmphibianApiStatus.ERROR
			}
		}
	}

	fun onAmphibianClicked(amphibian: Amphibian) {
		// Set the amphibian object
		_amphibian.value = amphibian
	}
}