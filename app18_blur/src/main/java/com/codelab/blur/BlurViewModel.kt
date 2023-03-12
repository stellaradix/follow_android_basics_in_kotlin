package com.codelab.blur

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.codelab.blur.workers.BlurWorker

class BlurViewModel(application: Application) : ViewModel() {

	internal var imageUri: Uri? = null
	internal var outputUri: Uri? = null

	private val workManager = WorkManager.getInstance(application)

	init {
		imageUri = getImageUri(application.applicationContext)
	}

	/**
	 * Create the WorkRequest to apply the blur and save the resulting image
	 * @param blurLevel The amount to blur the image
	 */
	internal fun applyBlur(blurLevel: Int) {
		val blurRequest = OneTimeWorkRequestBuilder<BlurWorker>()
			.setInputData(createInputDataForUri())
			.build()

		workManager.enqueue(blurRequest)
	}

	private fun uriOrNull(uriString: String?): Uri? {
		return if (!uriString.isNullOrEmpty()) {
			Uri.parse(uriString)
		} else {
			null
		}
	}

	private fun getImageUri(context: Context): Uri {
		val resources = context.resources

		val imageUri = Uri.Builder()
			.scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
			.authority(resources.getResourcePackageName(R.drawable.android_cupcake))
			.appendPath(resources.getResourceTypeName(R.drawable.android_cupcake))
			.appendPath(resources.getResourceEntryName(R.drawable.android_cupcake))
			.build()

		return imageUri
	}

	internal fun setOutputUri(outputImageUri: String?) {
		outputUri = uriOrNull(outputImageUri)
	}

	/**
	 * Creates the input data bundle which includes the Uri to operate on
	 * @return Data which contains the Image Uri as a String
	 */
	private fun createInputDataForUri(): Data {
		val builder = Data.Builder()
		imageUri?.let {
			builder.putString(KEY_IMAGE_URI, imageUri.toString())
		}
		return builder.build()
	}

	class BlurViewModelFactory(
		private val application: Application
	) : ViewModelProvider.Factory {

		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			return if (modelClass.isAssignableFrom(BlurViewModel::class.java)) {
				BlurViewModel(application) as T
			} else {
				throw IllegalArgumentException("Unknown ViewModel class")
			}
		}
	}
}