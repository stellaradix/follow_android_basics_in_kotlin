package com.codelab.inventory

import android.app.Application
import com.codelab.inventory.data.ItemRoomDatabase

class InventoryApplication : Application() {

	// Using by lazy so the database and the repository are only created when they're needed
	// rather than when the application starts
	val database: ItemRoomDatabase by lazy {
		ItemRoomDatabase.getDatabase(this)
	}
}