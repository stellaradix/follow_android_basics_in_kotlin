package com.codelab.marsphotos.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

/**
 * The Retrofit object with the Scalars converter.
 */
private val retrofit = Retrofit.Builder()
	.addConverterFactory(ScalarsConverterFactory.create())
	.baseUrl(BASE_URL)
	.build()

/**
 * A public interface that exposes the [getPhotos] method
 */
interface MarsApiService {

	/**
	 * Returns a [String] and this method can be called from a Coroutine.
	 * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
	 * HTTP method
	 */
	@GET("photos")
	suspend fun getPhotos(): String
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object MarsApi {
	val retrofitService: MarsApiService by lazy {
		retrofit.create(MarsApiService::class.java)
	}
}