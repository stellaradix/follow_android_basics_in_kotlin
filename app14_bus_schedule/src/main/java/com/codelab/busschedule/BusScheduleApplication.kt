package com.codelab.busschedule

import android.app.Application
import com.codelab.busschedule.database.AppDatabase

class BusScheduleApplication : Application() {

	val database: AppDatabase by lazy {
		AppDatabase.getDatabase(this)
	}
}