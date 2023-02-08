package com.codelab.forage.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codelab.forage.model.Forageable

/**
 * Room database to persist data for the Forage app.
 * This database stores a [Forageable] entity
 */
// create the database with all necessary annotations, methods, variables, etc.
@Database(entities = [Forageable::class], version = 1, exportSchema = false)
abstract class ForageDatabase : RoomDatabase() {

	abstract fun forageableDao(): ForageableDao

	companion object {

		@Volatile
		private var INSTANCE: ForageDatabase? = null

		fun getDatabase(context: Context): ForageDatabase {

			// if the INSTANCE is not null, then return it,
			// if it is, then create the database
			return INSTANCE ?: synchronized(this) {
				val instance = Room
					.databaseBuilder(
						context.applicationContext,
						ForageDatabase::class.java,
						"forage_database"
					)
					.build()

				// return instance
				return instance
			}
		}
	}
}