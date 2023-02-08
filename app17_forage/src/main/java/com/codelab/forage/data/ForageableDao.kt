package com.codelab.forage.data

import androidx.room.*
import com.codelab.forage.model.Forageable
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for database interaction.
 */
@Dao
interface ForageableDao {

	// implement a method to retrieve all Forageables from the database
	@Query("SELECT * FROM forageable_database ORDER BY name ASC")
	fun getForageables(): Flow<List<Forageable>>

	// implement a method to retrieve a Forageable from the database by id
	@Query("SELECT * FROM forageable_database WHERE id = :id")
	fun getForageable(id: Long): Flow<Forageable>

	// implement a method to insert a Forageable into the database
	// (use OnConflictStrategy.REPLACE)
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(forageable: Forageable)

	// implement a method to update a Forageable that is already in the database
	@Update
	suspend fun update(forageable: Forageable)

	// implement a method to delete a Forageable from the database.
	@Delete
	suspend fun delete(forageable: Forageable)
}